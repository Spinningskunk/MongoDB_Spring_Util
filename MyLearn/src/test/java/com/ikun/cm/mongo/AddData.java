package com.ikun.cm.mongo;

import com.alibaba.fastjson.JSONObject;
import com.ikun.cm.mongo.aggregation.custom.SwitchCaseAggregationOperation;
import com.ikun.cm.pojo.StatisticData;
import com.ikun.cm.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: HeKun
 * @date: 2024/10/26 12:14
 * @description:
 */
@SpringBootTest
public class AddData {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void AddData1(){
        StatisticData statisticData = new StatisticData();
        statisticData.set_id(UUIDUtil.getUUid(16,false));
        statisticData.setName("");

    }

    @Test
    void Test2(){
        Map<String,Object> caseMap = new HashMap<>();
        caseMap.put("A",100);
        caseMap.put("B",90);
        caseMap.put("C",80);
        caseMap.put("D",70);
        caseMap.put("E",60);
        caseMap.put("F",50);
        SwitchCaseAggregationOperation customCaseAggregation = new SwitchCaseAggregationOperation("kind","caseResult",caseMap,0);
        Aggregation aggregation = Aggregation.newAggregation(
            customCaseAggregation
        );
        List<JSONObject> testData = new ArrayList<>(mongoTemplate.aggregate(aggregation,JSONObject.class,JSONObject.class).getMappedResults());
    }

}
