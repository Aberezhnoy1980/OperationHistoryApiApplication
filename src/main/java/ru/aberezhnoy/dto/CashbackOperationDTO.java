package ru.aberezhnoy.dto;

import java.math.BigDecimal;

public class CashbackOperationDTO extends OperationDTO {
    BigDecimal cashbackAmount;

    public CashbackOperationDTO(Long id, Long customerId, BigDecimal amount, BigDecimal cashbackAmount, String description, String operationType) {
        super(id, customerId, amount, description, operationType);
        this.cashbackAmount = cashbackAmount;
    }

    public BigDecimal getCashbackAmount() {
        return cashbackAmount;
    }

    @Override
    public String getOperationType() {
        return operationType;
    }
}
