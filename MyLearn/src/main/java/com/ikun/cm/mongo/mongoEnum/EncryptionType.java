package com.ikun.cm.mongo.mongoEnum;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:38
 * @description: Enum for specifying encryption types. Supports multiple encryption algorithms.
 */
public enum EncryptionType {
    // This one is default encryption type.
    AES,
    // I like this one, subjectively.
    RSA,
    DES
}
