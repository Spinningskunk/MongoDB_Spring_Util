package com.ikun.cm.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author: HeKun
 * @date: 2024/12/24 01:52
 * @description: AES Encryption/Decryption utility class.
 * Provides methods for AES encryption and decryption.
 */
public class AESUtil {

    private static final String ALGORITHM = "AES";  // Using AES encryption algorithm

    /**
     * Generates an AES key.
     * @return the generated AES key
     * @throws Exception if encryption fails
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // AES uses 128-bit key
        return keyGenerator.generateKey();
    }

    /**
     * Encrypts the given data using the specified key.
     * @param data the data to be encrypted
     * @param key the encryption key
     * @return the encrypted data (Base64 encoded)
     * @throws Exception if encryption fails
     */
    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec); // Initialize encryption mode
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted); // Return Base64 encoded encrypted data
    }

    /**
     * Decrypts the given encrypted data using the specified key.
     * @param encryptedData the encrypted data to be decrypted (Base64 encoded)
     * @param key the decryption key
     * @return the decrypted data
     * @throws Exception if decryption fails
     */
    public static String decrypt(String encryptedData, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec); // Initialize decryption mode
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decrypted); // Return decrypted data
    }
}
