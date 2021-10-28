package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.eugene.cafe.controller.command.PagePath.*;

public class GoToAdminDashboardPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(ADMIN_DASHBOARD, Router.RouterType.REDIRECT);
        return router;
    }
}
