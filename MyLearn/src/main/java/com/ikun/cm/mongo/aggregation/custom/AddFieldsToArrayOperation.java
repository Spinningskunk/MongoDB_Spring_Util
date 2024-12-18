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
    private final String arrayField;
    private final List<String> fieldNames;


    public AddFieldsToArrayOperation(String arrayField, List<String> fieldNames) {
        this.arrayField = arrayField;
        this.fieldNames = fieldNames;
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        // 创建Document对象
        Document newObject = new Document();
        for (String fieldName : fieldNames) {
            newObject.append(fieldName, "$" + fieldName);
        }

        // 构造要往数组中插入的对应
        return new Document("$addFields", new Document(arrayField,
                new Document("$concatArrays", Arrays.asList(
                        "$" + arrayField,
                        Arrays.asList(newObject)
                ))
        ));
    }
}
