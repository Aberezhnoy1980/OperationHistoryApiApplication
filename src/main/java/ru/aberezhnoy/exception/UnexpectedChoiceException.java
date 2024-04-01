package ru.aberezhnoy.exception;

public class UnexpectedChoiceException extends RuntimeException {
    public UnexpectedChoiceException() {
        super("Unexpected choice");
    }
}
