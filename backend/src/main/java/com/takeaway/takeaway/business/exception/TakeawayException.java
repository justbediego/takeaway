package com.takeaway.takeaway.business.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeawayException extends Exception {
    private final String details;
    private final ExceptionTypes exceptionType;
    private final ExceptionEntities entity;

    public TakeawayException(ExceptionTypes exceptionType, ExceptionEntities entity, String message, String details) {
        super(message);
        this.details = details;
        this.exceptionType = exceptionType;
        this.entity = entity;
    }

    public TakeawayException(ExceptionTypes exceptionType, ExceptionEntities entity, String message) {
        this(exceptionType, entity, message, null);
    }

    public TakeawayException(ExceptionTypes exceptionType, ExceptionEntities entity) {
        this(exceptionType, entity, null);
    }

    public TakeawayException(ExceptionTypes exceptionType) {
        this(exceptionType, null);
    }
}
