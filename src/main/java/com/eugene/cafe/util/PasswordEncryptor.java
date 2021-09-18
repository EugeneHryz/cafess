package com.eugene.cafe.util;

public interface PasswordEncryptor {

    boolean checkPassword(String plainText, String hash);

    String encryptPassword(String plainText);
}
