package com.eugene.cafe.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordEncryptorTest {

    @Test
    public void passwordEncryptionShouldBeCorrect() {
        String plainTextPass = "abcd1234";
        String encrypted = PasswordEncryptor.encryptPassword(plainTextPass);
        boolean passwordsAreEqual = PasswordEncryptor.checkPassword(plainTextPass, encrypted);
        assertTrue(passwordsAreEqual);
    }
}
