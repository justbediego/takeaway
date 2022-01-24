package com.takeaway.takeaway.business.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.util.Strings;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class TakeawayException extends Exception {
    private final String details;

    protected TakeawayException(String message, String details) {
        super(message);
        this.details = details;
    }

    protected TakeawayException(String message) {
        this(message, Strings.EMPTY);
    }

    protected TakeawayException() {
        this(Strings.EMPTY);
    }
}
