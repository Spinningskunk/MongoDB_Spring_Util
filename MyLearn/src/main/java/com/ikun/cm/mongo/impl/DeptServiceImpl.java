package com.ikun.cm.mongo.impl;

import com.ikun.cm.mongo.repository.DeptRepository;
import com.ikun.cm.pojo.Dept;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/**
 * @author: HeKun
 * @date: 2024/11/7 0:44
 * @description:
 */
@Service
public class DeptServiceImpl extends GenericServiceImpl<Dept, String>{

    private final DeptRepository deptRepository;

    public DeptServiceImpl(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    @Override
    protected Class<Dept> getEntityClass() {
        return Dept.class;
    }

    @Override
    protected MongoRepository<Dept, String> getRepository() {
        return deptRepository;
    }
}
