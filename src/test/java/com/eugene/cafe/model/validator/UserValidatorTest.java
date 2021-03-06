package com.eugene.cafe.model.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    @Test
    public void emailValidationShouldBeCorrect() {
        String email = "qwe_rty@com";
        boolean result = UserValidator.validateEmail(email);
        assertTrue(result);
    }

    @Test
    public void emailValidationShouldBeIncorrect() {
        String email = "bn";
        boolean result = UserValidator.validateEmail(email);
        assertFalse(result);
    }

    @Test
    public void passwordValidationShouldBeCorrect() {
        String password = "jkaqur67Q_tt";
        boolean result = UserValidator.validatePassword(password);
        assertTrue(result);
    }

    @Test
    public void passwordValidationShouldBeIncorrect() {
        String password = "a12ni";
        boolean result = UserValidator.validatePassword(password);
        assertFalse(result);
    }

    @Test
    public void nameValidationShouldBeCorrect() {
        String name = "aqwsde";
        boolean result = UserValidator.validateName(name);
        assertTrue(result);
    }

    @Test
    public void nameValidationShouldBeIncorrect() {
        String name = "ячшшшшшшш";
        boolean result = UserValidator.validateName(name);
        assertFalse(result);
    }
}
