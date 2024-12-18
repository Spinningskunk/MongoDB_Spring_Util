package com.ikun.cm.pojo.base;

import com.ikun.cm.pojo.enums.HttpStatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HeKun
 * @date: 2024/11/5 23:07
 * @description: A generic response class for API responses;
 */
@Data
@ApiModel(value = "封装的基础返回类")
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> {

    /**
     * the HTTP status code of this response.
     */
    @ApiModelProperty(value = "状态码")
    private Integer statusCode;

    /**
     * A message providing additional information about this response.
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * The data to be returned to the client
     */
    @ApiModelProperty(value = "数据体")
    private T data;

    @ApiModelProperty(value = "请求是否成功")
    private Boolean success;

    /**
     * A default success constructor
     * @param data The data to be included in the response.
     */
    public static <T> ApiResponseDTO ok(T data) {
        return new ApiResponseDTO(200,"success",data,true);
    }

    /**
     * A simple success constructor
     * @param httpStatusCode my httpStatusCode enum
     * @param data The data to be included in the response.
     */
    public static <T> ApiResponseDTO ok(HttpStatusCode httpStatusCode, T data) {
        return new ApiResponseDTO(httpStatusCode.getCode(), httpStatusCode.getMarks(), data,true);
    }

    /**
     * A default error constructor
     * @param data The data to be included in the response.
     */
    public static <T> ApiResponseDTO error(T data) {
        return new ApiResponseDTO(500,"error",data,false);
    }


    /**
     * A simple error constructor
     * @param httpStatusCode my httpStatusCode enum
     * @param data The data to be included in the response.
     */
    public static <T> ApiResponseDTO error(HttpStatusCode httpStatusCode, T data) {
        return new ApiResponseDTO(httpStatusCode.getCode(), httpStatusCode.getMarks(), data,false);
    }



}
