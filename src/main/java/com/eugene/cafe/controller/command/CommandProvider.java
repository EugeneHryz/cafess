package com.eugene.cafe.controller.command;

import com.eugene.cafe.controller.command.impl.*;
import static com.eugene.cafe.controller.command.CommandType.*;

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
        commandMap.put(LOG_IN, new LogInCommand());
        commandMap.put(LOG_OUT, new LogOutCommand());
        commandMap.put(DEFAULT, new DefaultCommand());
        commandMap.put(GO_TO_FORGOT_PASSWORD_PAGE, new GoToForgotPasswordPageCommand());
        commandMap.put(GO_TO_SIGNUP_PAGE, new GoToSignupPageCommand());
        commandMap.put(SIGN_UP, new SignUpCommand());
        commandMap.put(CHANGE_LOCALE, new ChangeLocaleCommand());
        commandMap.put(GO_TO_PROFILE_SETTINGS_PAGE, new GoToProfileSettingsPage());
        commandMap.put(EDIT_USER_PROFILE, new EditUserProfileCommand());
        commandMap.put(UPDATE_PROFILE_PICTURE, new UpdateProfilePictureCommand());
        commandMap.put(CHANGE_PASSWORD, new ChangePasswordCommand());
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
