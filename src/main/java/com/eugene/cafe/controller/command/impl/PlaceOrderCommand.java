package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.AttributeName.*;

import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dto.UserDto;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlaceOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(PlaceOrderCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        UserDto user = (UserDto) request.getSession().getAttribute(USER);
        int userId = user.getId();

        if (user.getStatus() == UserStatus.BANNED) {
            logger.error("User can't place orders because he is banned");
            return new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        String pickupTimeParam = request.getParameter(PARAM_PICKUP_TIME);
        LocalTime pickupTime = LocalTime.parse(pickupTimeParam);

        Map<MenuItem, Integer> menuItems = (HashMap<MenuItem, Integer>) request.getSession().getAttribute(SHOPPING_CART);
        double orderTotal = (Double) request.getSession().getAttribute(ORDER_TOTAL);

        Router router;
        try {
            Optional<User> updatedUser = orderService.placeOrder(userId, orderTotal, menuItems, pickupTime);
            if (updatedUser.isPresent()) {

                request.getSession().setAttribute(SHOPPING_CART, new HashMap<MenuItem, Integer>());
                request.getSession().removeAttribute(ORDER_TOTAL);
                request.getSession().removeAttribute(SHOPPING_CART_SIZE);
                request.getSession().setAttribute(USER, updatedUser.get());

                router = new Router(ORDER_SUCCESS, Router.RouterType.REDIRECT);
            } else {
                router = new Router(ORDER_FAIL, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to place order", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
