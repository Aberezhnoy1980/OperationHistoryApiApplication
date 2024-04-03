package ru.aberezhnoy.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.dto.CustomerDTO;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.exception.DataSourceException;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.util.mapper.CustomerDTOMapper;
import ru.aberezhnoy.util.mapper.CustomerModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final Contract.Model<Customer> customerRepository;
    private final StatementService statementService;
    private final CustomerDTOMapper customerDTOMapper;

    private final CustomerModelMapper customerModelMapper;

    @Autowired
    public CustomerService(Contract.Model<Customer> customerRepository,
                           StatementService statementService,
                           CustomerDTOMapper customerDTOMapper,
                           CustomerModelMapper customerModelMapper) {
        this.customerRepository = customerRepository;
        this.statementService = statementService;
        this.customerDTOMapper = customerDTOMapper;
        this.customerModelMapper = customerModelMapper;
    }

    /**
     * Method for create demo data
     */
    @PostConstruct
    public void initStorage() {
        final Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            char prefix = (char) random.nextInt(65, 90);
            this.create(customerDTOMapper.apply(Customer.builder()
                    .setFirstname(prefix + "Customer")
                    .setLastname(prefix + "Cusomerovich")
                    .setSurname(prefix + "Custumoroff")
                    .setBirthDate("0" + random.nextInt(1, 10) + "/0" + random.nextInt(1, 9) + "/" + random.nextInt(1960, 2006))
                    .setGender(i % 2 == 0 ? "male" : "female")
                    .setEmail(prefix + "Customer" + "_" + prefix + prefix + "@mail.com")
                    .setPhoneNumber("" + random.nextLong(10_000_000_000L, 99_000_000_000L))
                    .build()));
        }
    }

    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().orElseThrow(DataSourceException::new).stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> findById(long id) {
        return customerRepository.findById(id).map(customerDTOMapper);
    }

    public Optional<Customer> findCustomerById(long id) {
        return customerRepository.findById(id);
    }

    public Customer create(CustomerDTO customerDto) {
        Customer customer = customerRepository.save(customerModelMapper.apply(customerDto));
        save(customer);
        return customer;
    }

    public void save(Customer customer) {
        statementService.addCustomer(customer);
    }

    public void update(CustomerDTO customerDto) {
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
        statementService.updateCustomer(customer);
    }

    public void removeById(long id) {
        customerRepository.removeById(id);
    }

    public Contract.Model<Customer> getCustomerRepository() {
        return customerRepository;
    }

    public CustomerDTOMapper getCustomerDTOMapper() {
        return customerDTOMapper;
    }

    public CustomerModelMapper getCustomerModelMapper() {
        return customerModelMapper;
    }
}
