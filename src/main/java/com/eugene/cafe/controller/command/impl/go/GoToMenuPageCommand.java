package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.RequestParameter.*;
import static com.eugene.cafe.controller.command.AttributeName.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.eugene.cafe.model.validator.ParamValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToMenuPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GoToMenuPageCommand.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);

        if (!ParamValidator.validatePageNumber(pageNumberParam)) {
            logger.error("Page number is invalid");
            return new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        String sortOrder = (String) request.getSession().getAttribute(MENU_ITEMS_SORT_ORDER);
        Category currentCategory = (Category) request.getSession().getAttribute(MENU_ITEMS_CURRENT_CATEGORY);

        int pageNumber = Integer.parseInt(pageNumberParam);
        Router router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
        try {
            MenuItemSortOrder order = MenuItemSortOrder.valueOf(sortOrder.toUpperCase());
            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(pageNumber, order, currentCategory);

            request.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            request.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, pageNumber);

        } catch (ServiceException e) {
            logger.error("Unable to load menu page: " + pageNumberParam, e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
