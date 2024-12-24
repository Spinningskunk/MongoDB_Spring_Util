package com.ikun.cm.controller;

import com.ikun.cm.mongo.controller.GenericController;
import com.ikun.cm.mongo.desensitization.SensitiveResponse;
import com.ikun.cm.pojo.User;
import com.ikun.cm.mongo.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/31 00:46
 * @description: A controller for handling user-related API requests, including the customization of user data retrieval.
 */
@RequestMapping("/custom/users")  // Define the base URL path for user-related endpoints.
@RestController("customUserController")  // Marks this class as a Spring MVC controller and automatically registers it.
public class UserController extends GenericController<User, String, UserServiceImpl> {

    private final UserServiceImpl userService;  // Injecting the UserServiceImpl service layer.

    /**
     * Constructor to initialize the controller with the service.
     *
     * @param userService The user service implementation used for handling user data.
     */
    public UserController(UserServiceImpl userService) {
        super(userService);  // Pass the userService to the generic parent controller for base functionality.
        this.userService = userService;
    }

    /**
     * This method returns a list of custom users, applying desensitization to sensitive fields if necessary.
     *
     * @return A list of User objects with potentially desensitized sensitive data.
     */
    @SensitiveResponse  // Marks this method to indicate that the response should be desensitized.
    @GetMapping("/custom")  // Handle GET requests to "/custom/users/custom" endpoint.
    public List<User> getCustomUser() {
        return userService.getCustomUser();  // Call the service to retrieve the customized list of users.
    }
}
