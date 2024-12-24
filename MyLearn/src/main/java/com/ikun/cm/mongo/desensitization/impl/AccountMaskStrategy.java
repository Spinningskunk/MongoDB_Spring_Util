package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:47
 * @description: Desensitize the account if you want.
 */
public class AccountMaskStrategy implements SensitiveDataStrategy {

    @Override
    public Object mask(Field field, Object object) {
        // TODO someday maybe not so far...
        return null;
    }
}
