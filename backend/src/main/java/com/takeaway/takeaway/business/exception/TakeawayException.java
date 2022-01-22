package com.takeaway.takeaway.business.exception;

import lombok.Data;

@Data
public abstract class TakeawayException extends Exception {
    private final String details;

    protected TakeawayException(String message, String details) {
        super(message);
        this.details = details;
    }
}
