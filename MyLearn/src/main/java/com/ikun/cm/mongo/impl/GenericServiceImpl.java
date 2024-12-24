package com.ikun.cm.mongo.impl;

import com.ikun.cm.mongo.criteria.CriteriaBuilder;
import com.ikun.cm.mongo.exception.CustomBusinessException;
import com.ikun.cm.mongo.exception.EntityNotFoundException;
import com.ikun.cm.mongo.service.GenericService;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: HeKun
 * @date: 2024/10/31 00:24
 * @description: A generic service implementation that provides common CRUD operations.
 * This class includes methods for handling data with logical deletion (soft delete) and batch insertion.
 * It also supports handling entities with or without a "deleted" field for logical deletion functionality.
 */
@Service
public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * This abstract method will be implemented by Spring to provide the entity's class type.
     * It's required due to Java's generic type erasure.
     *
     * @return The class of the entity type.
     */
    @Lookup
    protected abstract Class<T> getEntityClass();

    protected abstract MongoRepository<T, ID> getRepository();

    /**
     * Caches whether a class contains a "deleted" field to optimize query performance.
     * If the class extends this base class, it may include a "deleted" field for soft deletion.
     * The presence of this field is checked only once and the result is cached for future queries.
     */
    private static final Map<Class<?>, Boolean> deletedFieldCache = new ConcurrentHashMap<>();

    /**
     * Creates a query criteria for entities that do not have the "deleted" field.
     * If the entity has the "deleted" field, the query will include a check to ensure it is not logically deleted.
     *
     * @param entityClass The class of the entity to check for a "deleted" field.
     * @return A Criteria object to filter out logically deleted entities if the "deleted" field exists.
     */
    private Criteria createCriteriaWithNoDeleted(Class<?> entityClass) {
        Boolean hasDeletedField = hasDeletedField(entityClass);
        if (Boolean.TRUE.equals(hasDeletedField)) {
            return Criteria.where("deleted").is(false);
        }
        return new Criteria(); // Return empty criteria if no "deleted" field.
    }

    /**
     * Checks if the entity class contains a "deleted" field.
     * The result is cached to avoid redundant reflection checks.
     *
     * @param entityClass The class of the entity to check.
     * @return True if the entity contains a "deleted" field, false otherwise.
     */
    private boolean hasDeletedField(Class<?> entityClass) {
        Boolean hasDeletedField = deletedFieldCache.get(entityClass);
        if (hasDeletedField == null) {
            hasDeletedField = false;
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("deleted")) {
                    hasDeletedField = true;
                    break;
                }
            }
            deletedFieldCache.put(entityClass, hasDeletedField);
        }
        return hasDeletedField;
    }

    @Override
    public List<T> findAll() {
        // Apply logical deletion filter if the "deleted" field exists
        Criteria isFakeDel = createCriteriaWithNoDeleted(getEntityClass());
        if (hasDeletedField(getEntityClass())) {
            return mongoTemplate.find(Query.query(isFakeDel), getEntityClass());
        } else {
            return getRepository().findAll();
        }
    }

    /**
     * Retrieves all entities in a paginated format.
     *
     * @param pageable The pagination information.
     * @return A Page object containing the entities.
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T getById(ID entityId) {
        return getRepository()
                .findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + entityId + " not found"));
    }

    @Override
    public void batchInsert(List<T> entities) {
        mongoTemplate.insert(entities, getEntityClass());
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * Performs a logical (soft) delete by marking the "deleted" field as true.
     * If the entity class does not have the "deleted" field, an exception is thrown.
     *
     * @param id The ID of the entity to soft delete.
     */
    @Override
    public void fakeDeleteById(ID id) {
        if (!hasDeletedField(getEntityClass())) {
            throw new CustomBusinessException("The class does not contain a 'deleted' field, logical deletion is not possible.");
        }

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("deleted", true);
        UpdateResult result = mongoTemplate.updateFirst(query, update, getEntityClass());

        if (result.getMatchedCount() == 0) {
            throw new CustomBusinessException("No entity found with ID " + id + " for logical deletion.");
        }
    }

    @Override
    public void recoverFakeDeleteDataById(ID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("deleted", false);
        UpdateResult result = mongoTemplate.updateFirst(query, update, getEntityClass());

        if (result.getMatchedCount() == 0) {
            throw new CustomBusinessException("No entity found with ID " + id + " to recover from logical deletion.");
        }
    }

    @Override
    public List<T> dangerousQuery(Document queryDoc) {
        Query query = new Query();
        query.addCriteria(CriteriaBuilder.fromDocument(queryDoc));
        return mongoTemplate.find(query, getEntityClass());
    }

    @Override
    public Boolean existById(ID id) {
        return getRepository().existsById(id);
    }

    /**
     * Updates an entity by matching its ID and setting the provided input fields.
     * This method does not update the ID or the "deleted" field.
     *
     * @param inputEntity The entity with the new field values.
     */
    @Override
    public void updateByInputField(T inputEntity) {
        try {
            Field idField = inputEntity.getClass().getDeclaredField("_id");
            idField.setAccessible(true);
            Object idValue = idField.get(inputEntity);

            if (idValue == null) {
                throw new CustomBusinessException("The ID of the entity cannot be null.");
            }

            Query query = new Query(Criteria.where("_id").is(idValue));
            Update update = new Update();

            // Iterate over the fields and update non-ID and non-"deleted" fields
            Field[] fields = inputEntity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(inputEntity);
                if (value != null && !"id".equals(field.getName()) && !"deleted".equals(field.getName())) {
                    update.set(field.getName(), value);
                }
            }

            mongoTemplate.updateFirst(query, update, getEntityClass());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CustomBusinessException("Error updating data: " + e.getMessage(), e);
        }
    }
}
