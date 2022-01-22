package com.takeaway.takeaway.business.exception;

import java.util.UUID;

public class EntityNotFound extends TakeawayException {

    public EntityNotFound(UUID id) {
        super("Entity was not found", String.format("id: %s", id));
    }
}
