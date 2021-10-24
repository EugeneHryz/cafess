package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.PagePath.*;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoToCheckoutPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GoToCheckoutPageCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        Map<MenuItem, Integer> shoppingCart = (HashMap<MenuItem, Integer>) request.getSession().getAttribute(SHOPPING_CART);

        Router router = new Router(CHECKOUT_PAGE, Router.RouterType.FORWARD);
        try {
            double total = orderService.calculateOrderTotal(shoppingCart);
            List<LocalTime> pickupTimes = createPickupTimeList();

            request.getSession().setAttribute(ORDER_TOTAL, total);
            request.setAttribute(PICKUP_TIME_LIST, pickupTimes);

        } catch (ServiceException e) {
            logger.error("Unable to calculate order total", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }

    private List<LocalTime> createPickupTimeList() {
        LocalTime currentTime = LocalTime.now();

        LocalTime nearestPickupTime;
        int minutes = currentTime.getMinute();
        if (minutes > 30) {
            nearestPickupTime = LocalTime.of(currentTime.plusHours(1).getHour(), 30);
        } else {
            nearestPickupTime = LocalTime.of(currentTime.plusHours(1).getHour(), 0);
        }

        List<LocalTime> pickupTimes = new ArrayList<>();
        LocalTime time = nearestPickupTime;
        while (time.isBefore(LocalTime.of(22, 0))
                || time.equals(LocalTime.of(22, 0))) {

            if (time.isAfter(LocalTime.of(9, 0))) {
                pickupTimes.add(time);
            }
            time = time.plusMinutes(30);
        }
        return pickupTimes;
    }
}
