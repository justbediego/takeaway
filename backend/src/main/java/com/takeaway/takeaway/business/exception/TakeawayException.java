package com.takeaway.takeaway.business.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class TakeawayException extends Exception {
    private final String details;

    protected TakeawayException(String message, String details) {
        super(message);
        this.details = details;
    }
}
