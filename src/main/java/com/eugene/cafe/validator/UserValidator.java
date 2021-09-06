package com.eugene.cafe.validator;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[\\w-.]+@\\w+$";

    private static final String PASSWORD_REGEX = "^.{8,}$";

    // todo: validate name, surname...

    public static boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}
