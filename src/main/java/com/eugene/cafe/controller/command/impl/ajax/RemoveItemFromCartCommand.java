package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.MenuItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

public class RemoveItemFromCartCommand implements AjaxCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
                double newOrderTotal = calculateOrderTotal(shoppingCart);

                request.getSession().setAttribute(SHOPPING_CART_SIZE, totalItemsInCart);
                request.setAttribute(ORDER_TOTAL, newOrderTotal);

                JsonObject jsonObject = new JsonObject();
                // todo: place those strings in constants
                jsonObject.addProperty("order_total", newOrderTotal);
                jsonObject.addProperty("shopping_cart_size", totalItemsInCart);

                String jsonData = new Gson().toJson(jsonObject);
                response.getWriter().write(jsonData);
            }
        }
    }

    // fixme: duplicated code
    private double calculateOrderTotal(Map<MenuItem, Integer> shoppingCart) {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : shoppingCart.entrySet()) {
            total += (entry.getValue() * entry.getKey().getPrice());
        }

        BigDecimal bigDecimal = BigDecimal.valueOf(total);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}
