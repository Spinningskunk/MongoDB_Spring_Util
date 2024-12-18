package com.ikun.cm.mongo.repository;

import com.ikun.cm.pojo.User;
import com.ikun.cm.mongo.service.custom.UserRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    // 可以添加自定义查询方法

    /**
     * 测试自定义方法1
     * @return
     */
    List<User> customGetAll();
}
