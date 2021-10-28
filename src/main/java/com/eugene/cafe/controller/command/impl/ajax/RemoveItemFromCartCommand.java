package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

public class RemoveItemFromCartCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(RemoveItemFromCartCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String menuItemId = request.getParameter(PARAM_MENU_ITEM_ID);

        Map<MenuItem, Integer> shoppingCart = (HashMap<MenuItem, Integer>) request.getSession().getAttribute(SHOPPING_CART);
        int itemId = Integer.parseInt(menuItemId);

        if (shoppingCart != null && !shoppingCart.isEmpty()) {

            Optional<MenuItem> itemToDelete = shoppingCart.keySet()
                    .stream()
                    .filter(key -> key.getId() == itemId)
                    .findFirst();

            if (itemToDelete.isPresent()) {
                shoppingCart.remove(itemToDelete.get());

                int totalItemsInCart = 0;
                for (Integer value : shoppingCart.values()) {
                    totalItemsInCart += value;
                }
                double newOrderTotal = orderService.calculateOrderTotal(shoppingCart);

                request.getSession().setAttribute(SHOPPING_CART_SIZE, totalItemsInCart);
                request.getSession().setAttribute(ORDER_TOTAL, newOrderTotal);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(ORDER_TOTAL, newOrderTotal);
                jsonObject.addProperty(SHOPPING_CART_SIZE, totalItemsInCart);

                String jsonData = new Gson().toJson(jsonObject);
                try {
                    response.getWriter().write(jsonData);
                } catch (IOException e) {
                    logger.error("Error while writing a response body", e);
                }
            }
        } else {
            response.setStatus(500);
            try {
                response.getWriter().write("Unable to remove item from the cart because the cart is null or empty");
            } catch (IOException e) {
                logger.error("Error while writing a response body", e);
            }
        }
    }
}
