package com.takeaway.takeaway.dataaccess.model.enums;

public enum ItemPublishStates {
    ACTIVE("ACTIVE"),
    RESERVED("RESERVED"),
    SOLD("SOLD"),
    INACTIVE("INACTIVE");

    private String inText;

    ItemPublishStates(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
