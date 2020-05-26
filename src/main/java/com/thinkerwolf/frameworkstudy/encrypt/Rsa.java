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

    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
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

    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
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
            //1. Alice生成密钥对，公钥发给Bob
            KeyPair kp = generateKeyPair();
            //2. 对密钥对Base64编码变成字符串
            String publicKey = new String(Base64.getEncoder().encode(kp.getPublic().getEncoded()));
            String privateKey = new String(Base64.getEncoder().encode(kp.getPrivate().getEncoded()));
            System.out.printf("公钥：%s \n私钥：%s\n", publicKey, privateKey);

            //3. 请求过程 Bob公钥加密，Alice私钥解密
            byte[] request = "Bob request Alice".getBytes();
            byte[] encryptRequest = encrypt(request, getPublicKey(publicKey));
            byte[] decryptRequest = decrypt(encryptRequest, getPrivateKey(privateKey));
            System.out.println(new String(decryptRequest));

            //4. 响应过程


        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
