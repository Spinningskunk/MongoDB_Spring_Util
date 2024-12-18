package com.ikun.exception;

import com.ikun.cm.mongo.exception.EntityNotFoundException;
import com.ikun.cm.pojo.base.ApiResponseDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author: HeKun
 * @date: 2024/12/2 21:17
 * @description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * handle the exception to Entity not found
     * @param ex
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponseDTO handleEntityNotFountException(EntityNotFoundException ex){
        return ApiResponseDTO.error(ex.getMessage());
    }


}
