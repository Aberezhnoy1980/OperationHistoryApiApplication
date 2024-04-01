package ru.aberezhnoy.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.dto.CustomerDto;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.DataValidationException;
import ru.aberezhnoy.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private static final Logger logger = LogManager.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> findAll() {
        logger.info("Customer list requested");
        return customerService.findAll();
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody @Validated CustomerDto customerDto, BindingResult bindingResult) {
        logger.info("Saving  user");
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            throw new DataValidationException(bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        Customer customer = customerService.save(customerDto);
        return customerService.findById(customer.getId()).orElseThrow(() -> new CustomerNotFound(customer.getId()));
    }

    @PutMapping
    public void updateCustomer(@RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        logger.info("Updating user with id = {}", customerDto.getId());
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            throw new DataValidationException(bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        customerService.update(customerDto);
    }

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable Long id) {
        logger.info("User with id = {} requested", id);
        return customerService.findById(id).orElseThrow(() -> new CustomerNotFound(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        logger.info("Deleting user with id = {}", id);
        customerService.removeById(id);
    }
}
