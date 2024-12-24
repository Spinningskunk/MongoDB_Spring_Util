package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;
import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 1:17
 * @description: Desensitize the name such as Chinese name or English name,
 * such as "蔡徐坤" or "John Doe". Examples:
 * - "蔡徐坤" -> "蔡**"
 * - "John Doe" -> "John *****"
 * - "John Michael Doe" -> "John Michael *****"
 */
public class NameMaskStrategy implements SensitiveDataStrategy {

    /**
     * Mask the name field depending on whether it is a Chinese or English name.
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
                // Check if it's a Chinese or English name and mask accordingly
                if (isChinese(value)) {
                    return desensitizeChineseName(value);
                } else {
                    return desensitizeEnglishName(value);
                }
            }

            // If it's not a String, return the value unchanged
            return fieldValue;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle error accessing field
        }
    }

    /**
     * Check if the name is in Chinese characters.
     * @param value The name string to check.
     * @return true if it contains only Chinese characters, otherwise false.
     */
    private boolean isChinese(String value) {
        return value.matches("[\\u4e00-\\u9fa5]+");
    }

    /**
     * Mask a Chinese name by showing only the surname and replacing the rest with asterisks.
     * If the name has only one character, it will return it unchanged.
     * @param value The Chinese name to desensitize.
     * @return The desensitized Chinese name.
     */
    private String desensitizeChineseName(String value) {
        if (value.length() <= 1) {
            return value; // If there's only one character, return it unchanged
        }
        return value.substring(0, 1) + "**"; // Only show the surname, mask the rest
    }

    /**
     * Mask an English name by leaving the first name visible and replacing the last name with asterisks.
     * Handles middle names and multiple last names by masking the last part(s).
     * @param value The English name to desensitize.
     * @return The desensitized English name.
     */
    private String desensitizeEnglishName(String value) {
        String[] nameParts = value.split(" ");
        if (nameParts.length == 1) {
            return nameParts[0] + " *****"; // If only one name part, mask the rest
        } else {
            StringBuilder maskedName = new StringBuilder();
            for (int i = 0; i < nameParts.length - 1; i++) {
                maskedName.append(nameParts[i]).append(" "); // Append first and middle names
            }
            maskedName.append(repeat("*", nameParts[nameParts.length - 1].length())); // Mask the last name(s)
            return maskedName.toString();
        }
    }

    /**
     * Repeat a string (e.g., "*") for a given number of times.
     * @param str The string to repeat.
     * @param count The number of times to repeat the string.
     * @return The resulting string of repeated characters.
     */
    private String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
