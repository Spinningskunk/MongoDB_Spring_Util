package com.ikun.cm.mongo.impl;

import com.ikun.cm.mongo.repository.UserRepository;
import com.ikun.cm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, String>  {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(@Qualifier("userRepository")UserRepository userRepository) {
        // 将 userRepository 保存为成员变量
        this.userRepository = userRepository;
    }

    @Override
    protected MongoRepository<User, String> getRepository() {
        return userRepository;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public List<User> getCustomUser() {
        // 调用自定义方法
        return userRepository.customGetAll();
    }

}
