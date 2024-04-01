package ru.aberezhnoy.domain.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.data.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository class for processing crud operations with the {@link Customer}
 */
@Repository
public class CustomerRepository implements Contract.Model<Customer> {
    /**
     * Variable for creating and incrementing id while saving the entity locally
     */
    private static AtomicLong identity;
    /**
     * Service for to store and manipulation data when use internal
     * collection {@link List} for work locally (without connecting
     * to external data sources (DB))
     */
    private final StorageService<Customer> customerStorageService;

    /**
     * The base constructor injects {@link StorageService} and initializes new {@link AtomicLong} for subsequent static incrementing ID while saving
     *
     * @param customerStorageService - data source and CRUD operation service for type {@link Customer}
     */
    @Autowired
    private CustomerRepository(StorageService<Customer> customerStorageService) {
        identity = new AtomicLong(0);
        this.customerStorageService = customerStorageService;
    }

    /**
     * Method for create demo data
     */
    @PostConstruct
    public void initStorage() {
        final Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            char prefix = (char) random.nextInt(65, 90);
            this.save(Customer.builder()
                    .setFirstname(prefix + "Customer")
                    .setLastname(prefix + "Cusomerovich")
                    .setSurname(prefix + "Custumoroff")
                    .setBirthDate("0" + random.nextInt(1, 10) + "/0" + random.nextInt(1, 9) + "/" + random.nextInt(1960, 2006))
                    .setGender(i % 2 == 0 ? "male" : "female")
                    .setEmail(prefix + "Customer" + "_" + prefix + prefix + "@mail.com")
                    .setPhoneNumber("" + random.nextLong(10_000_000_000L, 99_000_000_000L))
                    .build());
        }
    }

    /**
     * Method for getting operation list
     *
     * @return returns an {@link Optional} describing the given {@link ArrayList} of customers,
     * if non-{@code null}, otherwise returns an empty {@link Optional}.
     */
    @Override
    public Optional<List<Customer>> findAll() {
        return Optional.ofNullable(customerStorageService.getData());
    }

    /**
     * Method for getting customer
     *
     * @param id customer unique identifier
     * @return returns an {@link Optional} describing the given {@link ArrayList} of customers,
     * if non-{@code null}, otherwise returns an empty {@link Optional}.
     */
    @Override
    public Optional<Customer> findById(long id) {
        for (int i = 0; i < customerStorageService.getSize(); i++) {
            if (customerStorageService.getElementByIndex(i).getId() == id) {
                return Optional.ofNullable(customerStorageService.getElementByIndex(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Method for adding a customer to data source
     *
     * @param customer customer with {@code null} id
     * @return saved customer with unique id
     */
    @Override
    public Customer save(Customer customer) {
        customer.setId(identity.incrementAndGet());
        customerStorageService.add(customer);
        return customer;
    }

    /**
     * Method for deleting a customer
     *
     * @param id customer unique identifier
     */
    @Override
    public void removeById(long id) {
        customerStorageService.removeByElement(findById(id).orElseThrow(() -> new CustomerNotFound(id)));
    }
}