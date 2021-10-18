package com.eugene.cafe.controller.command;

import com.eugene.cafe.controller.command.impl.ajax.*;

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
        commandMap.put(GO_TO_USER_PAGE, new GoToUserPageCommand());
        commandMap.put(BAN_USER, new BanUserCommand());
        commandMap.put(UNBAN_USER, new UnbanUserCommand());
        commandMap.put(ADD_ITEM_TO_CART, new AddItemToCartCommand());
        commandMap.put(REMOVE_ITEM_FROM_CART, new RemoveItemFromCartCommand());
    }

    public AjaxCommand getCommand(String commandName) {
        AjaxCommand command = commandMap.get(CommandType.valueOf(commandName.toUpperCase()));
//        if (commandName != null) {
//            try {
//
//            } catch (IllegalArgumentException e) {
//                command = commandMap.get(CommandType.DEFAULT);
//            }
//        } else {
//            command = commandMap.get(CommandType.DEFAULT);
//        }
        return command;
    }
}
