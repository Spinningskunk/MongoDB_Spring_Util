package com.ikun.cm.mongo.repository;

import com.ikun.cm.pojo.User;
import com.ikun.cm.mongo.service.custom.UserRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/12/24 02:45
 * @description: Repository interface for interacting with the `User` entity in MongoDB.
 *               This interface extends `MongoRepository` for basic CRUD operations and
 *               `UserRepositoryCustom` for custom query methods.
 */
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    /**
     * Custom method to retrieve all `User` records.
     * This method can be used to fetch all users from the MongoDB collection.
     *
     * @return A list of all `User` objects.
     */
    List<User> customGetAll();
}
