package com.ikun.cm.mongo.desensitization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: HeKun
 * @date: 2024/12/24 02:09
 * @description: Annotation to mark methods that require desensitization of sensitive data in the response.
 *
 * This annotation is applied to methods whose response data should undergo desensitization
 * before being sent back to the client. It is typically used in combination with custom
 * serialization logic to mask or transform sensitive information, such as personal identification
 * numbers, phone numbers, or names, into a less revealing format.
 *
 * Example usage:
 *
 * @SensitiveResponse
 * @GetMapping("/user/{id}")
 * public User getUser(@PathVariable("id") Long id) {
 *     return userService.getUserById(id);
 * }
 *
 * In this case, if the User object contains sensitive fields (e.g., `phoneNumber` or `name`),
 * they will be automatically desensitized before being returned in the API response.
 *
 * The desensitization process may include:
 * - Masking parts of a name, e.g., "John Doe" -> "John *****"
 * - Masking the phone number, e.g., "12345678901" -> "123xxxxxxxxxx"
 * - Masking identification card numbers, e.g., "123456789012345678" -> "123456xxxxxxxxxx"
 * - Of course, the way that filed will be desensitized is defined by the field's annotation @SensitiveField and our custom serialization logic
 */
@Target(ElementType.METHOD)  // Marks the annotation for method-level use
@Retention(RetentionPolicy.RUNTIME)  // Ensures the annotation is available at runtime
public @interface SensitiveResponse {
    // This annotation has no parameters, it is simply used for marking methods for desensitization.
}
