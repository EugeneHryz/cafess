package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.MenuItemDao;
import com.eugene.cafe.model.dao.MenuSortOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemDaoImplTest {

    static MenuItem menuItem;
    static List<MenuItem> menuItems;

    @Mock
    MenuItemDao menuItemDao;

    @BeforeEach
    public void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void init() {
        MenuItem.Builder builder = new MenuItem.Builder();
        menuItem = builder.buildMenuItem();

        menuItems = new ArrayList<>();
        menuItems.add(builder.buildMenuItem());
        menuItems.add(builder.buildMenuItem());
    }

    @Test
    public void findByIdShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.findById(anyInt())).thenReturn(Optional.of(menuItem));
        Optional<MenuItem> actual = menuItemDao.findById(4);
        assertEquals(actual, Optional.of(menuItem));
    }

    @Test
    public void createShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.create(any())).thenReturn(true);
        assertTrue(menuItemDao.create(menuItem));
    }

    @Test
    public void findAllShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.findAll()).thenReturn(menuItems);
        List<MenuItem> actual = menuItemDao.findAll();
        assertEquals(actual, menuItems);
    }

    @Test
    public void updateShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.update(any())).thenReturn(Optional.of(menuItem));
        Optional<MenuItem> actual = menuItemDao.update(menuItem);
        assertEquals(actual, Optional.of(menuItem));
    }

    @Test
    public void deleteByIdShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.deleteById(anyInt())).thenReturn(true);
        assertTrue(menuItemDao.deleteById(3));
    }

    @Test
    public void getSubsetOfMenuItemsShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.getSubsetOfMenuItems(anyInt(), anyInt(), any(MenuSortOrder.class), anyBoolean())).thenReturn(menuItems);
        List<MenuItem> actual = menuItemDao.getSubsetOfMenuItems(4, 4, MenuSortOrder.PRICE_ASCENDING, false);
        assertEquals(actual, menuItems);
    }

    @Test
    public void getSubsetActiveByCategoryShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.getSubsetOfActiveMenuItemsByCategory(anyInt(), anyInt(), any(MenuSortOrder.class), any(Category.class))).thenReturn(menuItems);
        List<MenuItem> actual = menuItemDao.getSubsetOfActiveMenuItemsByCategory(4, 4, MenuSortOrder.PRICE_ASCENDING, new Category("drinks"));
        assertEquals(actual, menuItems);
    }

    @Test
    public void getCountShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.getCount(anyBoolean())).thenReturn(4);
        int actual = menuItemDao.getCount(true);
        assertEquals(actual, 4);
    }

    @Test
    public void getCountByCategoryShouldBeCorrect() throws DaoException {
        Mockito.when(menuItemDao.getCountByCategory(any(Category.class), anyBoolean())).thenReturn(6);
        int actual = menuItemDao.getCountByCategory(new Category("salads"),true);
        assertEquals(actual, 6);
    }
}
