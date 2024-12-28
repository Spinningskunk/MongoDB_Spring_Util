package com.ikun.cm.util.encryption;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;

/**
 * @author: HeKun
 * @date: 2024/12/24 02:12
 * @description: RSA Encryption/Decryption utility class.
 * Provides methods for RSA encryption and decryption.
 */
public class RSAUtil {

    private static final String ALGORITHM = "RSA";  // Using RSA encryption algorithm

    /**
     * Encrypts the given data using the specified public key.
     * @param data the data to be encrypted
     * @param publicKeyStr the public key (Base64 encoded)
     * @return the encrypted data (Base64 encoded)
     * @throws Exception if encryption fails
     */
    public static String encrypt(String data, String publicKeyStr) throws Exception {
        PublicKey publicKey = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); // Initialize encryption mode
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData); // Return Base64 encoded encrypted data
    }

    /**
     * Decrypts the given encrypted data using the specified private key.
     * @param encryptedData the encrypted data to be decrypted (Base64 encoded)
     * @param privateKeyStr the private key (Base64 encoded)
     * @return the decrypted data
     * @throws Exception if decryption fails
     */
    public static String decrypt(String encryptedData, String privateKeyStr) throws Exception {
        PrivateKey privateKey = KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(new X509EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr)));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // Initialize decryption mode
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData); // Return decrypted data
    }
}
