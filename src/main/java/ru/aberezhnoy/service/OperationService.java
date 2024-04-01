package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.domain.model.CashbackOperation;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.LoanOperation;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.DataSourceException;
import ru.aberezhnoy.domain.model.Operation;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperationService {
    private final Contract.Model<Operation> operationRepository;
    private final Contract.Model<Customer> customerRepository;

    @Autowired
    public OperationService(Contract.Model<Operation> operationRepository, Contract.Model<Customer> customerRepository) {
        this.operationRepository = operationRepository;
        this.customerRepository = customerRepository;
    }

    public List<OperationDTO.Request.OperationDto> findAll() {
        return operationRepository.findAll().orElseThrow(DataSourceException::new)
                .stream()
                .map(this::mapToOperationDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert {@link Operation} entity to Data Transfer Object (DTO)
     *
     * @param operation
     * @return operationDto
     */
    private OperationDTO.Request.OperationDto mapToOperationDto(Operation operation) {
        if (operation instanceof LoanOperation loanOperation) {
            return new OperationDTO.Request.LoanOperationDto(loanOperation.getId(),
                    loanOperation.getCustomer().getId(),
                    loanOperation.getAmount(),
                    loanOperation.getLoanId(),
                    loanOperation.getDescription());
        } else {
            CashbackOperation cashbackOperation = (CashbackOperation) operation;
            return new OperationDTO.Request.CashbackOperationDto(cashbackOperation.getId(),
                    cashbackOperation.getCustomer().getId(),
                    cashbackOperation.getAmount(),
                    cashbackOperation.getCashbackAmount(),
                    cashbackOperation.getDescription());
        }
    }

    private Operation mapToOperation(OperationDTO.Request.OperationDto operationDto) {
        if (operationDto.getOperationType().equals("LoanOperation")) {
            return new LoanOperation(customerRepository.findById(operationDto.getCustomerId()).orElseThrow(() -> new CustomerNotFound(operationDto.getCustomerId())),
                    operationDto.getAmount(),
                    operationDto.getDescription(),
                    operationDto.getLoanId());
        } else {
            return new CashbackOperation(customerRepository.findById(operationDto.getCustomerId()).orElseThrow(() -> new CustomerNotFound(operationDto.getCustomerId())),
                    operationDto.getAmount(),
                    operationDto.getDescription());
        }
    }

    public Optional<OperationDTO.Request.OperationDto> findById(long id) {
        return operationRepository.findById(id).map(this::mapToOperationDto);
    }

    public Operation save(OperationDTO.Request.OperationDto operationDto) {
        return operationRepository.save(mapToOperation(operationDto));
    }

    public void removeById(long id) {
        operationRepository.removeById(id);
    }
}
