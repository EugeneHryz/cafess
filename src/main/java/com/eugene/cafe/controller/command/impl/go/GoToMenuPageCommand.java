package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.RequestParameter.*;
import static com.eugene.cafe.controller.command.RequestAttribute.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GoToMenuPageCommand implements Command {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);
        // todo: validate page number
        int pageNumber = Integer.parseInt(pageNumberParam);

        String sortOrder = (String) request.getSession().getAttribute(MENU_ITEMS_SORT_ORDER);
        Category currentCategory = (Category) request.getSession().getAttribute(MENU_ITEMS_CURRENT_CATEGORY);

        Router router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
        try {
            MenuItemSortOrder order = MenuItemSortOrder.valueOf(sortOrder.toUpperCase());
            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(pageNumber, order, currentCategory);

            request.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            request.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, pageNumber);

        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
