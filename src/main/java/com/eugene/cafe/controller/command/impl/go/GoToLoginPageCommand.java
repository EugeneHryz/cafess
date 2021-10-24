package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class GoToLoginPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(LOGIN_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
