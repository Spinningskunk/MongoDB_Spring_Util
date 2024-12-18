package com.ikun.cm.util;

import java.util.UUID;

/**
 * @author: HeKun
 * @date: 2023/8/2 11:22
 * @description: uuid的使用
 */
public class UUIDUtil {
    /**
     *
     * @param length id长度
     * @param includeHyphens 是否需要连字符
     * @return
     */
    public static String getUUid(int length, boolean includeHyphens) {
        if (length <= 0) {
            throw new IllegalArgumentException("UUID length must be greater than 0.");
        }
        // UUID的长度固定为32个字符
        int segments = (int) Math.ceil((double) length / 32);
        StringBuilder uuidBuilder = new StringBuilder();

        for (int i = 0; i < segments; i++) {
            // 移除连字符
            String segment = UUID.randomUUID().toString().replace("-", "");
            int charsToAppend = Math.min(length - uuidBuilder.length(), segment.length());
            uuidBuilder.append(segment, 0, charsToAppend);
        }

        if (includeHyphens) {
            insertHyphens(uuidBuilder);
        }

        return uuidBuilder.toString();
    }

    /**
     * 连字符
     * @param uuidBuilder
     */
    private static void insertHyphens(StringBuilder uuidBuilder) {
        int len = uuidBuilder.length();
        for (int i = 8; i < len; i += 9) {
            uuidBuilder.insert(i, "-");
        }
    }

    public static void main(String[] args) {
        System.out.println(getUUid(12,false));
    }
}
