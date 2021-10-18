package com.eugene.cafe.controller.listener;

import com.eugene.cafe.controller.command.RequestAttribute;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.HashMap;
import java.util.List;

import static com.eugene.cafe.controller.command.RequestAttribute.*;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        try {
            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(1, MenuItemSortOrder.PRICE_ASCENDING, null);
            int menuItemsCount = menuService.getMenuItemCountByCategory(null);

            se.getSession().setAttribute(MENU_ITEMS_SUBLIST, menuItems);
            se.getSession().setAttribute(MENU_ITEMS_PAGE_NUMBER, 1);
            se.getSession().setAttribute(MENU_ITEMS_COUNT, menuItemsCount);
            se.getSession().setAttribute(MENU_ITEMS_SORT_ORDER, MenuItemSortOrder.PRICE_ASCENDING.name().toLowerCase());
            se.getSession().setAttribute(SHOPPING_CART, new HashMap<MenuItem, Integer>());

        } catch (ServiceException e) {
            // fixme
        }

        se.getSession().setAttribute(RequestAttribute.LOCALE, "ru-RU");
    }
}
