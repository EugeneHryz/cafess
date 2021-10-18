package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddItemToCartCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(AddItemToCartCommand.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String menuItemId = request.getParameter(PARAM_MENU_ITEM_ID);

        Map<MenuItem, Integer> shoppingCart = (HashMap<MenuItem, Integer>) request.getSession().getAttribute(SHOPPING_CART);

        int itemId = Integer.parseInt(menuItemId);
        try {
            Optional<MenuItem> menuItem = menuService.findMenuItemById(itemId);
            if (menuItem.isPresent()) {

                MenuItem item = menuItem.get();
                Integer count = shoppingCart.get(item);
                if (count == null) {
                    count = 1;
                } else {
                    count++;
                }
                shoppingCart.put(item, count);

                int totalItemsInCart = 0;
                for (Integer value : shoppingCart.values()) {
                    totalItemsInCart += value;
                }
                request.getSession().setAttribute(SHOPPING_CART_SIZE, totalItemsInCart);

                String jsonData = new Gson().toJson(totalItemsInCart);
                response.getWriter().write(jsonData);
            }
        } catch (ServiceException e) {
            // todo write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
