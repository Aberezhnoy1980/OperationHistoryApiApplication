package ru.aberezhnoy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class OperationDTO {
    Long id;
    Long customerId;
    LocalDateTime date;
    BigDecimal amount;
    String description;
    String operationType;

    public OperationDTO(Long id, Long customerId, LocalDateTime date, BigDecimal amount, String description, String operationType) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.operationType = operationType;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getOperationType() {
        return operationType;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
