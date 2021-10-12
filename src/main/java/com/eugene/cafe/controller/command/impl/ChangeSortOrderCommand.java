package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ChangeSortOrderCommand implements Command {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String newSortOrder = request.getParameter(PARAM_SORT_ORDER);
        MenuItemSortOrder order = MenuItemSortOrder.valueOf(newSortOrder.toUpperCase());

        Category category = (Category) request.getSession().getAttribute(MENU_ITEMS_CURRENT_CATEGORY);

        Router router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
        try {
            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(1, order, category);

            request.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            request.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, 1);
            request.getSession().setAttribute(MENU_ITEMS_SORT_ORDER, newSortOrder);

        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
