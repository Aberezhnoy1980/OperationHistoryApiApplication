package ru.aberezhnoy.exception;

public class IncorrectEmailFormatException extends RuntimeException {
    public IncorrectEmailFormatException() {
        super("incorrect expression for 'email'");
    }
}
