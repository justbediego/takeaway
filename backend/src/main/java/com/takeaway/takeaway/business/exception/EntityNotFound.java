package com.takeaway.takeaway.business.exception;

import com.takeaway.takeaway.dataaccess.model.enums.EntityTypes;

import java.util.UUID;

public class EntityNotFound extends TakeawayException {

    public EntityNotFound(EntityTypes entityType, UUID id) {
        super(entityType.toString(), String.format("id: %s", id));
    }
}
