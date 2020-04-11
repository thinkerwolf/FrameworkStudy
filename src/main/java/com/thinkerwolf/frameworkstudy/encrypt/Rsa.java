package com.thinkerwolf.frameworkstudy.encrypt;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Rsa {
    /** 钥匙位数 */
    private static final int MAX_KEY_SIZE = 1024;
    /** 加密最大明文长度 */
    private static final int MAX_ENCRYPT_BLOCK = MAX_KEY_SIZE / 8 - 11;
    /** 解密最大密文长度 */
    private static final int MAX_DECRYPT_BLOCK = MAX_KEY_SIZE / 8;

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        g.initialize(1024);
        return g.generateKeyPair();
    }

    public static PublicKey getPublicKey(String k) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] encodeKey = Base64.getDecoder().decode(k.getBytes());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodeKey);
        return kf.generatePublic(spec);
    }

    public static PrivateKey getPrivateKey(String k) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] encodeKey = Base64.getDecoder().decode(k.getBytes());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encodeKey);
        return kf.generatePrivate(spec);
    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encodeData = Base64.getEncoder().encode(out.toByteArray());
        out.close();
        return encodeData;
    }

    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        data = Base64.getDecoder().decode(data);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decodeData = out.toByteArray();
        out.close();
        return decodeData;
    }

    public static void main(String[] args) {
        try {
            // 生成公钥密钥
            KeyPair kp = generateKeyPair();
            // 公钥密钥利用Base64编码生成公钥密钥字符串
            String publicKey = new String(Base64.getEncoder().encode(kp.getPublic().getEncoded()));
            String privateKey = new String(Base64.getEncoder().encode(kp.getPrivate().getEncoded()));
            System.out.printf("公钥：%s \n私钥：%s\n", publicKey, privateKey);
            // 加密内容
            byte[] content = "woshi".getBytes();
            // 加密
            byte[] encryptData = encrypt(content, getPublicKey(publicKey));
            // 解密
            byte[] decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println(new String(decryptData));

            // 签名


        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
