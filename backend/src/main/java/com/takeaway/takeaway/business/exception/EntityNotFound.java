package com.takeaway.takeaway.business.exception;

import java.util.UUID;

public class EntityNotFound extends TakeawayException {

    public EntityNotFound(String entityType, UUID id) {
        super(entityType, String.format("id: %s", id));
    }
}
