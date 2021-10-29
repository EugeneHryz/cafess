package com.eugene.cafe.controller.listener;

import com.eugene.cafe.controller.command.AttributeName;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static com.eugene.cafe.controller.command.AttributeName.*;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    private static final Logger logger = LogManager.getLogger(HttpSessionListenerImpl.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        try {
            List<MenuItem> menuItems = menuService.getSubsetOfActiveMenuItems(1, MenuSortOrder.PRICE_ASCENDING, null);
            int menuItemsCount = menuService.getMenuItemCountByCategory(null, true);

            se.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            se.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, 1);
            se.getSession().setAttribute(MENU_ITEMS_COUNT, menuItemsCount);
            se.getSession().setAttribute(MENU_ITEMS_SORT_ORDER, MenuSortOrder.PRICE_ASCENDING.name().toLowerCase());
            se.getSession().setAttribute(SHOPPING_CART, new HashMap<MenuItem, Integer>());

        } catch (ServiceException e) {
            logger.error("Unable to initialize session");
        }

        se.getSession().setAttribute(AttributeName.LOCALE, "ru-RU");
    }
}
