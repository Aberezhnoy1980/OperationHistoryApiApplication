package ru.aberezhnoy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public enum OperationDTO {
    ;

    private interface Id {
        @Positive
        Long getId();
    }

    private interface Date {
        LocalDateTime getDate();
    }

    private interface operationType {
        @NotBlank
        String getOperationType();
    }

    public interface Customer {
        @Positive
        Long getCustomerId();
    }

    public interface Amount {
        BigDecimal getAmount();
    }

    public interface CashbackAmount {
        BigDecimal getCashbackAmount();
    }

    public interface LoanId {
        @Positive
        Long getLoanId();
    }

    public interface Description {
        String getDescription();
    }

    public enum Request {
        ;

        @Getter
        public static class OperationDto implements Customer, Amount, Description, LoanId, CashbackAmount {
            Long id;
            String operationType;
            Long customerId;
            BigDecimal amount;
            Long loanId;
            BigDecimal cashbackAmount;
            String description;
        }

        @Value
        public static class LoanOperationDto extends OperationDto implements Customer, Amount, LoanId, Description {
            Long id;
            Long customerId;
            BigDecimal amount;
            Long loanId;
            String description;
        }

        @Value
        public static class CashbackOperationDto extends OperationDto implements Customer, Amount, CashbackAmount, Description {
            Long id;
            Long customerId;
            BigDecimal amount;
            BigDecimal cashbackAmount;
            String description;
        }
    }
}
