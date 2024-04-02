package ru.aberezhnoy.util.mapper;

import org.springframework.stereotype.Service;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.CustomerDTO;

import java.util.function.Function;

/**
 * Convert {@link Operation} entity to Data Transfer Object (DTO)
 */
@Service
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {
    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(customer);
    }
}
