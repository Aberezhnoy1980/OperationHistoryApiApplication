package ru.aberezhnoy.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aberezhnoy.dto.DemoOperationDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.DataValidationException;
import ru.aberezhnoy.service.AsyncInputOperationService;
import ru.aberezhnoy.service.OperationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/operations")
public class OperationController {
    private static final Logger logger = LogManager.getLogger(OperationController.class);
    private final OperationService operationService;
    private final AsyncInputOperationService asyncInputOperationService;

    @Autowired
    public OperationController(OperationService operationService, AsyncInputOperationService asyncInputOperationService) {
        this.operationService = operationService;
        this.asyncInputOperationService = asyncInputOperationService;
    }

    @GetMapping
    public List<OperationDTO> findAll() {
        logger.info("Operation list requested");
        return operationService.findAll();
    }

    @PostMapping
    public void createOperation(@RequestBody @Validated DemoOperationDTO demoOperationDto, BindingResult bindingResult) {
        logger.info("Saving  operation");
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            throw new DataValidationException(bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        asyncInputOperationService.addOperation(demoOperationDto);
    }

    @GetMapping("/{id}")
    public OperationDTO findById(@PathVariable Long id) {
        logger.info("Operation with id = {} requested", id);
        return operationService.findById(id).orElseThrow(() -> new CustomerNotFound(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        logger.info("Deleting operation with id = {}", id);
        operationService.removeById(id);
    }
}
