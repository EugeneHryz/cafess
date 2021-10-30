package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.UserDao;
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

public class UserDaoImplTest {

    static User user;
    static List<User> users;

    @Mock
    UserDao userDao;

    @BeforeEach
    public void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void init() {
        User.Builder builder = new User.Builder();
        user = builder.buildUser();

        users = new ArrayList<>();
        users.add(builder.buildUser());
        users.add(builder.buildUser());
    }

    @Test
    public void findByIdShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.findById(anyInt())).thenReturn(Optional.of(user));
        Optional<User> actual = userDao.findById(4);
        assertEquals(actual, Optional.of(user));
    }

    @Test
    public void createShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.create(any())).thenReturn(true);
        assertTrue(userDao.create(user));
    }

    @Test
    public void findAllShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.findAll()).thenReturn(users);
        List<User> actual = userDao.findAll();
        assertEquals(actual, users);
    }

    @Test
    public void updateShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.update(any())).thenReturn(Optional.of(user));
        Optional<User> actual = userDao.update(user);
        assertEquals(actual, Optional.of(user));
    }

    @Test
    public void deleteByIdShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.deleteById(anyInt())).thenReturn(true);
        assertTrue(userDao.deleteById(12));
    }

    @Test
    public void updateProfilePictureShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.updateProfilePicture(anyInt(), anyString())).thenReturn(Optional.of(user));
        Optional<User> actual = userDao.updateProfilePicture(4, "qaqaff.jpg");
        assertEquals(actual, Optional.of(user));
    }

    @Test
    public void findByEmailShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.findByEmail(anyString())).thenReturn(Optional.of(user));
        Optional<User> actual = userDao.findByEmail("acx@qqq");
        assertEquals(actual, Optional.of(user));
    }

    @Test
    public void changePasswordShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.changePassword(anyInt(), anyString())).thenReturn(true);
        assertTrue(userDao.changePassword(4, "ahhfdgbfd"));
    }

    @Test
    public void getSubsetOfUsersShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.getSubsetOfUsers(anyInt(), anyInt())).thenReturn(users);
        List<User> actual = userDao.getSubsetOfUsers(20, 10);
        assertEquals(actual, users);
    }

    @Test
    public void getCountShouldBeCorrect() throws DaoException {
        Mockito.when(userDao.getCount()).thenReturn(36);
        int actual = userDao.getCount();
        assertEquals(actual, 36);
    }
}
