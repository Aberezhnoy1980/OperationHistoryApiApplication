package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.dto.CustomerDto;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.DataSourceException;
import ru.aberezhnoy.domain.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final Contract.Model<Customer> customerRepository;

    @Autowired
    public CustomerService(Contract.Model<Customer> customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> findAll() {
        return customerRepository.findAll().orElseThrow(DataSourceException::new).stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
//        return customerRepository.findAll().stream()
//                .map(CustomerDto::new)
//                .collect(Collectors.toList());
    }

    /**
     * Convert Customer entity to Data Transfer Object (DTO)
     *
     * @param customer
     * @return customerDto
     */
    private CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(customer);
    }

    /**
     * Convert Data Transfer Object (DTO) to Customer entity
     *
     * @param customerDto
     * @return customer
     */
    private Customer mapToCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .setFirstname(customerDto.getFirstname())
                .setLastname(customerDto.getLastname())
                .setSurname(customerDto.getSurname())
                .setBirthDate(customerDto.getBirthDate())
                .setGender(customerDto.getGender())
                .setPhoneNumber(customerDto.getPhoneNumber())
                .setEmail(customerDto.getEmail())
                .build();
    }

    public Optional<CustomerDto> findById(long id) {
        return customerRepository.findById(id).map(this::mapToCustomerDto);
    }

    public Customer save(CustomerDto customerDto) {
        return customerRepository.save(mapToCustomer(customerDto));
    }

    public void update(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getId())
                .orElseThrow(() -> new CustomerNotFound(customerDto.getId()));

        String[] tokens = customerDto.getBirthDate().split("-");
        String birthDate = tokens[2] + "/" + tokens[1] + "/" + tokens[0];
        if (!customer.getFirstname().equals(customerDto.getFirstname()))
            customer.setFirstname(customerDto.getFirstname());
        if (!customer.getLastname().equals(customerDto.getLastname()))
            customer.setLastname(customerDto.getLastname());
        if (!customer.getSurname().equals(customerDto.getSurname()))
            customer.setSurname(customerDto.getSurname());
        if (!customer.getBirthDate().toString().equals(customerDto.getBirthDate()))
            customer.setBirthDate(birthDate);
        if (!customer.getGender().equals(customerDto.getGender()))
            customer.setGender(customerDto.getGender());
        if (!customer.getEmail().equals(customerDto.getEmail()))
            customer.setEmail(customerDto.getEmail());
        if (!customer.getPhoneNumber().equals(customerDto.getPhoneNumber()))
            customer.setPhoneNumber(customerDto.getPhoneNumber());
    }

    public void removeById(long id) {
        customerRepository.removeById(id);
    }
}
