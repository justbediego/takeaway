package com.takeaway.takeaway.business.exception;

public class UnrecognizedException extends TakeawayException {

    public UnrecognizedException(String details) {
        super("An unknown or unrecognized exception occurred", details);
    }
}