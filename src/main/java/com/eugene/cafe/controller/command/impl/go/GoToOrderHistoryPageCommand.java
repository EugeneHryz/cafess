package com.eugene.cafe.controller.command.impl.go;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.OrderService;
import com.eugene.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class GoToOrderHistoryPageCommand implements Command {

    private static final OrderService orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);
        // todo: validate page number
        int pageNumber = Integer.parseInt(pageNumberParam);

        Router router = new Router(ORDER_HISTORY_PAGE, Router.RouterType.FORWARD);
        try {
            List<Order> userOrders = orderService.getSubsetOfUserOrders(user.getId(), pageNumber);
            int orderCount = orderService.getUserOrderCount(user.getId());

            request.setAttribute(ORDERS_SUBLIST, userOrders);
            request.setAttribute(ORDERS_PAGE_NUMBER, pageNumber);
            request.setAttribute(USER_ORDERS_COUNT, orderCount);

        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
