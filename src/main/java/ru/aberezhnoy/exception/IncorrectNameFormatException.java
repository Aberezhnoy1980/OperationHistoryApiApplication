package ru.aberezhnoy.exception;

public class IncorrectNameFormatException extends RuntimeException {
    public IncorrectNameFormatException() {
        super("incorrect expression for 'name'");
    }
}
