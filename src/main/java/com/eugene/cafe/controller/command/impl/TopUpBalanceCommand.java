package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.AttributeName.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TopUpBalanceCommand implements Command {

    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        String topUpAmount = request.getParameter(PARAM_TOP_UP_AMOUNT);

        Router router = new Router(PROFILE_SETTINGS_PAGE, Router.RouterType.REDIRECT);
        try {
            Optional<User> updatedUser = userService.topUpUserBalance(user.getId(), topUpAmount);
            updatedUser.ifPresent(value -> request.getSession().setAttribute(USER, value));

        } catch (ServiceException e) {
            logger.error("Unable to top up balance", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
