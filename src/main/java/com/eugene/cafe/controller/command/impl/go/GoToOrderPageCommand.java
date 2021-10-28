package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import com.eugene.cafe.model.validator.ParamValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class GoToOrderPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GoToOrderPageCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);

        if (!ParamValidator.validatePageNumber(pageNumberParam)) {
            logger.error("Page number is invalid");
            return new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        int pageNumber = Integer.parseInt(pageNumberParam);
        Router router = new Router(ORDERS_PAGE, Router.RouterType.FORWARD);
        try {
            List<Order> orders = orderService.getSubsetOfUserOrders(0, pageNumber);
            int orderCount = orderService.getUserOrderCount(0);

            request.setAttribute(ORDERS_SUBLIST, orders);
            request.setAttribute(ORDERS_PAGE_NUMBER, pageNumber);
            request.setAttribute(ORDER_COUNT, orderCount);

        } catch (ServiceException e) {
            logger.error("Unable to load order page " + pageNumberParam, e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
