package ru.aberezhnoy.exception;

public class DataSourceException extends RuntimeException {
    public DataSourceException() {
        super("Data source is empty");
    }
}
