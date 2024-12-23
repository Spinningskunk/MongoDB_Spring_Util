package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:47
 * @description: Desensitize the account if you want.
 */
public class AccountMaskStrategy implements SensitiveDataStrategy {

    @Override
    public String desensitizationStrategy(String value) {
        // TODO
        return null;
    }
}
