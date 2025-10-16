package com.ikun.cm.transactional;

import java.lang.annotation.*;

/**
 * @author: HeKun
 * @date: 2025/10/16 1:58
 * @description: 封装mongodb事务 从而不再需要每次套路化的编写
 * 用法示例：
 * @MongoTransactional(propagation = Propagation.REQUIRED)
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoTransactional {
    Propagation propagation() default Propagation.REQUIRED;
}
