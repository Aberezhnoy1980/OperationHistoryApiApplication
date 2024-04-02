package ru.aberezhnoy.exception;

public class UnsupportedOperationTypeException extends RuntimeException {
    public UnsupportedOperationTypeException(String type) {
        super(String.format("Unsupported operation type: %s", type));
    }
}
