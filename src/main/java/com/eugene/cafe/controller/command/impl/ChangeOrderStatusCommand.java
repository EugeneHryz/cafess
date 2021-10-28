package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.AttributeName.ORDER_COUNT;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.entity.OrderStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChangeOrderStatusCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangeOrderStatusCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String orderIdParam = request.getParameter(PARAM_ORDER_ID);
        String orderStatus = request.getParameter(PARAM_ORDER_STATUS);

        Router router = new Router(ORDERS_PAGE, Router.RouterType.FORWARD);
        try {
            int orderId = Integer.parseInt(orderIdParam);
            OrderStatus status = OrderStatus.valueOf(orderStatus.toUpperCase());

            if (orderService.changeOrderStatus(orderId, status)) {

                List<Order> orders = orderService.getSubsetOfUserOrders(0, 1);
                int orderCount = orderService.getUserOrderCount(0);

                request.setAttribute(ORDERS_SUBLIST, orders);
                request.setAttribute(ORDERS_PAGE_NUMBER, 1);
                request.setAttribute(ORDER_COUNT, orderCount);
            }
        } catch (ServiceException e) {
            logger.error("Unable to change order status (order id: " + orderIdParam + ")", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
