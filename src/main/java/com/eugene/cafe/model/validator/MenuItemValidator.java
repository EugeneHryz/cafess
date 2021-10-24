package com.eugene.cafe.model.validator;

public class MenuItemValidator {

    private static final String NAME_REGEX = "^[\\p{L} ]{4,30}$";

    private static final String PRICE_REGEX = "^[0-9]+[.,][0-9]+$";

    private static final String DESCRIPTION_REGEX = "^.{5,300}$";

    public static boolean validateName(String name) {
        return name.matches(NAME_REGEX);
    }

    public static boolean validatePrice(String price) {
        return price.matches(PRICE_REGEX);
    }

    public static boolean validateDescription(String description) {
        return description.matches(DESCRIPTION_REGEX);
    }
}
