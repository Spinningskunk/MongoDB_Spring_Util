package com.ikun.cm.mongo.desensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ikun.cm.mongo.annotation.SensitiveField;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * A custom serializer for desensitizing sensitive fields in a Java object during JSON serialization.
 * This serializer checks for fields annotated with @SensitiveField and applies the corresponding
 * desensitization strategy defined by the annotation.
 *
 * @author: HeKun
 * @date: 2024/12/24 02:15
 * @date: 2024/12/24
 */
public class GenericSensitiveSerializer extends JsonSerializer<Object> {

    /**
     * Serializes the provided object by desensitizing sensitive fields.
     * For fields annotated with @SensitiveField, the corresponding desensitization strategy will be applied.
     *
     * For example:
     * - Masking phone numbers: "12345678901" -> "123xxxxxxxxxx"
     * - Masking identification card numbers: "123456789012345678" -> "123456xxxxxxxxxx"
     * - The way the field is desensitized is determined by the @SensitiveField annotation and the
     *   custom serialization logic for that field's desensitization strategy.
     *
     * @param value The object to be serialized, which may contain sensitive fields.
     * @param gen The JSON generator used for writing JSON output.
     * @param serializers The provider that can be used to get serializers for other types.
     * @throws IOException If an I/O error occurs while writing JSON.
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // If the value is null, write null to the JSON output.
        if (value == null) {
            gen.writeNull();
            return;
        }

        // Begin serializing the object as a JSON object.
        gen.writeStartObject();

        // Iterate over all fields of the object and apply desensitization if needed.
        for (Field field : value.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // Allow access to private/protected fields.

            // Check if the field is annotated with @SensitiveField.
            if (field.isAnnotationPresent(SensitiveField.class)) {
                SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
                SensitiveDataStrategy strategy;

                try {
                    // Instantiate the desensitization strategy defined in the annotation.
                    strategy = sensitiveField.strategy().getDeclaredConstructor().newInstance();
                    Object fieldValue = null;

                    try {
                        // Retrieve the field value via reflection.
                        fieldValue = field.get(value);
                    } catch (IllegalAccessException e) {
                        // Log and skip if the field cannot be accessed.
                        System.err.println("Failed to access field: " + field.getName());
                        gen.writeObjectField(field.getName(), null);  // Write null if access fails.
                        continue;
                    }

                    // Apply the desensitization strategy to the field value.
                    Object maskedValue = strategy.mask(field, value);

                    // Write the desensitized value to the JSON output.
                    gen.writeObjectField(field.getName(), maskedValue);
                } catch (Exception e) {
                    // Log and handle any exceptions (e.g., if the strategy can't be instantiated).
                    System.err.println("Error applying sensitive data strategy: " + e.getMessage());
                    try {
                        // If an error occurs, write the original field value to the JSON output.
                        gen.writeObjectField(field.getName(), field.get(value));
                    } catch (IllegalAccessException ex) {
                        // If field access fails again, write null.
                        System.err.println("Failed to access field value: " + ex.getMessage());
                        gen.writeObjectField(field.getName(), null);
                    }
                }
            } else {
                // If the field is not annotated with @SensitiveField, serialize the original field value.
                try {
                    gen.writeObjectField(field.getName(), field.get(value));
                } catch (IllegalAccessException e) {
                    // Log and skip if the field cannot be accessed.
                    System.err.println("Failed to access field: " + field.getName());
                    gen.writeObjectField(field.getName(), null);  // Write null if access fails.
                }
            }
        }

        // End the JSON object serialization.
        gen.writeEndObject();
    }
}
