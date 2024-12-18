package com.ikun.cm.mongo.aggregation.custom;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/8/2 9:54
 * @description: It's Available for lower versions of spring-mongo.
 *
 * This operation is used to assign a default value to a field in a MongoDB document.
 * hen the field is missing. In many NoSQL scenarios, certain fields might be absent
 * in some documents, which can lead to issues in processing or querying the data.
 * If the expected field is missing, this operation will assign it a default value (or null if none is provided).
 * This can help ensure the integrity of data processing without failing due to missing fields.
 *
 * The appropriateness and impact of this operation can be reviewed in the accompanying
 * `CheckLookUpIdOperation` class in the current directory.
 */
public class AddDefaultValueIfMissingOperation implements AggregationOperation {
    private final String field;
    private final String defaultValue;

    public AddDefaultValueIfMissingOperation(String field, String defaultValue) {
        this.field = field;
        this.defaultValue = defaultValue;
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        // If the specified field is missing, it assigns the provided default value.
        return new Document("$addFields", new Document(field,
                new Document("$ifNull", Arrays.asList("$" + field, defaultValue))
        ));
    }
}
