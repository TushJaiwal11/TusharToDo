package com.todo.exception;

public class ResourceNotFoundException extends Throwable {


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
