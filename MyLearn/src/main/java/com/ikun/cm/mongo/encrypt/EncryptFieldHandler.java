package com.ikun.cm.mongo.encrypt;

import com.ikun.cm.mongo.annotation.EncryptField;
import com.ikun.cm.mongo.mongoEnum.EncryptionType;
import com.ikun.cm.util.encryption.AESUtil;
import com.ikun.cm.util.encryption.DESUtil;
import com.ikun.cm.util.encryption.RSAUtil;

import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:38
 * @description: Utility class for handling the encryption and decryption of fields annotated with @EncryptField.
 */
public class EncryptFieldHandler {

    /**
     * Encrypts fields marked with @EncryptField annotation before saving to the database.
     * @param entity the object to process
     * @throws Exception if encryption fails
     */
    public static void handleEncryptFields(Object entity) throws Exception {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (encryptField != null) {
                EncryptionType encryptionType = encryptField.encryptionType();
                field.setAccessible(true);

                // Get field value
                Object value = field.get(entity);
                if (value != null) {
                    String encryptedValue = encryptValue(value.toString(), encryptionType);
                    field.set(entity, encryptedValue); // Set encrypted value back to the field
                }
            }
        }
    }

    /**
     * Encrypts the given value using the specified encryption type.
     * @param value the value to encrypt
     * @param encryptionType the encryption type to use (AES, RSA, or DES)
     * @return the encrypted value
     * @throws Exception if encryption fails
     */
    private static String encryptValue(String value, EncryptionType encryptionType) throws Exception {
        String key = "1234567890123456"; // Example key (you should generate or manage keys securely)

        switch (encryptionType) {
            case AES:
                return AESUtil.encrypt(value, key);
            case RSA:
                return RSAUtil.encrypt(value, key); // Public key encryption
            case DES:
                return DESUtil.encrypt(value, key); // DES encryption
            default:
                throw new UnsupportedOperationException("Unsupported encryption type: " + encryptionType);
        }
    }

    /**
     * Decrypts fields marked with @EncryptField annotation after retrieving from the database.
     * @param entity the object to process
     * @throws Exception if decryption fails
     */
    public static void handleDecryptFields(Object entity) throws Exception {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (encryptField != null) {
                EncryptionType encryptionType = encryptField.encryptionType();
                field.setAccessible(true);

                // Get field value
                Object value = field.get(entity);
                if (value != null) {
                    String decryptedValue = decryptValue(value.toString(), encryptionType);
                    field.set(entity, decryptedValue); // Set decrypted value back to the field
                }
            }
        }
    }

    /**
     * Decrypts the given value using the specified encryption type.
     * @param value the value to decrypt
     * @param encryptionType the encryption type to use (AES, RSA, or DES)
     * @return the decrypted value
     * @throws Exception if decryption fails
     */
    private static String decryptValue(String value, EncryptionType encryptionType) throws Exception {
        String key = "1234567890123456"; // Example key (you should generate or manage keys securely)

        switch (encryptionType) {
            case AES:
                return AESUtil.decrypt(value, key);
            case RSA:
                return RSAUtil.decrypt(value, key); // Private key decryption
            case DES:
                return DESUtil.decrypt(value, key); // DES decryption
            default:
                throw new UnsupportedOperationException("Unsupported decryption type: " + encryptionType);
        }
    }
}

