package ru.aberezhnoy.exception;

import java.util.List;

public class DataValidationException extends RuntimeException {
    private final List<String> messages;

    public DataValidationException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}