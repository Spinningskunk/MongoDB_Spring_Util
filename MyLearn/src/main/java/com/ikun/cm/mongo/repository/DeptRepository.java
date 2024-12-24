package com.ikun.cm.mongo.repository;

import com.ikun.cm.pojo.Dept;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HeKun
 * @date: 2024/11/7 00:43
 * @description: Repository interface for interacting with the `Dept` entity in MongoDB.
 *               This interface extends `MongoRepository` to provide basic CRUD operations.
 *               It handles the `Dept` entity, which represents departments within the system.
 */
public interface DeptRepository extends MongoRepository<Dept, String> {
    // No custom methods defined yet. You can add additional methods here for specific queries.
}
