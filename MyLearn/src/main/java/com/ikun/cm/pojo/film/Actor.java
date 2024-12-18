package com.ikun.cm.pojo.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HeKun
 * @date: 2024/10/29 0:00
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    /**
     * 演员姓名
     */
    String name;
    /**
     * 扮演角色
     */
    String role;
    /**
     * 头像地址
     */
    String avatarAddress;
}
