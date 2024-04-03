package ru.aberezhnoy.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.domain.model.CashbackOperation;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.LoanOperation;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.domain.repository.CustomerRepository;
import ru.aberezhnoy.dto.DemoOperationDTO;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.UnsupportedOperationTypeException;

import java.util.function.Function;

/**
 * Demo mapper-class for convert Data Transfer Object (DTO) to {@link Customer} model.
 * Needed for temporary solving issue with cast hierarchical DTOs
 */
@Service
public class DemoOperationModelMapper implements Function<DemoOperationDTO, Operation> {

    private final Contract.Model<Customer> customerRepository;

    @Autowired
    public DemoOperationModelMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Operation apply(DemoOperationDTO demoOperationDTO) {
        if (demoOperationDTO.getOperationType().equals("LoanOperation")) {
            return new LoanOperation(customerRepository.findById(demoOperationDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFound(demoOperationDTO.getCustomerId())),
                    demoOperationDTO.getAmount(),
                    demoOperationDTO.getDescription(),
                    demoOperationDTO.getLoanId());
        } else if (demoOperationDTO.getOperationType().equals("CashbackOperation")) {
            return new CashbackOperation(customerRepository.findById(demoOperationDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFound(demoOperationDTO.getCustomerId())),
                    demoOperationDTO.getAmount(),
                    demoOperationDTO.getDescription());
        } else throw new UnsupportedOperationTypeException(demoOperationDTO.getOperationType());
    }
}
