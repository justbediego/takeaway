package com.takeaway.takeaway.business.exception;

public enum ExceptionEntities {
    ATTACHMENT("ATTACHMENT"),
    USER("USER"),
    COUNTRY("COUNTRY"),
    STATE("STATE"),
    CITY("CITY"),
    LOCATION("LOCATION"),
    GEOLOCATION("GEOLOCATION"),
    ITEM_CATEGORY("ITEM_CATEGORY"),
    ITEM("ITEM");

    private String inText;

    ExceptionEntities(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}