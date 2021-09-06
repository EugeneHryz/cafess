package com.eugene.cafe._main;

import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.util.PasswordEncryptor;

public class Main {

    public static void main(String[] args) {

        ResourceManager manager = new ResourceManager("messages");
        System.out.println(manager.getProperty("invalidLoginOrPassMessage"));
    }
}
