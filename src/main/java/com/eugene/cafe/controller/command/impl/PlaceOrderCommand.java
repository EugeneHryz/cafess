package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.RequestAttribute.*;

import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlaceOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(PlaceOrderCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        int userId = user.getId();

        String pickupTimeParam = request.getParameter(PARAM_PICKUP_TIME);
        LocalTime pickupTime = LocalTime.parse(pickupTimeParam);

        Map<MenuItem, Integer> menuItems = (HashMap<MenuItem, Integer>)
                request.getSession().getAttribute(SHOPPING_CART);
        // do we need to recalculate order total or retrieve it from session?
        double orderTotal = (Double) request.getSession().getAttribute(ORDER_TOTAL);

        Router router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
        try {
            // todo: let the user know if order was placed successfully

            Locale locale = Locale.forLanguageTag((String) request.getSession(false).getAttribute(LOCALE));
            ResourceManager manager = new ResourceManager("message", locale);

            if (orderService.placeOrder(userId, orderTotal, menuItems, pickupTime)) {

                request.getSession().setAttribute(SHOPPING_CART, new HashMap<MenuItem, Integer>());
                request.getSession().removeAttribute(ORDER_TOTAL);
                request.getSession().removeAttribute(SHOPPING_CART_SIZE);

                request.setAttribute(ORDER_RESULT_MESSAGE, manager.getProperty(PLACE_ORDER_SUCCESS));
            } else {
                request.setAttribute(ORDER_RESULT_MESSAGE, manager.getProperty(PLACE_ORDER_FAIL));
            }
        } catch (ServiceException e) {
            logger.error("Failed to place order", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
