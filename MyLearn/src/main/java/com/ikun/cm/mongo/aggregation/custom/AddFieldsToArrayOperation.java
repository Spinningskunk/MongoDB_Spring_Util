package com.ikun.cm.mongo.aggregation.custom;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/27 14:34
 * @description:
 */
public class AddFieldsToArrayOperation implements AggregationOperation {

    // The aim field that we will add fields to,which is an array.
    private final String arrayField;

    // A list of field names that we will add to the array.
    private final List<String> fieldNames;

    /**
     * Constructor for AddFieldsToArrayOperation.
     * @param arrayField The aim field that we will add fields to,which is an array.
     * @param fieldNames A list of field names that we will add to the array.
     */
    public AddFieldsToArrayOperation(String arrayField, List<String> fieldNames) {
        this.arrayField = arrayField;
        this.fieldNames = fieldNames;
    }

    /**
     * Converts the aggregation operation into a MongoDB Document.
     * This method builds the corresponding MongoDB aggregation pipeline stage for adding fields to the array.
     *
     * @param context The context in which the aggregation operation is being executed.
     * @return A Document representing the $addFields aggregation stage.
     */
    @Override
    public Document toDocument(AggregationOperationContext context) {
        // Create a new Document to hold the fields that will be added to the array
        Document newObject = new Document();
        for (String fieldName : fieldNames) {
            newObject.append(fieldName, "$" + fieldName);
        }

        // Construct the $addFields stage, which adds the new fields to the specified array
        return new Document("$addFields", new Document(arrayField,
                new Document("$concatArrays", Arrays.asList(
                        "$" + arrayField,
                        Arrays.asList(newObject)
                ))
        ));
    }
}
