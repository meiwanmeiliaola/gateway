package com.msm.admin.modules.common.controller;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author quavario@gmail.com
 * @date 2020/5/20 15:28
 */
public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {

        // 生成RSA公钥/私钥:
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair kp = kpGen.generateKeyPair();
        PrivateKey sk = kp.getPrivate();
        PublicKey pk = kp.getPublic();


        // 待签名的消息:
//        byte[] message = "Hello, I am Bob!".getBytes(StandardCharsets.UTF_8);
        File file = new File("F:\\BaiduNetdiskDownload\\AdminLTE-3.0.2.zip");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] message = IOUtils.toByteArray(fileInputStream);

        // 用私钥签名:
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(sk);
        s.update(message);
        byte[] signed = s.sign();
        BigInteger bigInteger = new BigInteger(1, signed);
        String s1 = String.valueOf(bigInteger);
        System.out.println(s1);

        System.out.println(String.format("signature: %x", new BigInteger(1, signed)));

        System.out.println(signed.length);
        // 用公钥验证:
        Signature v = Signature.getInstance("SHA1withRSA");
        v.initVerify(pk);
        v.update(message);
        BigInteger bigInteger1 = new BigInteger(s1);
        boolean valid = v.verify(bigInteger1.toByteArray());
        System.out.println("valid? " + valid);
    }
}

