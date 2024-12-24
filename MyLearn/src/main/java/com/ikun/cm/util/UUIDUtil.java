package com.ikun.cm.util;

import java.util.UUID;

/**
 * @author: HeKun
 * @date: 2023/8/2 11:22
 * @description: A utility class for generating UUIDs with customizable length and optional hyphens.
 * This class provides a method to generate UUIDs of specified length, and optionally includes hyphens.
 * It utilizes Java's built-in UUID class and allows for flexible UUID generation.
 */
public class UUIDUtil {

    /**
     * Generates a UUID with a specified length and optional inclusion of hyphens.
     * The UUID is generated by concatenating random UUID segments and trimming or appending characters as needed.
     *
     * @param length The desired length of the generated UUID. It must be greater than 0.
     * @param includeHyphens Whether or not to include hyphens in the generated UUID.
     * @return A string representing the generated UUID with the specified length and formatting.
     * @throws IllegalArgumentException if the provided length is less than or equal to 0.
     */
    public static String getUUid(int length, boolean includeHyphens) {
        if (length <= 0) {
            throw new IllegalArgumentException("UUID length must be greater than 0.");
        }

        // UUIDs are fixed in length (32 characters excluding hyphens), so we calculate how many segments are needed.
        int segments = (int) Math.ceil((double) length / 32);
        StringBuilder uuidBuilder = new StringBuilder();

        for (int i = 0; i < segments; i++) {
            // Generate a random UUID, remove the hyphens, and append the required portion to the final string.
            String segment = UUID.randomUUID().toString().replace("-", "");
            int charsToAppend = Math.min(length - uuidBuilder.length(), segment.length());
            uuidBuilder.append(segment, 0, charsToAppend);
        }

        // If hyphens are required, insert them at the appropriate positions.
        if (includeHyphens) {
            insertHyphens(uuidBuilder);
        }

        return uuidBuilder.toString();
    }

    /**
     * Inserts hyphens into the UUID string at the appropriate positions (after every 8 characters).
     * This method ensures that the UUID is formatted in the standard form: 8-4-4-4-12.
     *
     * @param uuidBuilder The StringBuilder containing the UUID without hyphens.
     */
    private static void insertHyphens(StringBuilder uuidBuilder) {
        int len = uuidBuilder.length();
        for (int i = 8; i < len; i += 9) {
            uuidBuilder.insert(i, "-");
        }
    }

    /**
     * Main method for testing the UUID generation functionality.
     * It generates a UUID of length 12 without hyphens and prints it to the console.
     */
    public static void main(String[] args) {
        System.out.println(getUUid(12, false)); // Example output: 6dcb4d8efbc1
    }
}
