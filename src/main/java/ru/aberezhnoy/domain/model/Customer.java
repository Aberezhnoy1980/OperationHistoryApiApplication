package ru.aberezhnoy.domain.model;

import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.util.CustomerValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Simple POJO class, describing customer entity
 */
public class Customer implements Contract.Model.ConsolePrintable, Comparable<Customer> {
    private long id;
    private String firstname;
    private String lastname;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private Set<Operation> operations;

    public Customer() {
    }

    private Customer(Long id, String firstname, String lastname, String surname, String birthDate, String gender, String email, String phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.surname = surname;
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.operations = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender.toUpperCase());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    /**
     * Method for adding and link operation with customer while operation creating
     *
     * @param operation new created {@link Operation}
     */
    public void addOperations(Operation operation) {
        this.operations.add(operation);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender.toString();
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        String fullname = firstname.toUpperCase().charAt(0) +
                "." + lastname.toUpperCase().charAt(0) +
                "." + surname;
        return String.format("Customer id; %d, full name: %s, day of birth: %s", id, fullname, birthDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return id == customer.id
                && Objects.equals(phoneNumber, customer.phoneNumber)
                && Objects.equals(firstname, customer.firstname)
                && Objects.equals(lastname, customer.lastname)
                && Objects.equals(surname, customer.surname)
                && gender == customer.gender
                && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, surname, gender, email, phoneNumber);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int compareTo(Customer o) {
        if (birthDate.isAfter(o.birthDate)) return 1;
        else if (this.birthDate.isBefore(o.birthDate)) return -1;
        else return 0;
    }

    @Override
    public void print() {
        System.out.printf("Id: %d, surname: %s%n", id, surname);
    }

    private enum Gender {
        MALE, FEMALE
    }

    public static class Builder {
        private final Customer customer;

        public Builder() {
            this.customer = new Customer();
            this.customer.operations = new HashSet<>();
        }

        public Builder setFirstname(String firstname) {
            if (CustomerValidator.validateName(firstname))
                this.customer.setFirstname(firstname);
            return this;
        }

        public Builder setLastname(String lastname) {
            if (CustomerValidator.validateName(lastname))
                this.customer.setLastname(lastname);
            return this;
        }

        public Builder setSurname(String surname) {
            if (CustomerValidator.validateName(surname))
                this.customer.setSurname(surname);
            return this;
        }

        public Builder setBirthDate(String birthDate) {
            if (CustomerValidator.validateBirthdate(birthDate))
                this.customer.setBirthDate(birthDate);
            return this;
        }

        public Builder setGender(String gender) {
//            if (CustomerValidator.validateGender(gender))
            this.customer.setGender(gender);
            return this;
        }

        public Builder setEmail(String email) {
            if (CustomerValidator.validateEmail(email))
                this.customer.setEmail(email);
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            if (CustomerValidator.validatePhoneNumber(phoneNumber))
                this.customer.setPhoneNumber(phoneNumber);
            return this;
        }

        public Customer build() {
            return this.customer;
        }
    }
}
