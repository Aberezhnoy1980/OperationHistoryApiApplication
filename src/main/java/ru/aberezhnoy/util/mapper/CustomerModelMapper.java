package ru.aberezhnoy.util.mapper;

import org.springframework.stereotype.Service;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.dto.CustomerDTO;

import java.util.function.Function;

@Service
public class CustomerModelMapper implements Function<CustomerDTO, Customer> {
    @Override
    public Customer apply(CustomerDTO customerDTO) {
        return Customer.builder()
                .setFirstname(customerDTO.getFirstname())
                .setLastname(customerDTO.getLastname())
                .setSurname(customerDTO.getSurname())
                .setBirthDate(customerDTO.getBirthDate())
                .setGender(customerDTO.getGender())
                .setPhoneNumber(customerDTO.getPhoneNumber())
                .setEmail(customerDTO.getEmail())
                .build();
    }
}
