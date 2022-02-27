package com.takeaway.takeaway.business.exception;

public enum ExceptionTypes {
    CITY_NOT_SELECTED("CITY_NOT_SELECTED"),
    COUNTRY_NOT_SELECTED("COUNTRY_NOT_SELECTED"),
    EMAIL_ALREADY_IN_USE("EMAIL_ALREADY_IN_USE"),
    EMPTY_FILE_UPLOADED("EMPTY_FILE_UPLOADED"),
    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND"),
    FILE_SIZE_EXCEEDED("FILE_SIZE_EXCEEDED"),
    FILE_UPLOAD("FILE_UPLOAD"),
    INVALID_ADDRESS("INVALID_ADDRESS"),
    INVALID_COUNTRY_CODE("INVALID_COUNTRY_CODE"),
    INVALID_EMAIL("INVALID_EMAIL"),
    INVALID_FILENAME("INVALID_FILENAME"),
    INVALID_FIRST_NAME("INVALID_FIRST_NAME"),
    INVALID_GEOLOCATION_VALUES("INVALID_GEOLOCATION_VALUES"),
    INVALID_LAST_NAME("INVALID_LAST_NAME"),
    INVALID_LOCATION_TITLE("INVALID_LOCATION_TITLE"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    INVALID_PHONE_NUMBER("INVALID_PHONE_NUMBER"),
    INVALID_USERNAME("INVALID_USERNAME"),
    ITEM_WITHOUT_LOCATION("ITEM_WITHOUT_LOCATION"),
    NEW_EMAIL_SAME_AS_OLD("NEW_EMAIL_SAME_AS_OLD"),
    NEW_USERNAME_SAME_AS_OLD("NEW_USERNAME_SAME_AS_OLD"),
    STATE_NOT_SELECTED("STATE_NOT_SELECTED"),
    UNABLE_TO_PARSE_IMAGE("UNABLE_TO_PARSE_IMAGE"),
    UNRECOGNIZED("UNRECOGNIZED"),
    USERNAME_ALREADY_IN_USE("USERNAME_ALREADY_IN_USE"),
    USER_OR_PASSWORD_WRONG("USER_OR_PASSWORD_WRONG"),
    VERIFY_PASSWORD("VERIFY_PASSWORD"),
    WRONG_PASSWORD("WRONG_PASSWORD"),
    INVALID_ITEM_TITLE("INVALID_ITEM_TITLE"),
    INVALID_ITEM_DESCRIPTION("INVALID_ITEM_DESCRIPTION"),
    ITEM_CATEGORY_HAS_CHILDREN("ITEM_CATEGORY_HAS_CHILDREN"),
    ITEM_IS_BLOCKED("ITEM_IS_BLOCKED"),
    ITEM_IS_UNPUBLISHED("ITEM_IS_UNPUBLISHED"),
    INVALID_IDS_TO_REORDER("INVALID_IDS_TO_REORDER"),
    INVALID_REPORT_DESCRIPTION("INVALID_REPORT_DESCRIPTION"),
    INVALID_REPORT_CATEGORY("INVALID_REPORT_CATEGORY"),
    ITEM_ALREADY_REPORTED("ITEM_ALREADY_REPORTED");

    private String inText;

    ExceptionTypes(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}