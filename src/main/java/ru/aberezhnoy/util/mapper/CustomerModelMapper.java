package ru.aberezhnoy.util.mapper;

import org.springframework.stereotype.Service;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.dto.CustomerDTO;

import java.util.function.Function;

/**
 * Class for convert Data Transfer Object (DTO) to Customer entity
 */
@Service
public class CustomerModelMapper implements Function<CustomerDTO, Customer> {
    @Override
    public Customer apply(CustomerDTO customerDTO) {
        String birthDate = customerDTO.getBirthDate();
        if (birthDate.contains("-")) {
            String[] tokens = birthDate.split("-");
            birthDate = tokens[2] + "/" + tokens[1] + "/" + tokens[0];
        }
        return Customer.builder()
                .setFirstname(customerDTO.getFirstname())
                .setLastname(customerDTO.getLastname())
                .setSurname(customerDTO.getSurname())
                .setBirthDate(birthDate)
                .setGender(customerDTO.getGender())
                .setPhoneNumber(customerDTO.getPhoneNumber())
                .setEmail(customerDTO.getEmail())
                .build();
    }
}
