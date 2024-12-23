package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:48
 * @description: Desensitize the identification card number
 */
public class IdentifyCardMaskStrategy implements SensitiveDataStrategy {

    @Override
    public String desensitizationStrategy(String value) {
        if (value != null && value.length() == 18 && value.matches("^\\d{17}[0-9Xx]$")) {
            // Desensitize the identification card number
            return value.substring(0, 6) + "xxxxxxxx" + value.substring(14);
        }
        // Return the original value if it does not match the expected pattern.
        return value;
    }
}
