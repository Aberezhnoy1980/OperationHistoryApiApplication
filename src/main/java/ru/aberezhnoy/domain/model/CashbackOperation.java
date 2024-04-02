package ru.aberezhnoy.domain.model;

import ru.aberezhnoy.contract.Contract;

import java.math.BigDecimal;

public class CashbackOperation extends Operation implements Contract.Model.ConsolePrintable, Comparable<CashbackOperation> {
    private BigDecimal cashbackAmount;

    public CashbackOperation(Customer customer, BigDecimal amount, String description) {
        super(customer, amount, description);
        this.cashbackAmount = this.getAmount().multiply(new BigDecimal("0.15"));

    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    public CashbackOperation() {
        super();
    }

    public BigDecimal getCashbackAmount() {
        return cashbackAmount;
    }

    @Override
    public void print() {
        System.out.printf(this.toString());
    }

    @Override
    public int compareTo(CashbackOperation o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return String.format("Id: %d, type: %s, amount: %s, cashback: %s, date: %s", getId(), this.getOperationType(), getAmount(), cashbackAmount, getDate());
    }
}
