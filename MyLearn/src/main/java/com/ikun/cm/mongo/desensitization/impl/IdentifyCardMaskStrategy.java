package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24
 * @description: Desensitize the identification card number (18-digit ID).
 * The pattern is: First 6 digits + "xxxxxxxx" + last 4 digits.
 * Examples:
 * - "123456789012345678" -> "123456xxxxxxxx3456"
 */
public class IdentifyCardMaskStrategy implements SensitiveDataStrategy {

    /**
     * Desensitizes the identification card number.
     * It only desensitizes if the value is a valid 18-digit identification card number.
     * The pattern is: first 6 digits + "xxxxxxxx" + last 4 digits.
     *
     * @param field  The field to be desensitized.
     * @param object The object containing the field.
     * @return The desensitized identification card number, or the original value if it's invalid.
     */
    @Override
    public Object mask(Field field, Object object) {
        try {
            // Make sure the field is accessible
            field.setAccessible(true);

            // Get the value of the field in the provided object
            Object fieldValue = field.get(object);

            // Ensure the field is a String and matches the expected pattern (18 digits)
            if (fieldValue instanceof String) {
                String value = (String) fieldValue;
                if (value.length() == 18 && value.matches("^\\d{17}[0-9Xx]$")) {
                    // If the ID is valid, mask the middle 8 digits
                    return value.substring(0, 6) + "xxxxxxxx" + value.substring(14);
                }
            }

            // If the field value does not meet the criteria, return it unchanged
            return fieldValue;

        } catch (IllegalAccessException e) {
            // Handle the case where the field is inaccessible
            e.printStackTrace();
            return null;
        }
    }
}
