package com.ikun.cm.mongo.service;

import com.ikun.cm.pojo.base.PageResult;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

/**
 * @author: HeKun
 * @date: 2025/1/7 22:18
 * @description: Some util for using aggregation
 */
public interface AggregationUtilService {


    /**
     * Page Date by one times aggregation
     * We can use facet to do this
     * @param originalAggregation the Aggregation we want to use before
     * @param inputType
     * @param outputType
     * @param page
     * @param size
     * @return
     * @param <T>
     */
    public <T> PageResult<T> getPaginatedResults(Aggregation originalAggregation,
                                                 Class<?> inputType,
                                                 Class<T> outputType,
                                                 int page,
                                                 int size);
}
