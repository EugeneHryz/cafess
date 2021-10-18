package com.eugene.cafe.model.service.impl;

import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.OrderDao;
import com.eugene.cafe.model.dao.TransactionHelper;
import com.eugene.cafe.model.dao.UserDao;
import com.eugene.cafe.model.dao.impl.OrderDaoImpl;
import com.eugene.cafe.model.dao.impl.UserDaoImpl;
import com.eugene.cafe.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public boolean placeOrder(int userId, double orderTotal, Map<MenuItem, Integer> menuItems, LocalTime pickupTime) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final OrderDao orderDao = new OrderDaoImpl();

        List<MenuItem> menuItemsAsList = new ArrayList<>();
        for (Map.Entry<MenuItem, Integer> entry : menuItems.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                menuItemsAsList.add(entry.getKey());
            }
        }
        boolean result = false;
        try {
            helper.beginTransaction(orderDao);

            LocalDateTime dateTime = pickupTime.atDate(LocalDate.now());
            Order.Builder builder = new Order.Builder();
            builder.setUserId(userId)
                    .setPickupTime(Timestamp.valueOf(dateTime))
                    .setMenuItems(menuItems)
                    .setTotalPrice(orderTotal);
            Order order = builder.buildOrder();

            result = orderDao.create(order) &&
                    orderDao.createOrderMenuItemMappings(order, menuItemsAsList);
            helper.commit();
        } catch (DaoException e) {
            try {
                helper.rollback();
            } catch (DaoException daoException) {
                logger.error("Failed to undo transaction changes", daoException);
            }
            logger.error("Failed to place order", e);
            throw new ServiceException("Failed to place order", e);
        } finally {
            try {
                helper.endTransaction();
            } catch (DaoException e) {
                logger.error("Failed to end transaction", e);
            }
        }
        return result;
    }

    @Override
    public double calculateOrderTotal(Map<MenuItem, Integer> shoppingCart) throws ServiceException {
        // todo: is this necessary?
        if (shoppingCart == null) {
            logger.error("shopping cart is null");
            throw new ServiceException("shoppingCart is null");
        }

        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : shoppingCart.entrySet()) {
            total += (entry.getValue() * entry.getKey().getPrice());
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(total);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}
