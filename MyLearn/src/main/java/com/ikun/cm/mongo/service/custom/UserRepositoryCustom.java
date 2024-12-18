package com.ikun.cm.mongo.service.custom;

import com.ikun.cm.pojo.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> customGetAll();
}
