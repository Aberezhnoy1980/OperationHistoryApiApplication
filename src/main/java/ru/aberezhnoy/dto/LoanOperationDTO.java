package ru.aberezhnoy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanOperationDTO extends OperationDTO {
    Long loanId;

    public LoanOperationDTO(Long id, Long customerId, LocalDateTime date, BigDecimal amount, Long loanId, String description, String operationType) {
        super(id, customerId, date, amount, description, operationType);
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getOperationType() {
        return operationType;
    }
}
