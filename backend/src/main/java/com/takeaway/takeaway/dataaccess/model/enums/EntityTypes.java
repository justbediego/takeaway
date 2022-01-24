package com.takeaway.takeaway.dataaccess.model.enums;

public enum EntityTypes {
    ATTACHMENT("ATTACHMENT"),
    USER("USER");

    private String inText;

    EntityTypes(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
