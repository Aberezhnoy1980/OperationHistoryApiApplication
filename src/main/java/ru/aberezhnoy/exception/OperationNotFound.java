package ru.aberezhnoy.exception;

public class OperationNotFound extends RuntimeException {
    public OperationNotFound(long id) {
        super(String.format("Operation with %s not found", id));
    }
}
