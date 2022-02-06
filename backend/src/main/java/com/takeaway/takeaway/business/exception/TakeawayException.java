package com.takeaway.takeaway.business.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TakeawayException extends Exception {
    private final String details;

    protected TakeawayException(String message, String details) {
        super(message);
        this.details = details;
    }

    protected TakeawayException(String message) {
        this(message, "");
    }

    protected TakeawayException() {
        this("");
    }
}
