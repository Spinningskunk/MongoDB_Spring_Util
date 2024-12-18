package com.ikun.cm.mongo.repository;

import com.ikun.cm.pojo.StatisticData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HeKun
 * @date: 2024/10/30 23:45
 * @description:
 */
public interface StatisticRepository extends MongoRepository<StatisticData, String> {
}
