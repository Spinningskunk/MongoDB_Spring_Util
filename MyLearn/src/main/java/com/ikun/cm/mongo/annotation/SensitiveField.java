package com.ikun.cm.mongo.annotation;

import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:38
 * @description:
 */
@Target(ElementType.FIELD)  // The field we want to annotate
@Retention(RetentionPolicy.RUNTIME)  // Annotation will be available at runtime
public @interface SensitiveField {

    /**
     * Witch kind of strategy to use
     * @return
     */
    Class<? extends SensitiveDataStrategy> strategy();
}