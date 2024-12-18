package com.ikun.cm.mongo.controller;

import com.ikun.cm.mongo.service.GenericService;
import com.ikun.cm.pojo.base.ApiResponseDTO;
import io.swagger.annotations.ApiParam;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/31 00:45
 * @description: A generic controller for base crud,and I will make more useful function in the future.
 * just in order to lean english, l may use my poor english somewhere,just ignore it,haha.
 */
public abstract class GenericController<T, ID, S extends GenericService<T, ID>> {

    protected final S service;


    public GenericController(S service) {
        this.service = service;
    }


    /**
     * 根据id获取整个实体类
     * @param entityId
     * @return
     */
    @GetMapping("getById")
    public ApiResponseDTO<T> getById(@RequestParam(name = "entityId")@ApiParam(value = "entityId",name = "业务的_id") ID entityId) {
        return ApiResponseDTO.ok(service.getById(entityId));
    }


    /**
     * 获取所有数据 并且组装为一个集合
     * @return List<T>
     */
    @GetMapping("getAll")
    public ApiResponseDTO<List<T>> getAll() {
        return ApiResponseDTO.ok(service.findAll());
    }

    /**
     * 分页查询
     * @param pageable
     * @return Page<T>
     */
    @GetMapping("/page")
    public ApiResponseDTO<Page<T>> getAll(Pageable pageable) {
        return ApiResponseDTO.ok(service.findAll(pageable));
    }

    /**
     * 保存实体类
     * 注意  此处是用save而非insert实现的 所以如果实体类的_id是有属性的 可以达到更新的效果
     * @param entity
     * @return
     */
    @PostMapping("/create")
    public ApiResponseDTO<T> create(@RequestBody T entity) {
        return ApiResponseDTO.ok(service.save(entity));
    }

    /**
     * 这里可以传入String的 合理的bson风格的criteria
     * @param document
     * @return
     */
    @PostMapping("/dangerousQuery")
    public ApiResponseDTO<List<T>> dangerousQuery(@RequestBody Document document) {
        return ApiResponseDTO.ok(service.dangerousQuery(document));
    }


    /**
     * 根据id删除数据
     * @param _id
     */
    @GetMapping("/deleteByID")
    public ApiResponseDTO realDeleteByID(@ApiParam(value = "业务数据的id 是真删除(物理)") @RequestParam(value = "_id") ID _id) {
        service.deleteById(_id);
        return ApiResponseDTO.ok(true);
    }

    /**
     * 根据id删除数据
     * @param _id
     */
    @GetMapping("/fakeDeleteByID")
    public ApiResponseDTO fakeDeleteByID(@ApiParam(value = "业务数据的id 是假删除") @RequestParam(value = "_id") ID _id) {
        service.fakeDeleteById(_id);
        return ApiResponseDTO.ok(true);
    }


}
