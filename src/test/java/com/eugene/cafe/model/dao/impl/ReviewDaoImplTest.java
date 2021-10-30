package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.Review;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.ReviewDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class ReviewDaoImplTest {

    static Review review;
    static List<Review> reviews;

    @Mock
    ReviewDao reviewDao;

    @BeforeEach
    public void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void init() {
        Review.Builder builder = new Review.Builder();
        review = builder.buildReview();

        reviews = new ArrayList<>();
        reviews.add(builder.buildReview());
        reviews.add(builder.buildReview());
    }

    @Test
    public void findByIdShouldBeCorrect() throws DaoException {
        Mockito.when(reviewDao.findById(anyInt())).thenReturn(Optional.of(review));
        Optional<Review> actual = reviewDao.findById(4);
        assertEquals(actual, Optional.of(review));
    }

    @Test
    public void createShouldBeCorrect() throws DaoException {
        Mockito.when(reviewDao.create(any())).thenReturn(true);
        assertTrue(reviewDao.create(review));
    }

    @Test
    public void findAllShouldBeCorrect() throws DaoException {
        Mockito.when(reviewDao.findAll()).thenReturn(reviews);
        List<Review> actual = reviewDao.findAll();
        assertEquals(actual, reviews);
    }

    @Test
    public void updateShouldBeCorrect() throws DaoException {
        Mockito.when(reviewDao.update(any())).thenReturn(Optional.of(review));
        Optional<Review> actual = reviewDao.update(review);
        assertEquals(actual, Optional.of(review));
    }

    @Test
    public void deleteByIdShouldBeCorrect() throws DaoException {
        Mockito.when(reviewDao.deleteById(anyInt())).thenReturn(true);
        assertTrue(reviewDao.deleteById(2));
    }

}
