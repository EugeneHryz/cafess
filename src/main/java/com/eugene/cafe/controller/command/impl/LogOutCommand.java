package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.PagePath;
import com.eugene.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {

        request.getSession(false).invalidate();

        Router router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
