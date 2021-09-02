package com.eugene.cafe.command;

import com.eugene.cafe.command.impl.DefaultCommand;
import com.eugene.cafe.command.impl.LogInCommand;
import com.eugene.cafe.command.impl.LogOutCommand;

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
    }

    public Command getCommand(CommandType commandType) {
        Command command = commandMap.get(CommandType.DEFAULT);
        try {
            command = commandMap.get(commandType);
        } catch (IllegalArgumentException e) {

        }
        return command;
    }
}
