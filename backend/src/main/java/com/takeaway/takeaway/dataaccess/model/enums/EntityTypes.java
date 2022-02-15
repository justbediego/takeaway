package com.takeaway.takeaway.dataaccess.model.enums;

public enum EntityTypes {
    ATTACHMENT("ATTACHMENT"),
    USER("USER"),
    COUNTRY("COUNTRY"),
    STATE("STATE"),
    CITY("CITY"),
    LOCATION("LOCATION"),
    GEOLOCATION("GEOLOCATION"),
    ITEM_CATEGORY("ITEM_CATEGORY");

    private String inText;

    EntityTypes(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
