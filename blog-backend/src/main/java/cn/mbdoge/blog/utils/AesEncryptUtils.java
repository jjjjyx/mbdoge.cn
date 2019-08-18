package cn.mbdoge.blog.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class AesEncryptUtils {
    private static final String KEY = "Process finished with exit code 0";

    public static String encrypt(String plainText, String key) throws Exception {
        byte[] clean = plainText.getBytes(StandardCharsets.UTF_8);

        // Generating IV.
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//        System.out.println("iv = " + Arrays.toString(ivParameterSpec.getIV()));

        // Hashing key.
        MessageDigest digest = MessageDigest.getInstance("SHA-512");

        digest.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = new byte[16];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Encrypt.
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(clean);

        // Combine IV and encrypted part.
//        byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
//        System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
//        System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);

        return Base64.encodeBase64String(encrypted);
    }


    public static String decrypt(String content, String key) throws Exception {

        int ivSize = 16;
        int keySize = 16;

        byte[] encryptedIvTextBytes = Base64.decodeBase64(content);

        // Extract IV.
        byte[] iv = new byte[ivSize];
//        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract encrypted part.
//        int encryptedSize = encryptedIvTextBytes.length - ivSize;
//        byte[] encryptedBytes = new byte[encryptedSize];
//        System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

        // Hash key.
        byte[] keyBytes = new byte[keySize];
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.update(key.getBytes());
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
//        System.out.println("bytesToHex(keyBytes) = " + bytesToHex(keyBytes));

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Decrypt.
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);


        byte[] decrypted = cipherDecrypt.doFinal(encryptedIvTextBytes);

        return new String(decrypted);
    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte hashInByte : hashInBytes) {
            sb.append(Integer.toString((hashInByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }
    public static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY);
    }

    public static void main(String[] args) throws Exception {
//        Map map=new HashMap<String,String>();
//        map.put("key","value");
//        map.put("中文","汉字");
        String content = "222";
        System.out.println("加密前：" + content);

        String encrypt = encrypt(content, KEY);
        System.out.println("加密后：" + encrypt);

        String decrypt = decrypt("irGwltpx+gRKSJpMDCpHORb6nzBr+BnAGtUd6LEBxYn7pnjRNYaJrRbJyLbPeKct4c6hOYrye7ROXaeUI2WdKzt/4NCYk+yrRJ8ZHufxooKs4npvuo6ZUbxSKQPRscQ7RLWQQteJohIQUUgOzkfFEQ==", KEY);
        System.out.println("解密后：" + decrypt);
    }

}
