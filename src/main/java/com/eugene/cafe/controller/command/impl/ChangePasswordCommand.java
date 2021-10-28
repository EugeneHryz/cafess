package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.PagePath.ERROR_PAGE;
import static com.eugene.cafe.controller.command.PagePath.PROFILE_SETTINGS_PAGE;
import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class ChangePasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        int id = user.getId();

        String oldPassword = request.getParameter(PARAM_OLD_PASSWORD);
        String newPassword = request.getParameter(PARAM_NEW_PASSWORD);

        Router router = new Router(PROFILE_SETTINGS_PAGE, Router.RouterType.FORWARD);
        try {
            Locale locale = Locale.forLanguageTag((String) request.getSession(false).getAttribute(LOCALE));
            ResourceManager manager = new ResourceManager("message", locale);

            if (userService.changeUserPassword(id, oldPassword, newPassword)) {
                request.setAttribute(CHANGE_PASSWORD_SUCCESS, manager.getProperty(CHANGE_PASSWORD_SUCCESS));
            } else {
                request.setAttribute(CHANGE_PASSWORD_FAIL, manager.getProperty(CHANGE_PASSWORD_FAIL));
            }
        } catch (ServiceException e) {
            logger.error("Unable to change use password", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
