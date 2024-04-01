package ru.aberezhnoy.exception;

public class PhoneNumberFormatException extends RuntimeException {
    public PhoneNumberFormatException(String exp) {
        super("Error in phone number format: " + (exp.length() != 11 ? "incorrect digit counts" : "unacceptable symbols"));
    }
}
