package com.ikun.cm.mongo.impl.custom;

import com.ikun.cm.mongo.service.custom.UserRepositoryCustom;
import com.ikun.cm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<User> customGetAll() {
        User user = new User();
        user.setName("custom");
        return Arrays.asList(user);
    }
}
