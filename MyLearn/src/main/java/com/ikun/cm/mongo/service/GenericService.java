package com.ikun.cm.mongo.service;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/30 22:55
 * @description: Generic service interface for basic CRUD operations.
 *               This interface provides generic methods for any entity type (T) and its ID type (ID).
 *               The implementation should handle all interactions with MongoDB.
 */
public interface GenericService<T, ID> {

    /**
     * Fetch all data and return it as a list.
     * This method retrieves all records from the database and returns them in a collection (List).
     *
     * @return List<T> - A list of all entities of type T.
     */
    List<T> findAll();

    /**
     * Paginated query for fetching data.
     * This method supports pagination and returns a Page object containing a subset of all records.
     *
     * @param pageable - The pagination information (e.g., page number, page size).
     * @return Page<T> - A paginated result of entities of type T.
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Save an entity to the database.
     * If the entity already exists (based on its ID), it will be updated; otherwise, a new record will be inserted.
     *
     * @param entity - The entity to be saved.
     * @return T - The saved or updated entity.
     */
    T save(T entity);

    /**
     * Fetch an entity by its ID.
     * This method retrieves an entity from the database based on its unique identifier (ID).
     *
     * @param entityId - The unique identifier of the entity.
     * @return T - The entity of type T if found, null otherwise.
     */
    T getById(ID entityId);

    /**
     * Batch insert multiple entities into the database.
     * This method inserts a list of entities into MongoDB in one operation.
     * It is more efficient than inserting entities one by one.
     *
     * @param entities - The list of entities to be inserted.
     */
    void batchInsert(List<T> entities);

    /**
     * Delete an entity by its ID.
     * This method deletes an entity from the database by its ID.
     * If the entity has a "soft delete" field, the delete operation will be handled by modifying the entity's status instead.
     *
     * @param id - The ID of the entity to be deleted.
     */
    void deleteById(ID id);

    /**
     * Soft delete an entity by its ID.
     * This method marks an entity as deleted without actually removing it from the database.
     * It allows recovery of the entity later if needed.
     *
     * @param id - The ID of the entity to be soft-deleted.
     */
    void fakeDeleteById(ID id);

    /**
     * Recover a soft-deleted entity by its ID.
     * This method restores an entity that was previously soft-deleted.
     * It restores the entity to its original state.
     *
     * @param id - The ID of the entity to be restored.
     */
    void recoverFakeDeleteDataById(ID id);

    /**
     * Execute a dangerous query with raw BSON-style criteria.
     * This method allows executing raw queries with arbitrary BSON document structures.
     * It is potentially risky and should be used with caution to avoid security risks.
     *
     * @param queryDoc - The BSON document representing the query criteria.
     * @return List<T> - A list of entities that match the query.
     */
    List<T> dangerousQuery(Document queryDoc);

    /**
     * Check if an entity exists by its ID.
     * This method checks whether an entity with the specified ID exists in the database.
     *
     * @param id - The ID of the entity.
     * @return Boolean - True if the entity exists, false otherwise.
     */
    Boolean existById(ID id);

    /**
     * Update specific fields of an entity based on the input.
     * This method allows updating only the fields that are provided in the input entity.
     * Fields not included in the input will not be updated.
     *
     * @param inputEntity - The entity containing the fields to be updated.
     */
    void updateByInputField(T inputEntity);

    // TODO:
    // 1. Implement more advanced CRUD methods, including custom queries, updates, and deletions.
    // 2. Add annotations for special features, such as fuzzy queries or handling soft delete fields.
}
