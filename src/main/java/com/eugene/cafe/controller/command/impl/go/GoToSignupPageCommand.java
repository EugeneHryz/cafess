package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.PagePath;
import com.eugene.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class GoToSignupPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(PagePath.SIGNUP_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
