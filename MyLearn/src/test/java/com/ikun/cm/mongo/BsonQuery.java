package com.ikun.cm.mongo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ikun.cm.mongo.aggregation.build.CustomAggregationPipeline;
import com.ikun.cm.mongo.aggregation.build.SafeAggregationPipeline;
import com.ikun.cm.mongo.aggregation.custom.CheckLookUpIdOperation;
import com.ikun.cm.pojo.Dept;
import org.junit.jupiter.api.Test;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/18 14:56
 * @description:
 */
@SpringBootTest
public class BsonQuery {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void str2Query(){
        String str = "{ \"aggregate\" : \"__collection__\", \"pipeline\" : [{ \"$project\" : { \"deptName\" : 1, \"parentDeptId\" : 1, \"createTime\" : 1, \"notes\" : \"$marks\"}}, { \"$group\" : { \"_id\" : \"$parentDeptId\", \"sonCount\" : { \"$sum\" : 1}}}]}";
        Document bsonQuery = Document.parse(str);
        CustomAggregationPipeline customPipeline = new CustomAggregationPipeline(bsonQuery);
        SafeAggregationPipeline safeAggregationOperation = new SafeAggregationPipeline(bsonQuery);
        Aggregation aggregation = Aggregation.newAggregation(safeAggregationOperation.getOperations());
        List<JSONObject> dataList =  mongoTemplate.aggregate(aggregation, JSONObject.class,JSONObject.class).getMappedResults();
    }

    @Test
    public void convert(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("deptName","parentDeptId","createTime")
                        .and("marks").as("notes"),
                Aggregation.group("parentDeptId")
                        .count().as("sonCount")
        );
        String str =  aggregation.toString();
        Document bsonQuery = Document.parse(str);
        CustomAggregationPipeline customPipeline = new CustomAggregationPipeline(bsonQuery);
        Aggregation aggregation1 = Aggregation.newAggregation(customPipeline.getOperations());
        boolean finalQueryStr = aggregation1.toString().equals(str);

    }

    @Test
    public void str2QueryByForCommand(){
        String str = "{ "
                + "\"aggregate\" : \"dept\", "
                + "\"pipeline\" : ["
                + "{ \"$project\" : { \"deptName\" : 1, \"parentDeptId\" : 1, \"createTime\" : 1, \"notes\" : \"$marks\" } }, "
                + "{ \"$group\" : { \"_id\" : \"$parentDeptId\", \"sonCount\" : { \"$sum\" : 1 } } }"
                + "], "
                + "\"cursor\" : {}"
                + "}";
        //在这个bson like的查询sql中 在最后一步我们指定了一个空的游标
        //对于cursor的解释可以看学习文档
        // 解析 BSON 字符串
        Document bsonQuery = Document.parse(str);
        // 执行 MongoDB 命令
        Document result = mongoTemplate.executeCommand(bsonQuery);
        JSONObject queryResult = JSONObject.parseObject(JSON.toJSONString(mongoTemplate.executeCommand(bsonQuery)));
        JSONObject cursor = queryResult.getJSONObject("cursor");
        cursor.getJSONArray("firstBatch");

    }

    @Test
    public void realAgg(){
        Aggregation aggregation = Aggregation.newAggregation(
              Aggregation.project("deptName","parentDeptId","createTime")
                      .and("marks").as("notes"),
              Aggregation.group("parentDeptId")
                      .count().as("sonCount")
        );
        List<JSONObject> dataList =  mongoTemplate.aggregate(aggregation, Dept.class,JSONObject.class).getMappedResults();
    }
    @Test
    public void unSafeLookup(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("lookUp2","lookupId","lookupId","lookupData")
        );
        List<JSONObject> dataList = mongoTemplate.aggregate(aggregation,"lookUp1",JSONObject.class).getMappedResults();
    }

    @Test
    public void safeLookup(){
        CheckLookUpIdOperation checkLookUpIdOperation = new CheckLookUpIdOperation("lookupId");
        Aggregation aggregation = Aggregation.newAggregation(
                checkLookUpIdOperation,
                Aggregation.lookup("lookUp2","lookupId","lookupId","lookupData")
        );
        List<JSONObject> dataList = mongoTemplate.aggregate(aggregation,"lookUp1",JSONObject.class).getMappedResults();
    }

    @Test
    public void missCond(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .and(ConditionalOperators.Cond.when(Criteria.where("lookupId").exists(true)).then(1).otherwise(0)).as("haha")
        );
        List<JSONObject> dataList = mongoTemplate.aggregate(aggregation,"lookUp1",JSONObject.class).getMappedResults();
    }

}
