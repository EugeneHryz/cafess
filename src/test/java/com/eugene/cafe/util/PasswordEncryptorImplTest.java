package com.eugene.cafe.util;

import com.eugene.cafe.util.impl.PasswordEncryptorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordEncryptorImplTest {

    private static final PasswordEncryptor encryptor = new PasswordEncryptorImpl();

    @Test
    public void passwordEncryptionShouldBeCorrect() {
        String plainTextPass = "abcd1234";
        String encrypted = encryptor.encryptPassword(plainTextPass);
        boolean passwordsAreEqual = encryptor.checkPassword(plainTextPass, encrypted);
        assertTrue(passwordsAreEqual);
    }
}
