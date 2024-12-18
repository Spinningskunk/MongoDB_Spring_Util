package com.ikun.cm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/11/5 22:50
 * @description: simple pageResult
 * @param <T> the type of data contained  in the page result
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * A list of data items on the current page.
     */
    List<T> data;

    /**
     * The total number of items across all pages;
     */
    Long count;
}
