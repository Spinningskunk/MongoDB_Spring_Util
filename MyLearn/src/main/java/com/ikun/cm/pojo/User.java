package com.ikun.cm.pojo;

import com.ikun.cm.mongo.annotation.SensitiveField;
import com.ikun.cm.mongo.desensitization.impl.NameMaskStrategy;
import com.ikun.cm.mongo.pojo.SimpleDBObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/1/22 15:27
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User extends SimpleDBObject {

    @SensitiveField(strategy = NameMaskStrategy.class)
    @ApiModelProperty(value = "姓名")
    String name;

    @ApiModelProperty(value = "所属部门")
    String deptId;

    @ApiModelProperty(value = "角色列表")
    List<String> roles;
}
