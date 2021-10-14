package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.MenuItemDao;
import com.eugene.cafe.model.pool.ConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class MenuItemDaoImplTest {

    final ConnectionPool pool = ConnectionPool.getInstance();
    final MenuItemDao menuItemDao = new MenuItemDaoImpl();
    final Connection connection = pool.takeConnection();

    @Before
    public void before() {
        menuItemDao.setConnection(connection);
    }

    @After
    public void after() {
        pool.releaseConnection(connection);
    }

    @Test
    public void getSubsetOfMenuItemsShouldBeCorrect() throws DaoException {
//        List<MenuItem> menuItems = menuItemDao.getSubsetOfMenuItems(4, 0, MenuItemSortOrder.PRICE_DESCENDING);
//        for (MenuItem item : menuItems) {
//            System.out.println(item);
//        }
//        assertTrue(true);
        // todo: implement
    }
}
