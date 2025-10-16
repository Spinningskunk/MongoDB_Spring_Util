package com.ikun.cm.pojo;

import com.ikun.cm.mongo.pojo.RichDBObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HeKun
 * @date: 2024/11/6 19:50
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "a simple dept class")
public class Dept extends RichDBObject {
    
    @ApiModelProperty("the name of dept")
    private String deptName;

    @ApiModelProperty("the id of it's parent dept")
    private String parentDeptId;

    @ApiModelProperty("some describe for dept")
    private String marks;

    @ApiModelProperty("the contact number of the dept")
    private String contactNumber;
}
