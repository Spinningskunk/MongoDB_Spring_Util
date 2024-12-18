package com.ikun.cm.mongo.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.classgraph.json.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: HeKun
 * @date: 2024/11/2 18:54
 * @description: A simple and basic MongoDB object class to be extended
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("较少、简洁字段的mongo基础数据类")
public abstract class SimpleDBObject {

    @Id
    @JSONField(name = "_id")
    @ApiModelProperty(value = "业务唯一ID",notes = "干脆全局统一使用_id 避免混乱")
    private String _id;
    @ApiModelProperty(value = "业务数据创建时间",notes = "16位时间戳")
    private Long createTime;
    @ApiModelProperty(value = "业务数据更新时间",notes = "16位时间戳")
    private Long updateTime;

}
