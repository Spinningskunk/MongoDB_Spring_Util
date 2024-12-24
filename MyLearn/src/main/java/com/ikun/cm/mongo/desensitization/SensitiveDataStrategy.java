package com.ikun.cm.mongo.desensitization;

import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:43
 * @description:
 *
 * Interface for defining custom strategies to desensitize sensitive data in fields.
 *
 * Implementations of this interface are used to define how specific fields (e.g.,
 * phone numbers, identification numbers, names) should be masked or transformed
 * during serialization. The masking or transformation logic will be applied based
 * on the specific field's annotation and custom desensitization strategy.
 *
 * Example of usage:
 *
 * public class NameMaskStrategy implements SensitiveDataStrategy {
 *     @Override
 *     public Object mask(Field field, Object object) {
 *         // Apply masking logic for the 'name' field
 *         return "John *****";
 *     }
 * }
 *
 * The `mask` method is responsible for processing the value of the field in the given
 * object and returning the desensitized version of the value.
 *
 * Each desensitization strategy should implement the `mask` method to apply the desired
 * transformation or masking based on the field's type and the specific desensitization
 * rules for that field.
 */
public interface SensitiveDataStrategy {

    /**
     * Applies a desensitization strategy to the specified field in the given object.
     *
     * @param field The field to be desensitized.
     * @param object The object containing the field.
     * @return The desensitized value of the field.
     */
    Object mask(Field field, Object object);
}
