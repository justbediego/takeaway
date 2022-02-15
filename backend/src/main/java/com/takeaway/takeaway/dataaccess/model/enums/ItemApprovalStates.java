package com.takeaway.takeaway.dataaccess.model.enums;

public enum ItemApprovalStates {
    QUEUE("QUEUE"),
    APPROVED("APPROVED"),
    BLOCKED("BLOCKED");

    private String inText;

    ItemApprovalStates(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
