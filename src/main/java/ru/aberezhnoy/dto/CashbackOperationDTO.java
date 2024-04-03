package ru.aberezhnoy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CashbackOperationDTO extends OperationDTO {
    BigDecimal cashbackAmount;

    public CashbackOperationDTO(Long id, Long customerId, LocalDateTime date, BigDecimal amount, BigDecimal cashbackAmount, String description, String operationType) {
        super(id, customerId, date, amount, description, operationType);
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
