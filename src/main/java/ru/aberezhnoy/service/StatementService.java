package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.CustomerDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.DataSourceException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for Implementation of one-to-many relationship
 */
@Service
public class StatementService {
    private final CustomerService customerService;
    private final OperationService operationService;
    private Map<Customer, Set<Operation>> statement;
    private final List<Customer> customers;
    private final List<Operation> operations;

    @Autowired
    public StatementService(CustomerService customerService1, OperationService operationService1) {
        this.customerService = customerService1;
        this.operationService = operationService1;
        this.customers = customerService.getCustomerRepository().findAll().orElseThrow(DataSourceException::new);
        this.operations = operationService.getOperationRepository().findAll().orElseThrow(DataSourceException::new);
        this.statement = buildTransactionsMap();
    }

    public void addCustomer(Customer customer) {
        statement.put(customer, customer.getOperations());
    }

    public void updateCustomer(Customer customer) {
        statement.replace(customer, customer.getOperations());
    }

    public Set<CustomerDTO> getAllCustomers() {
        return statement.keySet().stream().map(c -> customerService.getCustomerDTOMapper().apply(c))
                .collect(Collectors.toSet());
    }

    public Set<OperationDTO> getAllOperations() {
        Set<Operation> res = new HashSet<>();
        for (Set<Operation> s : statement.values()) {
            res.addAll(s);
        }
        return res.stream()
                .map(operation -> operationService.getOperationDTOMapper().apply(operation))
                .collect(Collectors.toSet());
    }

    public Optional<Set<OperationDTO>> getOperationsByCustomer(long id) {
        for (Customer c : customers) {
            if (c.getId() == id)
                return Optional.of(statement.get(c).stream()
                        .map(operation -> operationService.getOperationDTOMapper().apply(operation))
                        .collect(Collectors.toSet()));
        }
        return Optional.empty();
    }

    private Map<Customer, Set<Operation>> buildTransactionsMap() {
        this.statement = new HashMap<>();
        for (Customer customer : customers) {
            statement.put(customer, customer.getOperations());
        }
        return statement;
    }

    public Map<Customer, Set<Operation>> getMapStatement() {
        buildTransactionsMap();
        return statement;
    }
}
