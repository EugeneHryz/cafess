package com.eugene.cafe.controller.command;

import com.eugene.cafe.controller.command.impl.*;

import java.util.EnumMap;

public class CommandProvider {

    private static CommandProvider instance;

    private final EnumMap<CommandType, Command> commandMap = new EnumMap<>(CommandType.class);

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    private CommandProvider() {
        commandMap.put(CommandType.LOG_IN, new LogInCommand());
        commandMap.put(CommandType.LOG_OUT, new LogOutCommand());
        commandMap.put(CommandType.DEFAULT, new DefaultCommand());
        commandMap.put(CommandType.GO_TO_FORGOT_PASSWORD_PAGE, new GoToForgotPasswordPageCommand());
        commandMap.put(CommandType.GO_TO_SIGNUP_PAGE, new GoToSignupPageCommand());
        commandMap.put(CommandType.SIGN_UP, new SignUpCommand());
        commandMap.put(CommandType.CHANGE_LOCALE, new ChangeLocaleCommand());
    }

    public Command getCommand(String commandName) {
        Command command;
        if (commandName != null) {
            try {
                command = commandMap.get(CommandType.valueOf(commandName.toUpperCase()));
            } catch (IllegalArgumentException e) {
                command = commandMap.get(CommandType.DEFAULT);
            }
        } else {
            command = commandMap.get(CommandType.DEFAULT);
        }
        return command;
    }
}
