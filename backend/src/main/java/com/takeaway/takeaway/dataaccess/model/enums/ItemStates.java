package com.takeaway.takeaway.dataaccess.model.enums;

public enum ItemStates {
    EDIT("EDIT"),
    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED");

    private String inText;

    ItemStates(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
