package com.ikun.cm.mongo.desensitization;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:43
 * @description:
 */
public interface SensitiveDataStrategy {

    /**
     * The desensitization strategy we will use.
     * @param value
     * @return
     */
    String desensitizationStrategy(String value);

}
