package com.ikun.cm.mongo.service.custom;

import com.ikun.cm.mongo.service.AggregationUtilService;
import com.ikun.cm.pojo.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: HeKun
 * @date: 2025/1/7 22:21
 * @description:
 */
@Slf4j
@Service
public class AggregationUtilServiceImpl implements AggregationUtilService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public <T> PageResult<T> getPaginatedResults(Aggregation originalAggregation, Class<?> inputType, Class<T> outputType, int page, int size) {

//        // Build pageable aggregation
//        Aggregation facetAggregation = Aggregation.newAggregation(
//                Aggregation.facet(
//                        Aggregation.count().as("totalCount"),
//                        Aggregation.skip((long) page * size),
//                        Aggregation.limit(size)
//                ).as("result")
//        );
//
//        // 获取聚合结果
//        Aggregation aggregationWithFacet = Aggregation.newAggregation(originalAggregation, facetAggregation);
//
//        // 执行聚合查询
//        Map<String, List<?>> result = mongoTemplate.aggregate(aggregationWithFacet, "collectionName", Map.class).getMappedResults();
//
//        // 处理聚合结果
//        List<T> data = (List<T>) result.get("result");
//        long count = result.containsKey("totalCount") && !result.get("totalCount").isEmpty() ?
//                ((Map<?, ?>) result.get("totalCount").get(0)).get("count") : 0;

        // 返回分页结果
        return new PageResult<>();


    }
}
