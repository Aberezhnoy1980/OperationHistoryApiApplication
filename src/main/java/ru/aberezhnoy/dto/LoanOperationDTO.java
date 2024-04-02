package ru.aberezhnoy.dto;

import java.math.BigDecimal;

public class LoanOperationDTO extends OperationDTO {
    Long loanId;

    public LoanOperationDTO(Long id, Long customerId, BigDecimal amount, Long loanId, String description, String operationType) {
        super(id, customerId, amount, description, operationType);
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getOperationType() {
        return operationType;
    }
}
