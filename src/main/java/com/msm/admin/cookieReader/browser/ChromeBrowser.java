package com.msm.admin.cookieReader.browser;

import com.fasterxml.jackson.databind.JsonNode;
import com.msm.admin.cookieReader.OS;
import com.msm.admin.cookieReader.cookie.Cookie;
import com.msm.admin.cookieReader.cookie.DecryptedCookie;
import com.msm.admin.cookieReader.cookie.EncryptedCookie;
import com.msm.admin.utils.JsonUtils;
import com.sun.jna.platform.win32.Crypt32Util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author quavario@gmail.com
 * @date 2021/3/8 11:12
 */
public class ChromeBrowser extends Browser {
    private String chromeKeyringPassword = null;

    /**
     * Returns a set of cookie store locations
     * @return
     */
    @Override
    protected Set<File> getCookieStores() {
        HashSet<File> cookieStores = new HashSet<File>();

        // pre Win7
        cookieStores.add(new File(System.getProperty("user.home") + "\\Application Data\\Google\\Chrome\\User Data\\Default\\Cookies"));

        // Win 7+
        cookieStores.add(new File(System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies"));

        // Mac
        cookieStores.add(new File(System.getProperty("user.home") + "/Library/Application Support/Google/Chrome/Default/Cookies"));

        // Linux
        cookieStores.add(new File(System.getProperty("user.home") + "/.config/chromium/Default/Cookies"));

        return cookieStores;
    }

    /**
     * Processes all cookies in the cookie store for a given domain or all
     * domains if domainFilter is null
     *
     * @param cookieStore
     * @param domainFilter
     * @return
     */
    @Override
    protected Set<Cookie> processCookies(File cookieStore, String domainFilter) {
        HashSet<Cookie> cookies = new HashSet<Cookie>();
        if(cookieStore.exists()){
            Connection connection = null;
            try {
                cookieStoreCopy.delete();
                Files.copy(cookieStore.toPath(), cookieStoreCopy.toPath());
                // load the sqlite-JDBC driver using the current class loader
                Class.forName("org.sqlite.JDBC");
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:" + cookieStoreCopy.getAbsolutePath());
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30); // set timeout to 30 seconds
                ResultSet result = null;
                if(domainFilter == null || domainFilter.isEmpty()){
                    result = statement.executeQuery("select * from cookies");
                } else {
                    result = statement.executeQuery("select * from cookies where host_key like \"%" + domainFilter + "%\"");
                }
                while (result.next()) {
                    String name = result.getString("name");
                    byte[] encryptedBytes = result.getBytes("encrypted_value");
                    String path = result.getString("path");
                    String domain = result.getString("host_key");
                    boolean secure = result.getBoolean("is_secure");
                    boolean httpOnly = result.getBoolean("is_httponly");
                    Date expires = result.getDate("expires_utc");

                    EncryptedCookie encryptedCookie = new EncryptedCookie(name,
                            encryptedBytes,
                            expires,
                            path,
                            domain,
                            secure,
                            httpOnly,
                            cookieStore);

                    DecryptedCookie decryptedCookie = decrypt(encryptedCookie);

                    if(decryptedCookie != null){
                        cookies.add(decryptedCookie);
                    } else {
                        cookies.add(encryptedCookie);
                    }
                    cookieStoreCopy.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // if the error message is "out of memory",
                // it probably means no database file is found
            } finally {
                try {
                    if (connection != null){
                        connection.close();
                    }
                } catch (SQLException e) {
                    // connection close failed
                }
            }
        }
        return cookies;
    }

    /**
     * Decrypts an encrypted cookie
     * @param encryptedCookie
     * @return
     */
    @Override
    protected DecryptedCookie decrypt(EncryptedCookie encryptedCookie) {
        byte[] decryptedBytes = null;
        if(OS.isWindows()){
            try {
                // 获取解密key
                String location = System.getProperty("user.home")+"/AppData/Local/Google/Chrome/User Data/Local State";
                Path p = Paths.get(location);
//                ExpressionExecutor ee = Utils.readJson(Files.readString(p));
//                String key = ee.execute("os_crypt->encrypted_key").get();
//
//                JsonUtils.jsonToEntity()

                decryptedBytes = Crypt32Util.cryptUnprotectData(encryptedCookie.getEncryptedValue());
            } catch (Exception e){
                decryptedBytes = null;
            }
        } else if(OS.isLinux()){
            try {
                byte[] salt = "saltysalt".getBytes();
                char[] password = "peanuts".toCharArray();
                char[] iv = new char[16];
                Arrays.fill(iv, ' ');
                int keyLength = 16;

                int iterations = 1;

                PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength * 8);
                SecretKeyFactory pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

                byte[] aesKey = pbkdf2.generateSecret(spec).getEncoded();

                SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new String(iv).getBytes()));

                // if cookies are encrypted "v10" is a the prefix (has to be removed before decryption)
                byte[] encryptedBytes = encryptedCookie.getEncryptedValue();
                if (new String(encryptedCookie.getEncryptedValue()).startsWith("v10")) {
                    encryptedBytes = Arrays.copyOfRange(encryptedBytes, 3, encryptedBytes.length);
                }
                decryptedBytes = cipher.doFinal(encryptedBytes);
            } catch (Exception e) {
                decryptedBytes = null;
            }
        } else if(OS.isMac()){
            // access the decryption password from the keyring manager
            if(chromeKeyringPassword == null){
                try {
                    chromeKeyringPassword = getMacKeyringPassword("Chrome Safe Storage");
                } catch (IOException e) {
                    decryptedBytes = null;
                }
            }
            try {
                byte[] salt = "saltysalt".getBytes();
                char[] password = chromeKeyringPassword.toCharArray();
                char[] iv = new char[16];
                Arrays.fill(iv, ' ');
                int keyLength = 16;

                int iterations = 1003;

                PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength * 8);
                SecretKeyFactory pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

                byte[] aesKey = pbkdf2.generateSecret(spec).getEncoded();

                SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new String(iv).getBytes()));

                // if cookies are encrypted "v10" is a the prefix (has to be removed before decryption)
                byte[] encryptedBytes = encryptedCookie.getEncryptedValue();
                if (new String(encryptedCookie.getEncryptedValue()).startsWith("v10")) {
                    encryptedBytes = Arrays.copyOfRange(encryptedBytes, 3, encryptedBytes.length);
                }
                decryptedBytes = cipher.doFinal(encryptedBytes);
            } catch (Exception e) {
                decryptedBytes = null;
            }
        }

        if(decryptedBytes == null){
            return null;
        } else {
            return new DecryptedCookie(encryptedCookie.getName(),
                    encryptedCookie.getEncryptedValue(),
                    new String(decryptedBytes),
                    encryptedCookie.getExpires(),
                    encryptedCookie.getPath(),
                    encryptedCookie.getDomain(),
                    encryptedCookie.isSecure(),
                    encryptedCookie.isHttpOnly(),
                    encryptedCookie.getCookieStore());
        }
    }

    /**
     * Accesses the apple keyring to retrieve the Chrome decryption password
     * @param application
     * @return
     * @throws IOException
     */
    private static String getMacKeyringPassword(String application) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"security", "find-generic-password","-w", "-s", application};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String result = "";
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            result += s;
        }
        return result;
    }
}
