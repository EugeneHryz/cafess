package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

public class SaveReviewCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SaveReviewCommand.class);

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);

        Locale locale = Locale.forLanguageTag((String) request.getSession(false).getAttribute(LOCALE));
        ResourceManager manager = new ResourceManager("message", locale);

        String ratingParam = request.getParameter(PARAM_RATING);
        short rating = ratingParam != null ? Short.parseShort(ratingParam) : 0;

        String orderIdParam = request.getParameter(PARAM_ORDER_ID);
        int orderId = Integer.parseInt(orderIdParam);
        String comment = request.getParameter(PARAM_COMMENT);

        Router router = new Router(ORDER_HISTORY_PAGE, Router.RouterType.FORWARD);
        try {
            if (orderService.saveOrderReview(orderId, rating, comment)) {
                request.setAttribute(SAVE_REVIEW_MESSAGE, manager.getProperty(SAVE_REVIEW_SUCCESS));
            } else {
                request.setAttribute(SAVE_REVIEW_MESSAGE, manager.getProperty(SAVE_REVIEW_FAIL));
            }

            List<Order> userOrders = orderService.getSubsetOfUserOrders(user.getId(), 1);
            int orderCount = orderService.getUserOrderCount(user.getId());

            request.setAttribute(ORDERS_SUBLIST, userOrders);
            request.setAttribute(ORDERS_PAGE_NUMBER, 1);
            request.setAttribute(ORDER_COUNT, orderCount);

        } catch (ServiceException e) {
            logger.error("Unable to save review", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
