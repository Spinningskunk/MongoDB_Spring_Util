package com.ikun.cm.mongo.desensitization.impl;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

/**
 * @author: HeKun
 * @date: 2024/12/24 1:17
 * @description: Desensitize the name such as Chinese name or English name,such as "蔡徐坤" or "John Doe".
 * Examples:
 * - "蔡徐坤" -> "蔡**"
 * - "John Doe" -> "John *****"
 * - "John Michael Doe" -> "John Michael *****"
 */
public class NameMaskStrategy implements SensitiveDataStrategy {

    /**
     * Desensitizes the name based on its type (Chinese or English).
     * - For Chinese names, only the surname is shown and the rest is replaced with asterisks.
     * - For English names, the first name is shown and the last name is replaced with asterisks.
     *
     * @param value The name to be desensitized
     * @return The desensitized name by our rules.
     */
    @Override
    public String desensitizationStrategy(String value) {
        if (value == null || value.isEmpty()) {
            // Return the value unchanged if it's null or empty.
            return value;
        }
        // Check if the name is a Chinese name.
        if (isChinese(value)) {
            return desensitizeChineseName(value);
        } else {
            // If it's not a Chinese name, assume it's an English name.
            return desensitizeEnglishName(value);
        }
    }


    // Check if the name is a Chinese name.
    private boolean isChinese(String value) {
        // As we all know, Chinese characters are within the Unicode range \u4e00-\u9fa5
        return value.matches("[\\u4e00-\\u9fa5]+");
    }

    /**
     * Masks a Chinese name. Only the surname is visible, the rest is replaced with asterisks.
     * If the name consists of only one character, it is returned unchanged.
     *
     * @param value The Chinese name to be desensitized.
     * @return The desensitized Chinese name.
     */
    private String desensitizeChineseName(String value) {
        if (value.length() <= 1) {
            return value;  // 只有一个字，直接返回
        }
        return value.substring(0, 1) + "**";  // 只显示姓，名字部分用星号替代
    }

    /**
     * Masks an English name. The first name is visible, and the last name is replaced with asterisks.
     * Handle cases where there may be a middle name or multiple last names.
     * For example: "John Michael Doe" -> "John Michael *****"
     *
     * @param value The English name to be desensitized.
     * @return The desensitized English name.
     */
    private String desensitizeEnglishName(String value) {
        // Split the name into parts based on spaces (handles first name, middle name, last name)
        String[] nameParts = value.split(" ");
        if (nameParts.length == 1) {
            // If there is only one part (e.g., "John"), mask the last name with asterisks.
            return nameParts[0] + " *****";
        } else {
            // The first name(s) are visible, and the last name is replaced with asterisks.
            StringBuilder maskedName = new StringBuilder();
            for (int i = 0; i < nameParts.length - 1; i++) {
                maskedName.append(nameParts[i]).append(" "); // Append first and middle names
            }
            // Mask the last name with asterisks
            maskedName.append(repeat("*", nameParts[nameParts.length - 1].length()));
            return maskedName.toString();
        }
    }

    /**
     * Repeats a string (character) a specified number of times.
     * This method is used to generate asterisks for name masking.
     *
     * @param str   The string (e.g., "*") to repeat.
     * @param count The number of times to repeat the string.
     * @return A new string consisting of the repeated characters.
     */
    private String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
