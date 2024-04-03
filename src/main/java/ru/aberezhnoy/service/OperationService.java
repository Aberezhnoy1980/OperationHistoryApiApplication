package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.DemoOperationDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.DataSourceException;
import ru.aberezhnoy.util.mapper.DemoOperationModelMapper;
import ru.aberezhnoy.util.mapper.OperationDTOMapper;
import ru.aberezhnoy.util.mapper.OperationModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperationService {
    private final Contract.Model<Operation> operationRepository;
    private final StatementService statementService;
    private final CustomerService customerService;
    private final OperationDTOMapper operationDTOMapper;
    private final OperationModelMapper operationModelMapper;
    private final DemoOperationModelMapper demoOperationModelMapper;

    @Autowired
    public OperationService(Contract.Model<Operation> operationRepository,
                            StatementService statementService,
                            CustomerService customerService,
                            OperationDTOMapper operationDTOMapper,
                            OperationModelMapper operationModelMapper,
                            DemoOperationModelMapper demoOperationModelMapper) {
        this.operationRepository = operationRepository;
        this.statementService = statementService;
        this.customerService = customerService;
        this.operationDTOMapper = operationDTOMapper;
        this.operationModelMapper = operationModelMapper;
        this.demoOperationModelMapper = demoOperationModelMapper;
    }

    public List<OperationDTO> findAll() {
        return operationRepository.findAll().orElseThrow(DataSourceException::new)
                .stream()
                .map(operationDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<OperationDTO> findById(long id) {
        return operationRepository.findById(id).map(operationDTOMapper);
    }

    public Operation create(DemoOperationDTO demoOperationDto) {
        Operation operation = operationRepository.save(demoOperationModelMapper.apply(demoOperationDto));
        save(operation);
        return operation;
    }

    public void save(Operation operation) {
        Customer customer = operation.getCustomer();
        customer.addOperations(operation);
        statementService.updateCustomer(customer);
    }

    public void removeById(long id) {
        operationRepository.removeById(id);
    }

    public Contract.Model<Operation> getOperationRepository() {
        return operationRepository;
    }

    public OperationDTOMapper getOperationDTOMapper() {
        return operationDTOMapper;
    }

    public OperationModelMapper getOperationModelMapper() {
        return operationModelMapper;
    }
}
