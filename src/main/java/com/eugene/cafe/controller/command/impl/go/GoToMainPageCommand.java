package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.PagePath.*;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GoToMainPageCommand implements Command {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(MAIN_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
