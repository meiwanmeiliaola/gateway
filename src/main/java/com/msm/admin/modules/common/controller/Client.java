package com.msm.admin.modules.common.controller;

import io.netty.handler.codec.base64.Base64Encoder;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Scanner;

/**
 * @author quavario@gmail.com
 * @date 2020/5/20 17:08
 */
public class Client {
    static final String password = "msmkeystore";
    static final String alias = "msmkey";
    static final String keyPass = "msmkeypass";

    static final String STORE_PATH = "\\msm.keystore";
    public static void main(String[] args) throws Exception {
        String filePath = "";
        byte[] message;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            System.out.println("请输入待签名文件路径：");
            Scanner scanner = new Scanner(System.in);
            do {
                filePath = scanner.next();
                if (!new File(filePath).exists()) {
                    System.out.println("文件不存在，请重新输入：");
                }
            } while (!new File(filePath).exists());
        }
        message = getFileBytes(filePath);

        File oriFile = new File(filePath);

        String currentFilePath = System.getProperty("user.dir");
        File storeFile = new File(currentFilePath + STORE_PATH);
        FileInputStream storeFileIs = new FileInputStream(storeFile);

        // 载入keystore
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(storeFileIs, password.toCharArray());

        /*Certificate certificate = ks.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("publicKey" + publicKey);*/

        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, keyPass.toCharArray());
        // 用私钥签名:
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(privateKey);
        s.update(message);
        byte[] signed = s.sign();
        System.out.println(signed.length);
        byte[] signedFileBytes = new byte[message.length + signed.length];


        for (int i = 0; i < message.length; i ++) {
            signedFileBytes[i] = message[i];
        }
        int position = message.length;

        for (int i = 0; i < signed.length; i ++) {
            signedFileBytes[position] = signed[i];
            position++;
        }

        File file = new File(currentFilePath + "\\signed_" + oriFile.getName());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(signedFileBytes);

        System.out.println("signed successfully to " + file.getAbsolutePath());
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
