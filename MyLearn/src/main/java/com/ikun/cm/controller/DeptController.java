package com.ikun.cm.controller;

import com.ikun.cm.mongo.controller.GenericController;
import com.ikun.cm.mongo.impl.DeptServiceImpl;
import com.ikun.cm.pojo.Dept;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HeKun
 * @date: 2024/11/7 0:46
 * @description: A controller class for handling department-related API requests.
 *               This controller provides CRUD operations for the Dept entity.
 */
@RequestMapping("/custom/dept")  // Define the base URL path for department-related endpoints.
@RestController("deptController")  // Marks this class as a Spring MVC controller and automatically registers it as a bean.
public class DeptController extends GenericController<Dept, String, DeptServiceImpl> {

    /**
     * Constructor to initialize the DeptController with the DeptServiceImpl.
     *
     * @param deptServiceImpl The service layer that handles business logic for department data.
     */
    public DeptController(DeptServiceImpl deptServiceImpl) {
        super(deptServiceImpl);  // Pass the deptServiceImpl to the generic parent controller for basic functionality.
    }
}
