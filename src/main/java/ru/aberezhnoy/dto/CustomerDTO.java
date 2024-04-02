package ru.aberezhnoy.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aberezhnoy.domain.model.Customer;

@NoArgsConstructor
@Data
public class CustomerDTO {
    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String surname;
    private String birthDate;
    private String gender;
    @Email
    private String email;
    @NotBlank
    @Digits(integer = 11, fraction = 0)
    private String phoneNumber;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.firstname = customer.getFirstname();
        this.lastname = customer.getLastname();
        this.surname = customer.getSurname();
        this.birthDate = String.valueOf(customer.getBirthDate());
        this.gender = String.valueOf(customer.getGender());
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
    }
}
