package ru.aberezhnoy.exception;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(long id) {
        super(String.format("Customer with id %d not found", id));
    }
}
