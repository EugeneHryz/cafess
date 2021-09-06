package com.eugene.cafe.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public static boolean checkPassword(String plainTextPass, String hash) {
        return BCrypt.checkpw(plainTextPass, hash);
    }

    public static String encryptPassword(String plainTextPass) {
        return BCrypt.hashpw(plainTextPass, BCrypt.gensalt());
    }
}
