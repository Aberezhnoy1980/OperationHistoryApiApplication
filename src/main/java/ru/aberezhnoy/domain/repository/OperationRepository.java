package ru.aberezhnoy.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.domain.data.StorageService;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.exception.OperationNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository class for processing crud operations with the {@link Operation}
 */
@Repository
public class OperationRepository implements Contract.Model<Operation> {
    /**
     * Variable for creating and incrementing id while saving the entity locally
     */
    private static AtomicLong identity;
    /**
     * Service for to store and manipulation data when use internal
     * collection {@link List} for work locally (without connecting
     * to external data sources (DB))
     */
    private final StorageService<Operation> operationStorageService;

    /**
     * The base constructor injects {@link StorageService} and initializes new {@link AtomicLong} for subsequent static incrementing ID while saving
     *
     * @param operationStorageService - data source and CRUD operation service for type {@link Operation}
     */
    @Autowired
    private OperationRepository(StorageService<Operation> operationStorageService) {
        identity = new AtomicLong(0);
        this.operationStorageService = operationStorageService;
    }


    /**
     * Method for getting operation list
     *
     * @return returns an {@link Optional} describing the given {@link ArrayList} of customers,
     * if non-{@code null}, otherwise returns an empty {@link Optional}.
     */
    @Override
    public Optional<List<Operation>> findAll() {
        return Optional.ofNullable(operationStorageService.getData());
    }

    /**
     * Method for getting operation
     *
     * @param id - operation unique identifier
     * @return returns an {@link Optional} describing the given {@link ArrayList} of operations,
     * if non-{@code null}, otherwise returns an empty {@link Optional}.
     */
    @Override
    public Optional<Operation> findById(long id) {
        for (int i = 0; i < operationStorageService.getSize(); i++) {
            if (operationStorageService.getElementByIndex(i).getId() == id) {
                return Optional.of(operationStorageService.getElementByIndex(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Method for adding an operation to data source
     *
     * @param operation operation with null id. It's coming from DTO
     * @return saved operation with unique id
     */
    @Override
    public Operation save(Operation operation) {
        operation.setId(identity.incrementAndGet());
        operationStorageService.add(operation);
        return operation;
    }

    /**
     * Method for deleting a operation
     *
     * @param id operation unique identifier
     */
    @Override
    public void removeById(long id) {
        operationStorageService.removeByElement(findById(id).orElseThrow(() -> new OperationNotFound(id)));
    }
}
