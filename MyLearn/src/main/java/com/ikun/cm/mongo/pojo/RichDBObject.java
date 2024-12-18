package com.ikun.cm.mongo.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.classgraph.json.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author: HeKun
 * @date: 2024/11/5 20:38
 * @description: A rich basic MongoDB object class to be extended. However, it is not fully rich yet...
 * todo... More useful fields may be added in the future.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("较多字段的mongo基础数据类")
public abstract class RichDBObject {


    @Id
    @JSONField(name = "_id")
    @ApiModelProperty(value = "业务唯一ID",notes = "干脆全局统一使用_id 避免混乱")
    private String _id;
    @ApiModelProperty(value = "业务数据创建时间",notes = "16位时间戳")
    private Long createTime;
    @ApiModelProperty(value = "业务数据更新时间",notes = "16位时间戳")
    private Long updateTime;

    @ApiModelProperty(value = "逻辑删除标识", notes = "true表示已删除，false表示未删除 默认为未删除")
    private Boolean deleted = false;

    @ApiModelProperty(value = "拓展字段",notes = "请避免存储过多逻辑性、重要的的东西")
    private Map<String,Object> extraFields;
}
