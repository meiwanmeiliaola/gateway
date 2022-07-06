package com.msm.admin.modules.common.controller;



import java.io.*;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Scanner;

/**
 * @author quavario@gmail.com
 * @date 2020/5/20 17:08
 */
public class Server {
    static final String password = "msmkeystore";
    static final String alias = "msmkey";
    static final String keyPass = "msmkeypass";
    static final String STORE_PATH = "\\msm.keystore";

    public static void main(String[] args) throws Exception {
        String filePath = "";
        byte[] signedFileBytes;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            System.out.println("请输入待验证文件路径：");
            Scanner scanner = new Scanner(System.in);
            do {
                filePath = scanner.next();
                if (!new File(filePath).exists()) {
                    System.out.println("文件不存在，请重新输入：");
                }
            } while (!new File(filePath).exists());
        }
        signedFileBytes = getFileBytes(filePath);

        byte[] oriFileBytes = new byte[signedFileBytes.length - 256];

        byte[] signed = new byte[256];

        int position = 0;
        for (int i = 0; i < signedFileBytes.length; i++) {
            if (i < signedFileBytes.length -256) {
                oriFileBytes[i] = signedFileBytes[i];
            } else {
                signed[position] = signedFileBytes[i];
                position++;
            }
        }

        String currentFilePath = System.getProperty("user.dir");
        File storeFile = new File(currentFilePath + STORE_PATH);
        FileInputStream storeFileIs = new FileInputStream(storeFile);

        // 载入keystore
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(storeFileIs, password.toCharArray());

        Certificate certificate = ks.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();


        Signature v = Signature.getInstance("SHA1withRSA");
        v.initVerify(publicKey);
        v.update(oriFileBytes);
        boolean verify = v.verify(signed);
        System.out.println(verify);
    }

    public static byte[] getFileBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("待签名文件不存在");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        return outputStream.toByteArray();
    }
}
