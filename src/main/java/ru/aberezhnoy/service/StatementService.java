package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.CustomerDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.util.mapper.CustomerDTOMapper;
import ru.aberezhnoy.util.mapper.OperationDTOMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for Implementation of one-to-many relationship
 */
@Service
public class StatementService {
    private final CustomerDTOMapper customerDTOMapper;
    private final OperationDTOMapper operationDTOMapper;
    private final Map<Customer, Set<Operation>> statement;

    @Autowired
    public StatementService(CustomerDTOMapper customerDTOMapper, OperationDTOMapper operationDTOMapper) {
        this.customerDTOMapper = customerDTOMapper;
        this.operationDTOMapper = operationDTOMapper;
        this.statement = new HashMap<>();
    }

    public void addCustomer(Customer customer) {
        statement.put(customer, customer.getOperations());
    }

    public void updateCustomer(Customer customer) {
        statement.replace(customer, customer.getOperations());
    }

    public Set<CustomerDTO> getAllCustomers() {
        return statement.keySet().stream().map(customerDTOMapper)
                .collect(Collectors.toSet());
    }

    public Set<OperationDTO> getAllOperations() {
        Set<Operation> res = new HashSet<>();
        for (Set<Operation> s : statement.values()) {
            res.addAll(s);
        }
        return res.stream()
                .map(operationDTOMapper)
                .collect(Collectors.toSet());
    }

    public Optional<Set<OperationDTO>> getOperationsByCustomer(long id) {
        for (Customer c : statement.keySet()) {
            if (c.getId() == id)
                return Optional.of(statement.get(c).stream()
                        .map(operationDTOMapper)
                        .collect(Collectors.toSet()));
        }
        return Optional.empty();
    }
}
