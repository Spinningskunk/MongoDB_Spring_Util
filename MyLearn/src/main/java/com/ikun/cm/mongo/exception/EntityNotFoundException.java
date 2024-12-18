package com.ikun.cm.mongo.exception;

/**
 * @author: HeKun
 * @date: 2024/12/2 21:17
 * @description: when we can not find the entity,
 * such as getById()、updateById() and so on.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
