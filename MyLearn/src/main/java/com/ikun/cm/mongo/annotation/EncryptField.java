package com.ikun.cm.mongo.annotation;

import com.ikun.cm.mongo.mongoEnum.EncryptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: HeKun
 * @date: 2024/12/27 2:01
 * @description: We could use this annotation to encrypt the field we need, such as password, phone number, etc.
 * By specifying the encryption type (default: AES), the encryption method is determined.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EncryptField {
    /**
     * Specifies the encryption type. Defaults to AES encryption.
     * @return the encryption type
     */
    EncryptionType encryptionType() default EncryptionType.AES;
}
