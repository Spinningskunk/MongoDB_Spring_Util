package com.ikun.cm.transactional;

/**
 * @author: HeKun
 * @date: 2025/10/16 15:07
 * @description:事务传播行为（仿 Spring）
 * REQUIRED：加入当前事务（默认）
 * REQUIRES_NEW：新建独立事务
 */
public enum Propagation {
    //假如当前事务(默认)
    REQUIRED,
    //新建独立事务
    REQUIRES_NEW
}
