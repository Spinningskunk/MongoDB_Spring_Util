package com.ikun.cm.controller;

import com.ikun.cm.mongo.controller.GenericController;
import com.ikun.cm.pojo.StatisticData;
import com.ikun.cm.mongo.impl.StatisticServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HeKun
 * @date: 2024/10/31 00:48
 * @description: 测试统计分析的controller
 */
@RequestMapping("/custom/statistic")
@RestController("customStatisticController")
public class StatisticController extends GenericController<StatisticData, String, StatisticServiceImpl> {

    public StatisticController(StatisticServiceImpl statisticService) {
        super(statisticService);
    }
}
