package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.OrderDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class OrderDaoImplTest {

    static Order order;
    static List<Order> orders;
    static List<MenuItem> menuItems;

    @Mock
    OrderDao orderDao;

    @BeforeEach
    public void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void init() {
        Order.Builder builder = new Order.Builder();
        order = builder.buildOrder();

        orders = new ArrayList<>();
        orders.add(builder.buildOrder());
        orders.add(builder.buildOrder());

        MenuItem.Builder menuBuilder = new MenuItem.Builder();
        menuItems = new ArrayList<>();
        menuItems.add(menuBuilder.buildMenuItem());
        menuItems.add(menuBuilder.buildMenuItem());
    }

    @Test
    public void findByIdShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.findById(anyInt())).thenReturn(Optional.of(order));
        Optional<Order> actual = orderDao.findById(4);
        assertEquals(actual, Optional.of(order));
    }

    @Test
    public void createShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.create(any())).thenReturn(true);
        assertTrue(orderDao.create(order));
    }

    @Test
    public void findAllShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.findAll()).thenReturn(orders);
        List<Order> actual = orderDao.findAll();
        assertEquals(actual, orders);
    }

    @Test
    public void updateShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.update(any())).thenReturn(Optional.of(order));
        Optional<Order> actual = orderDao.update(order);
        assertEquals(actual, Optional.of(order));
    }

    @Test
    public void deleteByIdShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.deleteById(anyInt())).thenReturn(true);
        assertTrue(orderDao.deleteById(7));
    }

    @Test
    public void createOrderMenuItemMappingsShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.createOrderMenuItemMappings(any(Order.class), anyList())).thenReturn(true);
        assertTrue(orderDao.createOrderMenuItemMappings(order, menuItems));
    }

    @Test
    public void findMenuItemsByOrderIdShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.findMenuItemsByOrderId(anyInt())).thenReturn(menuItems);
        List<MenuItem> actual = orderDao.findMenuItemsByOrderId(10);
        assertEquals(actual, menuItems);
    }

    @Test
    public void getSubsetOfUserOrdersShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.getSubsetOfUserOrders(anyInt(), anyInt(), anyInt())).thenReturn(orders);
        List<Order> actual = orderDao.getSubsetOfUserOrders(1, 4, 10);
        assertEquals(actual, orders);
    }

    @Test
    public void getSubsetOfOrdersShouldBeCorrect() throws DaoException {
        Mockito.when(orderDao.getSubsetOfOrders(anyInt(), anyInt())).thenReturn(orders);
        List<Order> actual = orderDao.getSubsetOfOrders(10, 4);
        assertEquals(actual, orders);
    }

    @Test
    public void getUserOrderCountBeCorrect() throws DaoException {
        Mockito.when(orderDao.getUserOrderCount(anyInt())).thenReturn(17);
        int actual = orderDao.getUserOrderCount(1);
        assertEquals(actual, 17);
    }

    @Test
    public void getOrderCountBeCorrect() throws DaoException {
        Mockito.when(orderDao.getOrderCount()).thenReturn(90);
        int actual = orderDao.getOrderCount();
        assertEquals(actual, 90);
    }
}
