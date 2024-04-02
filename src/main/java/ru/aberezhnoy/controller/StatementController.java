package ru.aberezhnoy.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aberezhnoy.dto.CustomerDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.DataSourceException;
import ru.aberezhnoy.service.StatementService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/transactions")
public class StatementController {
    private static final Logger logger = LogManager.getLogger(StatementController.class);
    private final StatementService statementService;

    @Autowired
    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/allTransactions")
    public Set<OperationDTO> getAllOperations() {
        logger.info("Operation list requested");
        return statementService.getAllOperations();
    }

    @GetMapping("/{id}")
    public Set<OperationDTO> getAllOperationsByCustomerId(@PathVariable Long id) {
        logger.info("Operation for client with id: {} requested", id);
        return statementService.getOperationsByCustomer(id).orElseThrow(DataSourceException::new);
    }

    @GetMapping("/allCustomers")
    public Set<CustomerDTO> getAllCustomers() {
        logger.info("Customer list requested");
        return statementService.getAllCustomers();
    }
}
