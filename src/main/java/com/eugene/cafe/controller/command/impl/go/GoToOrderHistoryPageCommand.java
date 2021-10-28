package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.entity.User;
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

public class GoToOrderHistoryPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GoToOrderHistoryPageCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);

        if (!ParamValidator.validatePageNumber(pageNumberParam)) {
            logger.error("Page number is invalid");
            return new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        Router router = new Router(ORDER_HISTORY_PAGE, Router.RouterType.FORWARD);
        int pageNumber = Integer.parseInt(pageNumberParam);
        try {
            List<Order> userOrders = orderService.getSubsetOfUserOrders(user.getId(), pageNumber);
            int orderCount = orderService.getUserOrderCount(user.getId());

            request.setAttribute(ORDERS_SUBLIST, userOrders);
            request.setAttribute(ORDERS_PAGE_NUMBER, pageNumber);
            request.setAttribute(ORDER_COUNT, orderCount);

        } catch (ServiceException e) {
            logger.error("Unable to load order history page " + pageNumberParam, e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
