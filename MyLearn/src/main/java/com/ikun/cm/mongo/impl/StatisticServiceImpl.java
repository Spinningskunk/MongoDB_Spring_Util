package com.ikun.cm.mongo.impl;

import com.ikun.cm.mongo.repository.StatisticRepository;
import com.ikun.cm.pojo.StatisticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/**
 * @author: HeKun
 * @date: 2024/10/30 23:44
 * @description:
 */
@Service
public class StatisticServiceImpl extends GenericServiceImpl<StatisticData, String> {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticServiceImpl(@Qualifier("statisticRepository")StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    protected MongoRepository<StatisticData, String> getRepository() {
        return statisticRepository;
    }

    @Override
    protected Class<StatisticData> getEntityClass() {
        return StatisticData.class;
    }
}
