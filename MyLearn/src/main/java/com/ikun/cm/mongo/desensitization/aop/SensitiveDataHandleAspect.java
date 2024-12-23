package com.ikun.cm.mongo.desensitization.aop;

import com.ikun.cm.mongo.annotation.SensitiveField;
import com.ikun.cm.mongo.desensitization.SensitiveDataStrategy;
import com.ikun.cm.pojo.base.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author: HeKun
 * @date: 2024/12/24 0:57
 * @description:
 */
@Slf4j
@Aspect
@Component
public class SensitiveDataHandleAspect {


    // Spring 应用上下文，用于注入策略类
    @Autowired
    private ApplicationContext applicationContext;
    @Pointcut("execution(* com.ikun.cm.controller.*.*(..))")
    public void sensitiveDataPointcut() {}

    public SensitiveDataHandleAspect() {
        log.info("SensitiveDataHandleAspect 被加载！");
    }

    @Around("sensitiveDataPointcut()")
    public Object handleSensitiveData(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("切面开始处理");
        Object result = joinPoint.proceed(); // 执行目标方法

        // 处理敏感数据
        if (result != null) {
            Class<?> clazz = result.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(SensitiveField.class)) {
                    field.setAccessible(true); // 设置字段可访问

                    try {
                        Object value = field.get(result);
                        if (value instanceof String) {
                            SensitiveField annotation = field.getAnnotation(SensitiveField.class);
                            // 获取脱敏策略类
                            Class<? extends SensitiveDataStrategy> strategyClass = annotation.strategy();

                            // 动态从 Spring 容器中获取对应的策略实现
                            SensitiveDataStrategy strategy = applicationContext.getBean(strategyClass);

                            // 执行脱敏操作
                            String maskedValue = strategy.desensitizationStrategy((String) value);
                            field.set(result, maskedValue); // 更新字段值
                        }
                    } catch (IllegalAccessException e) {
                        log.error("访问字段失败", e);
                    }
                }
            }
        }

        return result;
    }


}
