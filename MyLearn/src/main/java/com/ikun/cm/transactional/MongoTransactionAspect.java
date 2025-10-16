package com.ikun.cm.transactional;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * MongoDB 事务切面实现
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MongoTransactionAspect {

    private final MongoClient mongoClient;

    @Around("@annotation(com.ikun.cm.transactional.MongoTransactional)")
    public Object handleTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        MongoTransactional annotation = signature.getMethod().getAnnotation(MongoTransactional.class);
        Propagation propagation = annotation.propagation();

        boolean newTransaction = false;
        ClientSession session = null;

        try {
            // 1️、判断是否已有事务
            if (propagation == Propagation.REQUIRED && MongoTransactionContext.hasSession()) {
                session = MongoTransactionContext.peek();
                log.debug("Join existing Mongo transaction");
            } else {
                // 新建事务
                session = mongoClient.startSession();
                session.startTransaction();
                MongoTransactionContext.push(session);
                newTransaction = true;
                log.debug("Start new Mongo transaction [{}]", propagation);
            }

            // 2️执行业务方法
            Object result = joinPoint.proceed();

            // 3如果是新事务，则提交
            if (newTransaction) {
                session.commitTransaction();
                log.debug("Commit Mongo transaction");
            }

            return result;
        } catch (Exception e) {
            if (session != null && session.hasActiveTransaction()) {
                session.abortTransaction();
                log.error("Abort Mongo transaction due to error", e);
            }
            throw e;
        } finally {
            if (newTransaction && session != null) {
                session.close();
                MongoTransactionContext.pop();
                log.debug("Clean Mongo session");
            }
        }
    }
}
