package com.ikun.cm.controller;

import com.ikun.cm.mongo.controller.GenericController;
import com.ikun.cm.mongo.impl.DeptServiceImpl;
import com.ikun.cm.pojo.Dept;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HeKun
 * @date: 2024/11/7 0:46
 * @description:
 */
@RequestMapping("/custom/dept")
@RestController("deptController")
public class DeptController extends GenericController<Dept, String, DeptServiceImpl> {

    public DeptController(DeptServiceImpl deptServiceImpl) {
        super(deptServiceImpl);
    }
}
