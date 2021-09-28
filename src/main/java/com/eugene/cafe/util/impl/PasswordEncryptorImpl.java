package com.eugene.cafe.util.impl;

import com.eugene.cafe.util.PasswordEncryptor;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptorImpl implements PasswordEncryptor {

    public boolean checkPassword(String plainTextPass, String hash) {
        return BCrypt.checkpw(plainTextPass, hash);
    }

    public String encryptPassword(String plainTextPass) {
        return BCrypt.hashpw(plainTextPass, BCrypt.gensalt());
    }
}
