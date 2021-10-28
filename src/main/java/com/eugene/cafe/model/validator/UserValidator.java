package com.eugene.cafe.model.validator;

public class UserValidator {

    private static final String EMAIL_REGEX = "^(?=.{3,30}$)[\\w.]+@[\\w.]+$";

    private static final String PASSWORD_REGEX = "^.{8,40}$";

    private static final String NAME_SURNAME_REGEX = "^(\\p{L}){3,20}$";

    private static final String AMOUNT_REGEX = "^[0-9]+$";

    public static boolean validateUser(String name, String surname, String email, String password) {
        return validateName(name) && (surname == null || surname.isEmpty() || validateSurname(surname))
                && validateEmail(email) && validatePassword(password);
    }

    public static boolean validateUser(String name, String surname, String email) {
        return validateName(name) && (surname == null || surname.isEmpty() || validateSurname(surname))
                && validateEmail(email);
    }

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name) {
        return name.matches(NAME_SURNAME_REGEX);
    }

    public static boolean validateSurname(String surname) {
        return surname.matches(NAME_SURNAME_REGEX);
    }

    public static boolean validateTopUpAmount(String amount) {
        return amount.matches(AMOUNT_REGEX);
    }
}
