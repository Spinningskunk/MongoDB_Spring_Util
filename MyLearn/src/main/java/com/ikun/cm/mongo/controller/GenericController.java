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
     * Get the entire entity class according to the id.
     * @param entityId
     * @return
     */
    @GetMapping("getById")
    public ApiResponseDTO<T> getById(@RequestParam(name = "entityId")@ApiParam(value = "entityId",name = "业务的_id") ID entityId) {
        return ApiResponseDTO.ok(service.getById(entityId));
    }


    /**
     * Retrieves all entities and returns them as a list.
     * @return ApiResponseDTO containing a list of all entities.
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
     * Saves an entity.
     * This method uses `save` instead of `insert`, so if the entity already has an ID, it will perform an update.
     * @param entity The entity to save or update.
     * @return ApiResponseDTO containing the saved or updated entity.
     */
    @PostMapping("/create")
    public ApiResponseDTO<T> create(@RequestBody T entity) {
        return ApiResponseDTO.ok(service.save(entity));
    }

    /**
     * Executes a potentially dangerous query.
     * You can pass a valid BSON-style criteria document as a string.
     * @param document The BSON query document.
     * @return ApiResponseDTO containing the result of the query.
     */
    @PostMapping("/dangerousQuery")
    public ApiResponseDTO<List<T>> dangerousQuery(@RequestBody Document document) {
        return ApiResponseDTO.ok(service.dangerousQuery(document));
    }


    /**
     * Deletes an entity by its ID (performs a physical delete).
     * @param _id The ID of the entity to delete.
     * @return ApiResponseDTO indicating the operation was successful.
     */
    @GetMapping("/deleteByID")
    public ApiResponseDTO realDeleteByID(@ApiParam(value = "业务数据的id 是真删除(物理)") @RequestParam(value = "_id") ID _id) {
        service.deleteById(_id);
        return ApiResponseDTO.ok(true);
    }

    /**
     * Performs a fake delete (soft delete) by marking the entity as deleted but not removing it from the database.
     * @param _id The ID of the entity to fake delete.
     * @return ApiResponseDTO indicating the operation was successful.
     */
    @GetMapping("/fakeDeleteByID")
    public ApiResponseDTO fakeDeleteByID(@ApiParam(value = "业务数据的id 是假删除") @RequestParam(value = "_id") ID _id) {
        service.fakeDeleteById(_id);
        return ApiResponseDTO.ok(true);
    }


}
