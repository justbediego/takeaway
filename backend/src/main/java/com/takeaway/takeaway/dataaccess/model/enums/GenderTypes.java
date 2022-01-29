package com.takeaway.takeaway.dataaccess.model.enums;

public enum GenderTypes {
    MAN("MAN"),
    WOMAN("WOMAN"),
    NOT_SAY("NOT_SAY");

    private String inText;

    GenderTypes(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
