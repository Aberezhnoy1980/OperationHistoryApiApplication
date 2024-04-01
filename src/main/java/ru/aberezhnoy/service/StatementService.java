package ru.aberezhnoy.service;

import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.Operation;

import java.util.*;

/**
 * Class for Implementation of one-to-many relationship
 */
public class StatementService {
    private Map<Customer, Set<Operation>> statement;
    private final List<Customer> customers;
    private final List<Operation> operations;

    public StatementService(List<Customer> customers, List<Operation> operations) {
        this.customers = customers;
        this.operations = operations;
        this.statement = buildTransactionsMap();
    }

    public void addCustomer(Customer customer) {
        statement.put(customer, customer.getOperations());
    }

    public void updateCustomer(Customer customer) {
        statement.replace(customer, customer.getOperations());
    }

    public Set<Customer> getAllCustomers() {
        return statement.keySet();
    }

    public Set<Operation> getAllOperations() {
        Set<Operation> res = new HashSet<>();
        for (Set<Operation> s : statement.values()) {
            res.addAll(s);
        }
        return res;
    }

    public Optional<Set<Operation>> getOperationsByCustomer(long id) {
        for (Customer c : customers) {
            if (c.getId() == id)
                return Optional.of(statement.get(c));
        }
        return Optional.empty();
    }

    private Map<Customer, Set<Operation>> buildTransactionsMap() {
        this.statement = new HashMap<>();
//        Set<Operation> operationSet = new HashSet<>();
        for (int i = 0; i < customers.size(); i++) {
//            for (Operation o : operations) {
//                if (o.getCustomer().getId() == customers.getElementByIndex(i).getId())
//                    operationSet.add(o);
//            }
            statement.put(customers.get(i), customers.get(i).getOperations());
//            operationSet.clear();
        }
        return statement;
    }

    public Map<Customer, Set<Operation>> getMapStatement() {
        buildTransactionsMap();
        return statement;
    }
}
