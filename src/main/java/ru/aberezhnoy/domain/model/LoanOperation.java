package ru.aberezhnoy.domain.model;

import ru.aberezhnoy.contract.Contract;

import java.math.BigDecimal;

public class LoanOperation extends Operation implements Contract.Model.ConsolePrintable, Comparable<LoanOperation> {
    private Long loanId;

    public LoanOperation(Customer customer, BigDecimal amount, String description, Long loanId) {
        super(customer, amount, description);
        this.loanId = loanId;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    public LoanOperation() {
        super();
    }

    public Long getLoanId() {
        return loanId;
    }

    @Override
    public void print() {
        System.out.printf(this.toString());
    }

    @Override
    public int compareTo(LoanOperation o) {
        return this.getAmount().compareTo(o.getAmount());
    }

    @Override
    public String toString() {
        return String.format("Id: %d, type: %s, loan id: %s, amount: %s, date: %s", getId(), this.getOperationType(), loanId, getAmount(), getDate());
    }
}
