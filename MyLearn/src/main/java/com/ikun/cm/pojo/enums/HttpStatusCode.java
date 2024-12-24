package com.ikun.cm.pojo.enums;

/**
 * @author: HeKun
 * @date: 2024/11/5 23:26
 * @description: Enumeration representing common HTTP status codes.
 * This enum contains the standard HTTP status codes and their corresponding descriptions.
 * It implements the `BaseEnum` interface to provide a consistent way of accessing the status code and description.
 */
public enum HttpStatusCode implements BaseEnum {

    // Enum constants representing common HTTP status codes
    OK(200, "OK"),                             // 200 OK - The request has succeeded.
    CREATED(201, "Created"),                   // 201 Created - The request has been fulfilled and a new resource has been created.
    BAD_REQUEST(400, "Bad Request"),           // 400 Bad Request - The server could not understand the request due to invalid syntax.
    NOT_FOUND(404, "Not Found"),               // 404 Not Found - The server cannot find the requested resource.
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"); // 500 Internal Server Error - The server has encountered a situation it doesn't know how to handle.

    // Enum attributes

    /**
     * The HTTP status code.
     * This is the numerical value representing the result of the HTTP request.
     */
    private final int code;

    /**
     * A brief description of the status code.
     * Provides a human-readable explanation of the status.
     */
    private final String marks;

    /**
     * Constructor for HttpStatusCode enum.
     * This constructor assigns the code and description for each HTTP status code.
     *
     * @param code The HTTP status code (e.g., 200, 404, 500).
     * @param marks A brief description of the status (e.g., "OK", "Not Found").
     */
    HttpStatusCode(int code, String marks) {
        this.code = code;
        this.marks = marks;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return The integer value representing the HTTP status code.
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * Gets the description of the status code.
     *
     * @return A brief explanation of the status (e.g., "OK", "Bad Request").
     */
    @Override
    public String getMarks() {
        return marks;
    }
}
