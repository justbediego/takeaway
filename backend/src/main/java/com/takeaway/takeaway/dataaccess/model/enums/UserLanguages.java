package com.takeaway.takeaway.dataaccess.model.enums;

public enum UserLanguages {
    DE("DE"),
    EN("EN");

    private String inText;

    UserLanguages(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
