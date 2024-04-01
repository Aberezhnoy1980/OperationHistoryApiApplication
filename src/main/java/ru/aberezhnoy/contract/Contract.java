package ru.aberezhnoy.contract;

import ru.aberezhnoy.domain.model.Customer;
import ru.aberezhnoy.domain.model.Operation;

import java.util.List;
import java.util.Optional;

/**
 * End-to-end interface for the MVC project
 */
public interface Contract {
    interface View {
        void run();
    }

    /**
     * An interface contains CRUD operation methods for repository classes
     *
     * @param <T> type of value
     */
    interface Model<T extends Model.ConsolePrintable> {
        /**
         * Method for getting all saved type instances from a data source
         *
         * @return an {@link Optional} describing the given parametrized list of type instances,
         * if non-{@code null}, otherwise returns an empty {@link Optional}.
         */
        Optional<List<T>> findAll();

        /**
         * Method for getting type instance from a data sources by ID
         *
         * @param id - a type instance field containing a unique identifier
         * @return an {@link Optional} describing the given type instance,
         * if non-{@code null}, otherwise returns an empty {@link Optional}.
         */

        Optional<T> findById(long id);

        /**
         * Method that allows to add and save type instance to data source
         *
         * @param element type instance
         * @return the type instance that was saved and received the identifier
         */
        T save(T element);

        /**
         * Method for delete type instance from data source
         *
         * @param id - a type instance field containing a unique identifier
         */
        void removeById(long id);

        /**
         * An interface containing console display method for operations parameters
         */
        interface ConsolePrintable {
            /**
             * Method for child type parameters display in console
             */
            void print();
        }

    }

    interface Controller {
        void saveCustomer(Customer customer);

        void saveOperation(Operation operation);

        void showAllCustomers();

        void showAllOperations();

        void showOperationsByCustomer(long id);
    }
}
