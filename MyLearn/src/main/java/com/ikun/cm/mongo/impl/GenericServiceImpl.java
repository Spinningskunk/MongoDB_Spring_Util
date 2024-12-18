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
 * @description: 公共的查询实现
 */
@Service
public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 定义一个抽象方法，Spring 会为我们实现这个方法
     * 这样我们就可以在集成的类中重写这个方法
     * 从而获取到当前extends的类的 class了
     * 该死的 泛型擦除啊
     * @return
     */
    @Lookup
    protected abstract Class<T> getEntityClass();

    protected abstract MongoRepository<T, ID> getRepository();

    /**
     * 用于缓存每个类是否包含 "deleted" 字段的信息
     * 我们把软删除字段做到了基类里面 但是不一定所有业务都会用到软删除字段，也不一定所有类都会extends我的基类 所以我们的软删除逻辑要进行判断
     * 如果没有软删除这个字段 那么我们在其查询中就不管他了
     * 但是如果每次都从类里面获取的话 查询又是个高频使用的地方，且一个类的字段会改变的情况很少，软删除的字段更会是在一开始就确定好
     * 所以我们这里使用缓存 将类的字段缓存起来
     */
    private static final Map<Class<?>, Boolean> deletedFieldCache = new ConcurrentHashMap<>();

    private Criteria createCriteriaWithNoDeleted(Class<?> entityClass) {
        // 直接调用已修改的 hasDeletedField 方法，缓存逻辑在其中
        Boolean hasDeletedField = hasDeletedField(entityClass);

        // 根据是否存在 "deleted" 字段来返回查询对象
        if (Boolean.TRUE.equals(hasDeletedField)) {
            return Criteria.where("deleted").is(false);
        }

        return new Criteria();
    }

    /**
     * 检查类中是否存在 "deleted" 字段
     * @param entityClass
     * @return
     */
    private boolean hasDeletedField(Class<?> entityClass) {
        // 从缓存中获取是否存在 "deleted" 字段的结果
        Boolean hasDeletedField = deletedFieldCache.get(entityClass);

        if (hasDeletedField == null) {
            //没有则默认false
            hasDeletedField = false;
            // 如果缓存中没有，则进行检查
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("deleted")) {
                    hasDeletedField = true;
                    break;
                }
            }
            // 将结果存入缓存
            deletedFieldCache.put(entityClass, hasDeletedField);
        }

        return hasDeletedField;
    }


    @Override
    public List<T> findAll() {
        //如果有假删除的字段 就加上假删除的条件
        Criteria isFakeDel = createCriteriaWithNoDeleted(getEntityClass());
        if (hasDeletedField(getEntityClass())){
            return mongoTemplate.find(Query.query(isFakeDel),getEntityClass());
        }else {
            return getRepository().findAll();
        }
    }

    /**
     * Nothing to say,just findAll
     * @param pageable
     * @return
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
     * This method performs a logical delete operation on a document by its ID.
     * We should check if the document contains the "deleted" field,because if the mongodb find this document
     * by given _id, it will return 1 for the match count regardless of whether the "deleted" field exist or not
     *
     * @param id
     */
    @Override
    public void fakeDeleteById(ID id) {
        // 先检查类中是否有 "deleted" 字段
        if (!hasDeletedField(getEntityClass())) {
            throw new CustomBusinessException("类中没有 'deleted' 字段，无法执行逻辑删除！");
        }

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("deleted", true);
        UpdateResult result = mongoTemplate.updateFirst(query, update, getEntityClass());

        if (result.getMatchedCount() == 0) {
            throw new CustomBusinessException("未找到对应ID的数据，无法执行逻辑删除！");
        }
    }

    @Override
    public void recoverFakeDeleteDataById(ID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("deleted", false);
        UpdateResult result = mongoTemplate.updateFirst(query, update, getEntityClass());

        if (result.getMatchedCount() == 0) {
            throw new CustomBusinessException("未找到对应ID的数据，无法恢复逻辑删除的数据！");
        }
    }

    @Override
    public List<T> dangerousQuery(Document queryDoc){
        Query query = new Query();
        query.addCriteria(CriteriaBuilder.fromDocument(queryDoc));
        List<T> dataList =  mongoTemplate.find(query,getEntityClass());
        return dataList;
    }


    @Override
    public Boolean existById(ID id) {
        return getRepository().existsById(id);
    }

    @Override
    public void updateByInputField(T inputEntity) {
        try {
            // 通过反射获取 ID 字段的值
            Field idField = inputEntity.getClass().getDeclaredField("_id");
            idField.setAccessible(true);
            Object idValue = idField.get(inputEntity);

            if (idValue == null) {
                throw new CustomBusinessException("实体对象的 ID 不能为空！");
            }

            Query query = new Query(Criteria.where("_id").is(idValue));
            Update update = new Update();

            // 遍历所有字段，构建 Update 对象
            Field[] fields = inputEntity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(inputEntity);
                // 跳过ID字段、软删除字段 因为软删除字段应该通过恢复接口操作，而非更新接口操作
                if (value != null && !"id".equals(field.getName()) && !"deleted".equals(field.getName())) {
                    update.set(field.getName(), value);
                }
            }
            // 执行更新
            mongoTemplate.updateFirst(query, update, getEntityClass());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CustomBusinessException("更新数据时出错：" + e.getMessage(), e);
        }
    }


}
