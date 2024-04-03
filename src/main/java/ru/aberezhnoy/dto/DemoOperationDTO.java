package ru.aberezhnoy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DemoOperationDTO {
    private Long id;
    private Long customerId;
    private Long loanId;
    private String operationType;
    private LocalDateTime date;
    private BigDecimal amount;
    private String cashbackAmount;
    private String description;
}
