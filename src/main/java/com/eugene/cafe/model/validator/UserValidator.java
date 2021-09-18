package com.eugene.cafe.model.validator;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[\\w_.]+@[\\w.]+$";

    private static final String PASSWORD_REGEX = "^.{8,}$";

    private static final String NAME_SURNAME_REGEX = "^(\\p{L}){4,}$";

    public static boolean validateUser(String name, String surname, String email, String password) {
        return validateName(name) && validateSurname(surname) && validateEmail(email) && validatePassword(password);
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name) {
        return name != null && name.matches(NAME_SURNAME_REGEX);
    }

    public static boolean validateSurname(String surname) {
        return surname != null && surname.matches(NAME_SURNAME_REGEX);
    }
}
