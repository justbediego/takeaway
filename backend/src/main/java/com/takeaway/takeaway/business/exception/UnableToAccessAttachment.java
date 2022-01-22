package com.takeaway.takeaway.business.exception;

import java.util.UUID;

public class UnableToAccessAttachment extends TakeawayException {

    public UnableToAccessAttachment(UUID attachmentId) {
        super("Was unable to access attachment", String.format("id: %s", attachmentId));
    }
}
