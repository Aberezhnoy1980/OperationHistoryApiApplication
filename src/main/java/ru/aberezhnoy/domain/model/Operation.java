package ru.aberezhnoy.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aberezhnoy.contract.Contract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Parent class for operation entities
 */
@Data
@NoArgsConstructor
public abstract class Operation implements Contract.Model.ConsolePrintable {

    /**
     * Identifier for operation
     */
    private Long id;
    /**
     * Field for sorting operation
     */
    private String operationType;

    /**
     * Reference to a customer executed this operation
     */
    private Customer customer;

    /**
     * Operation start timestamp
     */
    private LocalDateTime date;

    /**
     * Amount of operation in {@link BigDecimal} format
     */
    private BigDecimal amount;

    /**
     * A simple text field
     */
    private String description;

    /**
     * Base constructor
     */
    public Operation(Customer customer, BigDecimal amount, String description) {
        this.id = null;
        this.operationType = this.getClass().getSimpleName();
        this.customer = customer;
        this.date = LocalDateTime.now();
        this.amount = amount;
        this.description = description;
    }

    /**
     * Method allows to display general operations information in the console.
     * It's abstract method from {@link Contract.Model.ConsolePrintable} interface
     */
    @Override
    public void print() {
        System.out.printf("Id: %d, amount: %s, date: %s, description: %s%n", id, amount, date, description);
    }
}
