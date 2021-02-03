package com.example.warehouse.manager.exception;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super("Exception : " + message);
    }
}
