package ru.aberezhnoy.util.mapper;

import org.springframework.stereotype.Service;
import ru.aberezhnoy.domain.model.CashbackOperation;
import ru.aberezhnoy.domain.model.LoanOperation;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.CashbackOperationDTO;
import ru.aberezhnoy.dto.LoanOperationDTO;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.UnsupportedOperationTypeException;

import java.util.function.Function;

/**
 * Convert {@link Operation} entity to Data Transfer Object (DTO)
 */
@Service
public class OperationDTOMapper implements Function<Operation, OperationDTO> {
    @Override
    public OperationDTO apply(Operation operation) {
        if (operation instanceof LoanOperation loanOperation) {
            return new LoanOperationDTO(loanOperation.getId(),
                    loanOperation.getCustomer().getId(),
                    loanOperation.getAmount(),
                    loanOperation.getLoanId(),
                    loanOperation.getDescription(),
                    loanOperation.getType());
        } else if (operation instanceof CashbackOperation cashbackOperation) {
            return new CashbackOperationDTO(cashbackOperation.getId(),
                    cashbackOperation.getCustomer().getId(),
                    cashbackOperation.getAmount(),
                    cashbackOperation.getCashbackAmount(),
                    cashbackOperation.getDescription(),
                    cashbackOperation.getType());
        } else throw new UnsupportedOperationTypeException(operation.getOperationType());
    }
}
