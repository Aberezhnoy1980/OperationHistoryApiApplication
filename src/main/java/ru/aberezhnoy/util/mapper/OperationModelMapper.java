package ru.aberezhnoy.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.domain.model.CashbackOperation;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.LoanOperation;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.domain.repository.CustomerRepository;
import ru.aberezhnoy.dto.CashbackOperationDTO;
import ru.aberezhnoy.dto.LoanOperationDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.UnsupportedOperationTypeException;

import java.util.function.Function;

@Service
public class OperationModelMapper implements Function<OperationDTO, Operation> {

    private final Contract.Model<Customer> customerRepository;

    @Autowired
    public OperationModelMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Operation apply(OperationDTO operationDTO) {
        if (operationDTO.getOperationType().equals("LoanOperation")) {
            LoanOperationDTO loanOperationDTO = (LoanOperationDTO) operationDTO;
            return new LoanOperation(customerRepository.findById(loanOperationDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFound(loanOperationDTO.getCustomerId())),
                    loanOperationDTO.getAmount(),
                    loanOperationDTO.getDescription(),
                    loanOperationDTO.getLoanId());
        } else if (operationDTO.getOperationType().equals("CashbackOperation")) {
            CashbackOperationDTO cashbackOperationDto = (CashbackOperationDTO) operationDTO;
            return new CashbackOperation(customerRepository.findById(cashbackOperationDto.getCustomerId()).orElseThrow(() -> new CustomerNotFound(cashbackOperationDto.getCustomerId())),
                    cashbackOperationDto.getAmount(),
                    cashbackOperationDto.getDescription());
        } else throw new UnsupportedOperationTypeException(operationDTO.getOperationType());
    }
}
