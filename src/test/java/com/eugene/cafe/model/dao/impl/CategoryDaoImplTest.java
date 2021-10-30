package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.CategoryDao;
import com.eugene.cafe.model.dao.MenuItemDao;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class CategoryDaoImplTest {

    static Category category;
    static List<Category> categories;

    @Mock
    CategoryDao categoryDao;

    @BeforeEach
    public void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void init() {
        category = new Category("drinks");
        category.setId(1);
        Category testCategory = new Category("desserts");
        testCategory.setId(2);

        categories = new ArrayList<>();
        categories.add(category);
        categories.add(testCategory);
    }

    @Test
    public void findByIdShouldBeCorrect() throws DaoException {
        Mockito.when(categoryDao.findById(anyInt())).thenReturn(Optional.of(category));
        Optional<Category> actual = categoryDao.findById(4);
        assertEquals(actual, Optional.of(category));
    }

    @Test
    public void createShouldBeCorrect() throws DaoException {
        Mockito.when(categoryDao.create(any())).thenReturn(true);
        assertTrue(categoryDao.create(category));
    }

    @Test
    public void findAllShouldBeCorrect() throws DaoException {
        Mockito.when(categoryDao.findAll()).thenReturn(categories);
        List<Category> actual = categoryDao.findAll();
        assertEquals(actual, categories);
    }

    @Test
    public void updateShouldBeCorrect() throws DaoException {
        Mockito.when(categoryDao.update(any())).thenReturn(Optional.of(category));
        Optional<Category> actual = categoryDao.update(category);
        assertEquals(actual, Optional.of(category));
    }

    @Test
    public void deleteByIdShouldBeCorrect() throws DaoException {
        Mockito.when(categoryDao.deleteById(anyInt())).thenReturn(true);
        assertTrue(categoryDao.deleteById(7));
    }
}
