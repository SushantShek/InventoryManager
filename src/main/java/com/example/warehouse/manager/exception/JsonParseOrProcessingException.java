package com.example.warehouse.manager.exception;

public class JsonParseOrProcessingException extends RuntimeException {
    public JsonParseOrProcessingException(String message) {
        super("Exception : " + message);
    }
}
