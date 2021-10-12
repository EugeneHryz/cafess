package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.eugene.cafe.controller.command.PagePath.ERROR_PAGE;
import static com.eugene.cafe.controller.command.PagePath.MAIN_PAGE;
import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

public class ChangeCurrentCategoryCommand implements Command {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String categoryIdParam = request.getParameter(PARAM_CATEGORY_ID);
        int categoryId = Integer.parseInt(categoryIdParam);

        // fixme: maybe need to change later
        List<Category> categories = (List<Category>) request.getServletContext().getAttribute(MENU_CATEGORIES_LIST);
        Category newCategory = null;
        for (Category category : categories) {
            if (category.getId() == categoryId) {
                newCategory = category;
            }
        }

        String sortOrder = (String) request.getSession().getAttribute(MENU_ITEMS_SORT_ORDER);
        MenuItemSortOrder order = MenuItemSortOrder.valueOf(sortOrder.toUpperCase());

        Router router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
        try {
            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(1, order, newCategory);

            int menuItemsCount = menuService.getMenuItemCountByCategory(newCategory);

            request.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            request.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, 1);
            request.getSession().setAttribute(MENU_ITEMS_CURRENT_CATEGORY, newCategory);
            request.getSession().setAttribute(MENU_ITEMS_COUNT, menuItemsCount);

        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
