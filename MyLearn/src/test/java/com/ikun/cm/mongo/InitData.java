package com.ikun.cm.mongo;

import com.alibaba.fastjson.JSONObject;
import com.ikun.cm.controller.UserController;
import com.ikun.cm.pojo.User;
import com.ikun.cm.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author: HeKun
 * @date: 2024/12/16 22:40
 * @description:
 */
@SpringBootTest
public class InitData {

    @Autowired
    MongoTemplate mongoTemplate;


    @Autowired
    private UserController userController;

    @Test
    public void testAop() {
        // 调用目标方法
        List<User> users = userController.getCustomUser();
        assertNotNull(users);
    }


}
