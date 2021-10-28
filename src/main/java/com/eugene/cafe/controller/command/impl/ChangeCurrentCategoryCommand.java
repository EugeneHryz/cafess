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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.controller.command.PagePath.ERROR_PAGE;
import static com.eugene.cafe.controller.command.PagePath.MAIN_PAGE;
import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

public class ChangeCurrentCategoryCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangeCurrentCategoryCommand.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String categoryIdParam = request.getParameter(PARAM_CATEGORY_ID);
        int categoryId = Integer.parseInt(categoryIdParam);

        String sortOrder = (String) request.getSession().getAttribute(MENU_ITEMS_SORT_ORDER);
        MenuItemSortOrder order = MenuItemSortOrder.valueOf(sortOrder.toUpperCase());

        Router router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
        try {
            Optional<Category> newCategory = menuService.findCategoryById(categoryId);
            Category category = newCategory.orElse(null);

            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(1, order, category);

            int menuItemsCount = menuService.getMenuItemCountByCategory(category);

            request.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            request.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, 1);
            request.getSession().setAttribute(MENU_ITEMS_CURRENT_CATEGORY, category);
            request.getSession().setAttribute(MENU_ITEMS_COUNT, menuItemsCount);

        } catch (ServiceException e) {
            logger.error("Unable to change current category", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
