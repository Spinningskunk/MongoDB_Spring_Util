package com.ikun.cm.controller;

import com.ikun.cm.mongo.controller.GenericController;
import com.ikun.cm.pojo.User;
import com.ikun.cm.mongo.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/31 00:46
 * @description: 测试的用户接口
 */
@RequestMapping("/custom/users")
@RestController("customUserController")
public class UserController extends GenericController<User, String, UserServiceImpl> {

    private final UserServiceImpl userService;
    public UserController(UserServiceImpl userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping("/custom")
    public List<User> getCustomUser(){
        return userService.getCustomUser();
    }
}
