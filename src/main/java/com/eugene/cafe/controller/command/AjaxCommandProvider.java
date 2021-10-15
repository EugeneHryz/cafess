package com.eugene.cafe.controller.command;

import com.eugene.cafe.controller.command.impl.ajax.GetUserCountCommand;

import java.util.EnumMap;

import static com.eugene.cafe.controller.command.CommandType.*;

public class AjaxCommandProvider {

    private static AjaxCommandProvider instance;

    private final EnumMap<CommandType, AjaxCommand> commandMap = new EnumMap<>(CommandType.class);

    public static AjaxCommandProvider getInstance() {
        if (instance == null) {
            instance = new AjaxCommandProvider();
        }
        return instance;
    }

    private AjaxCommandProvider() {
        commandMap.put(GET_USER_COUNT, new GetUserCountCommand());
    }

    public AjaxCommand getCommand(String commandName) {
        AjaxCommand command;
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
