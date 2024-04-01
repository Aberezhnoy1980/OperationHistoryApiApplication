package ru.aberezhnoy.exception;

public class IncorrectGenderFormatException extends RuntimeException {
    public IncorrectGenderFormatException() {
        super("incorrect expression for 'gender'");
    }
}
