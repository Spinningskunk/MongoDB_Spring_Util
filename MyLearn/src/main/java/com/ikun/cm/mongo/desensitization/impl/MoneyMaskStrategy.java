package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:53
 * @description: Desensitize the money such as salary and so on if you want.
 */
public class MoneyMaskStrategy implements SensitiveDataStrategy {

    @Override
    public Object mask(Field field, Object object) {
        // TODO someday maybe not so far...
        return null;
    }
}
