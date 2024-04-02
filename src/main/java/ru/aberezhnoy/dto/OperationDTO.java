package ru.aberezhnoy.dto;

import java.math.BigDecimal;


public class OperationDTO {
    Long id;
    Long customerId;
    BigDecimal amount;
    String description;
    String operationType;

    public OperationDTO(Long id, Long customerId, BigDecimal amount, String description, String operationType) {
        this.id = id;
        this.customerId = customerId;
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
}
