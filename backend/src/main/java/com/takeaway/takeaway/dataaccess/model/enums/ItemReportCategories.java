package com.takeaway.takeaway.dataaccess.model.enums;

public enum ItemReportCategories {

    // Spam
    SPAM("SPAM"),

    // Nudity or sexual activity
    NUDITY("NUDITY"),

    // Hate speech or symbols
    HATE("HATE"),

    // Violence or dangerous organizations
    VIOLENCE("VIOLENCE"),

    // Bullying or harassment
    BULLYING("BULLYING"),

    // Selling illegal or regulated goods
    ILLEGAL("ILLEGAL"),

    // Intellectual property violations
    PROPERTY_VIOLATION("PROPERTY_VIOLATION"),

    // Suicide or self-injury
    SUICIDE("SUICIDE"),

    // Eating disorders
    EATING_DISORDER("EATING_DISORDER"),

    // Scams or fraud
    SCAM("SCAM"),

    // False information
    FALSE_INFORMATION("FALSE_INFORMATION"),

    // Others
    OTHERS("OTHERS");

    private String inText;

    ItemReportCategories(String inText) {
        this.inText = inText;
    }

    @Override
    public String toString() {
        return inText;
    }
}
