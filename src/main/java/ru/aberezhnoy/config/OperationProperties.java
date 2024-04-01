package ru.aberezhnoy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="operation.processing")
public class OperationProperties {
    private int sleepMilliSeconds;
}
