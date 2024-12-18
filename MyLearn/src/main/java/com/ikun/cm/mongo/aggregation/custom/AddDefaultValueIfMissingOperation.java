package com.ikun.cm.mongo.aggregation.custom;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author: HeKun
 * @date: 2023/8/2 9:54
 * @description: 低版本spring-mongo可用
 * 这个功能是 如果一个对象中没有这个字段 我们就可以给他赛一个默认值
 * 从而避免后面你根据这个字段作为cond的时候 没有这个字段的数据不走逻辑
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
        // 如果确实没有这个字段 就push为我们给定的值
        return new Document("$addFields", new Document(field,
                new Document("$ifNull", Arrays.asList("$" + field, defaultValue))
        ));
    }
}
