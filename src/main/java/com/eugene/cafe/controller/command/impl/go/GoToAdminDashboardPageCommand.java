package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserRole;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestAttribute.*;

public class GoToAdminDashboardPageCommand implements Command {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(ADMIN_DASHBOARD, Router.RouterType.REDIRECT);
//        try {
//            List<User> usersWithoutAdmins = userService.getAllUsersWithoutAdmins();
//
//            request.getSession().setAttribute(USERS_LIST, usersWithoutAdmins);
//
//        } catch (ServiceException e) {
//            // todo: write log
//            request.getSession().setAttribute(EXCEPTION, e);
//            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
//        }

        return router;
    }
}
