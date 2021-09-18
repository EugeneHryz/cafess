package com.eugene.cafe.util;

import com.eugene.cafe.util.impl.PasswordEncryptorImpl;
import org.junit.Test;

import static org.junit.Assert.*;

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
