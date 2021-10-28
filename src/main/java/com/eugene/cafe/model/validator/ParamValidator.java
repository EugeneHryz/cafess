package com.eugene.cafe.model.validator;

public class ParamValidator {

    private static final String MENU_ITEM_NAME_REGEX = "^[\\p{L} ]{4,30}$";

    private static final String MENU_ITEM_PRICE_REGEX = "^[0-9]+[.,][0-9]+$";

    private static final String MENU_ITEM_DESCRIPTION_REGEX = "^[^<>]{5,300}$";

    private static final String PAGE_NUMBER_REGEX = "^[0-9]+$";

    private static final String COMMENT_REGEX = "^[^<>]{5,}$";

    public static boolean validateMenuItem(String name, String price, String description) {
        return validateName(name) && validatePrice(price) && validateDescription(description);
    }

    public static boolean validateName(String name) {
        return name.matches(MENU_ITEM_NAME_REGEX);
    }

    public static boolean validatePrice(String price) {
        return price.matches(MENU_ITEM_PRICE_REGEX);
    }

    public static boolean validateDescription(String description) {
        return description.matches(MENU_ITEM_DESCRIPTION_REGEX);
    }

    public static boolean validatePageNumber(String pageNumber) {
        return pageNumber.matches(PAGE_NUMBER_REGEX);
    }

    public static boolean validateComment(String comment) {
        return comment.matches(COMMENT_REGEX);
    }
}
