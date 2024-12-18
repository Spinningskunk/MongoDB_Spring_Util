package com.ikun.cm.pojo.enums;

/**
 * @author: HeKun
 * @date: 2024/11/5 23:26
 * @description: Enumeration representing common HTTP status codes,just ignore my poor english.
 */
public enum HttpStatusCode implements BaseEnum{
    // Enum constants
    OK(200, "OK"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    //Enum attributes
    /**
     * The HTTP status code.
     */
    private final int code;

    /**
     * A simple description of current status code.
     */
    private final String marks;

    HttpStatusCode(int code, String marks) {
        this.code = code;
        this.marks = marks;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMarks() {
        return marks;
    }
}
