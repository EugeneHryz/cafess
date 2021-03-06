package com.eugene.cafe.controller.command;

import com.eugene.cafe.controller.command.impl.*;
import com.eugene.cafe.controller.command.impl.go.*;

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
        commandMap.put(GO_TO_SIGNUP_PAGE, new GoToSignupPageCommand());
        commandMap.put(GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commandMap.put(SIGN_UP, new SignUpCommand());
        commandMap.put(CHANGE_LOCALE, new ChangeLocaleCommand());
        commandMap.put(GO_TO_PROFILE_PAGE, new GoToProfileSettingsPageCommand());
        commandMap.put(EDIT_USER_PROFILE, new EditUserProfileCommand());
        commandMap.put(UPDATE_PROFILE_PICTURE, new UpdateProfilePictureCommand());
        commandMap.put(CHANGE_PASSWORD, new ChangePasswordCommand());
        commandMap.put(GO_TO_ADMIN_DASHBOARD_PAGE, new GoToAdminDashboardPageCommand());
        commandMap.put(ADD_MENU_ITEM, new AddMenuItemCommand());
        commandMap.put(GO_TO_MENU_PAGE, new GoToMenuPageCommand());
        commandMap.put(CHANGE_SORT_ORDER, new ChangeSortOrderCommand());
        commandMap.put(CHANGE_CURRENT_CATEGORY, new ChangeCurrentCategoryCommand());
        commandMap.put(TOP_UP_BALANCE, new TopUpBalanceCommand());
        commandMap.put(GO_TO_CHECKOUT_PAGE, new GoToCheckoutPageCommand());
        commandMap.put(PLACE_ORDER, new PlaceOrderCommand());
        commandMap.put(GO_TO_ORDER_HISTORY_PAGE, new GoToOrderHistoryPageCommand());
        commandMap.put(SAVE_REVIEW, new SaveReviewCommand());
        commandMap.put(GO_TO_ORDERS_PAGE, new GoToOrderPageCommand());
        commandMap.put(CHANGE_ORDER_STATUS, new ChangeOrderStatusCommand());
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
