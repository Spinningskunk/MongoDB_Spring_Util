package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:47
 * @description: Desensitize the phone number
 */
public class PhoneMaskStrategy implements SensitiveDataStrategy {

    /**
     * Desensitize the phone number.
     * @param value
     * @return
     */
    @Override
    public String desensitizationStrategy(String value) {
        // Check if the input value matches the expected pattern.
        if (value != null && value.length() == 11 && value.matches("^1\\d{10}$")) {
            //  Desensitize the phone number
            return value.substring(0, 3) + "xxxxxxxxxx";
        }
        // Return the original value if it does not match the expected pattern.
        return value;
    }

}
