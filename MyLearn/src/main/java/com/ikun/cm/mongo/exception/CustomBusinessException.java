package com.ikun.cm.mongo.exception;

/**
 * @author: HeKun
 * @date: 2024/11/6 0:58
 * @description: Custom exception for handling some business logic errors
 */
public class CustomBusinessException extends RuntimeException{

    /**
     * It may be useful someday....
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new CustomBusinessException with the specified detail message.
     *
     * @param message the detail message.
     */
    public CustomBusinessException(String message) {
        super(message);
    }

    /**
     * Constructs a new CustomBusinessException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public CustomBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
