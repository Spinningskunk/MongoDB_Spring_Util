package com.ikun.cm.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author: HeKun
 * @date: 2024/12/27 01:22
 * @description: Utility class for generating UUIDs with customizable length and optional hyphens.
 */
@Slf4j
public class UUIDUtil {
    /**
     * Generates a UUID with the specified length and optional hyphens.
     * The length must be a multiple of 4 to ensure the correct segment structure.
     *
     * @param length         The length of the generated UUID, which must be a multiple of 4.
     *                       This is because each segment of the UUID is 8 characters long (4 bytes),
     *                       and the total length must be divisible by 4 to maintain consistent segment sizes.
     * @param includeHyphens A boolean flag indicating whether the UUID should include hyphens.
     * @return A string representing the generated UUID.
     * @throws IllegalArgumentException If the length is not a multiple of 4.
     */
    public static String getUUid(int length, boolean includeHyphens) {
        // Validate that the length is a multiple of 4 to ensure proper segment structure
        if (length % 4 != 0) {
            throw new IllegalArgumentException("UUID length must be a multiple of 4.");
        }

        // Calculate the number of segments based on the length
        int segments = length / 4;
        StringBuilder uuidBuilder = new StringBuilder();

        // Generate each segment of the UUID
        for (int i = 0; i < segments; i++) {
            // Extract the first 8 characters from a randomly generated UUID
            String segment = UUID.randomUUID().toString().substring(0, 8);
            uuidBuilder.append(segment);

            // Append a hyphen if required, and it's not the last segment
            if (includeHyphens && i < segments - 1) {
                uuidBuilder.append("-");
            }
        }

        return uuidBuilder.toString();
    }

    public static void main(String[] args) {
        // Example usage: generate a 12-character UUID without hyphens
        log.info(getUUid(12, false));
    }
}
