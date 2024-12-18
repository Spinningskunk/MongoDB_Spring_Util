package com.ikun.cm.mongo.repository;

import com.ikun.cm.pojo.Dept;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HeKun
 * @date: 2024/11/7 0:43
 * @description:
 */
public interface DeptRepository extends MongoRepository<Dept, String> {

}
