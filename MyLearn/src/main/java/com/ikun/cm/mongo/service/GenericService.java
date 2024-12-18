package com.ikun.cm.mongo.service;

/**
 * @author: HeKun
 * @date: 2024/10/30 22:55
 * @description:
 */
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<T, ID> {
    /**
     * 获取所有数据 并且组装为一个集合
     * @return List<T>
     */
    List<T> findAll();

    /**
     * 分页查询Page<T>
     * @param pageable
     * @return Page<T>
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 保存实体类
     * 注意  此处是用save而非insert实现的 所以如果实体类的_id是有属性的 可以达到更新的效果
     * @param entity
     * @return T
     */
    T save(T entity);

    /**
     * 根据id获取
     * @param entityId
     * @return
     */
    T getById(ID entityId);


    /**
     * 批量插入
     * 请注意 此处我们用的是insert而非save
     * @param entities
     */
    void batchInsert(List<T> entities);

    /**
     * 根据id删除
     * 如果这个类有假删除字段 我们将会以修改代替删除
     * @param id
     */
    void deleteById(ID id);

    /**
     * 根据业务id假删除业务数据
     * @param id
     */
    void fakeDeleteById(ID id);

    /**
     * 恢复假删除的数据
     * @param id
     */
    void recoverFakeDeleteDataById(ID id);

    /**
     * 这里可以传入String的 合理的bson风格的criteria
     * @param queryDoc
     * @return List<T>
     */
    List<T> dangerousQuery(Document queryDoc);

    /**
     * 根据id判断是否存在
     * @param id
     * @return
     */
    Boolean existById(ID id);

    /**
     * 根据传进来的字段去更新字段
     * 没传进来的字段不更新
     * 如果字段齐全 则直接全部更新
     * @param inputEntity
     */
    void updateByInputField(T inputEntity);


    //todo 1、实现更多增删改查的方法
    //     2、一些注解的使用 比如模糊查询、删除的时候软删除字段
}

