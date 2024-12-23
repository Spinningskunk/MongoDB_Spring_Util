package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:53
 * @description: Desensitize the money such as salary and so on if you want.
 */
public class MoneyMaskStrategy implements SensitiveDataStrategy {

    @Override
    public String desensitizationStrategy(String value) {
        // TODO
        return null;
    }
}
