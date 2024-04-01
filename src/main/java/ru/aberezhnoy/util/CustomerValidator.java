package ru.aberezhnoy.util;

import ru.aberezhnoy.exception.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerValidator {

    public static boolean validateName(String name) {
        if (!name.matches("[A-Za-z]+")) throw new IncorrectNameFormatException();
        return true;
    }

    public static boolean validateBirthdate(String date) {
        if (!date.matches("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[12])/(19[0-9][0-9]|20[0-1][0-6])"))
            throw new DateFormatException(String.format("Wrong date format: '%s' does not match the pattern \"dd/MM/yyyy\" ", date));
        LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (d.isAfter((LocalDate.now().minusYears(18))))
            throw new DateFormatException("The customer must be an adult");
        return true;
    }

    public static boolean validateGender(String gender) {
        if (!gender.matches("[FEMALfemal]")) throw new IncorrectGenderFormatException();
        return true;
    }

    public static boolean validateEmail(String email) {
        if (!email.matches("\\w+([._-]?\\w+)*@\\w+([._-]?\\w+)*\\.\\w{2,4}")) throw new IncorrectEmailFormatException();
        return true;
    }

    public static boolean validatePhoneNumber(String number) {
        if (!number.matches("[+0-9]{11,12}")) throw new PhoneNumberFormatException(number);
        return true;
    }
}
