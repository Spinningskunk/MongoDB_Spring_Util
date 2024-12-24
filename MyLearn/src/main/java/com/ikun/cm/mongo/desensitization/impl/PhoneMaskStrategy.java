package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;
import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:47
 * @description: Desensitize the phone number.
 */
public class PhoneMaskStrategy implements SensitiveDataStrategy {

    /**
     * Mask the phone number field depending on its value.
     * @param field The field to be desensitized.
     * @param object The object containing the field.
     * @return The desensitized value or the original value if not applicable.
     */
    @Override
    public Object mask(Field field, Object object) {
        try {
            // Make sure the field is accessible
            field.setAccessible(true);

            // Get the value of the field from the object
            Object fieldValue = field.get(object);

            // If the value is a string and not null, proceed with desensitization
            if (fieldValue instanceof String) {
                String value = (String) fieldValue;
                if (value.isEmpty()) {
                    return value; // Return empty string as is
                }
                // If it's a phone number, apply desensitization
                return desensitizePhoneNumber(value);
            }

            // If it's not a String, return the value unchanged
            return fieldValue;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle error accessing field
        }
    }

    /**
     * Desensitize the phone number by masking the middle digits.
     * @param value The phone number to desensitize.
     * @return The desensitized phone number.
     */
    private String desensitizePhoneNumber(String value) {
        // Check if the phone number is valid (11 digits, starting with '1')
        if (value != null && value.length() == 11 && value.matches("^1\\d{10}$")) {
            // Desensitize the phone number (e.g., 13812345678 -> 138xxxxxxxx)
            return value.substring(0, 3) + "xxxxxxxxxx";
        }
        // If the value is not a valid phone number, return it unchanged
        return value;
    }
}
