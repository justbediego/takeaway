package com.takeaway.takeaway.dataaccess.model.enums;

public enum AttachmentTypes {
    IMAGE("IMAGE"),
    VIDEO("VIDEO"),
    DOCUMENT("DOCUMENT"),
    AUDIO("AUDIO");

    private String inText;

    AttachmentTypes(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
