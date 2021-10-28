package com.eugene.cafe.model.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParamValidatorTest {

    @Test
    public void nameValidationShouldBeCorrect() {
        String name = "nice name wow";
        boolean actual = ParamValidator.validateName(name);
        assertTrue(actual);
    }

    @Test
    public void priceValidationShouldBeCorrect() {
        String price = "123.777";
        boolean actual = ParamValidator.validatePrice(price);
        assertTrue(actual);
    }

    @Test
    public void descriptionValidationShouldBeCorrect() {
        String description = "heelooo this is text string";
        boolean actual = ParamValidator.validateDescription(description);
        assertTrue(actual);
    }

    @Test
    public void pageNumberValidationShouldBeCorrect() {
        String page = "20";
        boolean actual = ParamValidator.validatePageNumber(page);
        assertTrue(actual);
    }
}
