package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class GoToOrderPageCommand implements Command {

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);
        // todo: validate page number
        int pageNumber = Integer.parseInt(pageNumberParam);

        Router router = new Router(ORDERS_PAGE, Router.RouterType.FORWARD);
        try {
            List<Order> orders = orderService.getSubsetOfUserOrders(0, pageNumber);
            int orderCount = orderService.getUserOrderCount(0);

            request.setAttribute(ORDERS_SUBLIST, orders);
            request.setAttribute(ORDERS_PAGE_NUMBER, pageNumber);
            request.setAttribute(ORDER_COUNT, orderCount);

        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
