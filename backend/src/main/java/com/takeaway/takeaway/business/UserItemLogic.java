package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.ChangeItemAttachmentOrderDto;
import com.takeaway.takeaway.business.dto.CreateAttachmentDto;
import com.takeaway.takeaway.business.dto.CreateItemDto;
import com.takeaway.takeaway.business.dto.UpdateItemDto;
import com.takeaway.takeaway.business.exception.TakeawayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class UserItemLogic {

    public UUID createNewItem(UUID userId, CreateItemDto data) throws TakeawayException {
        return null;
    }

    public void updateItemDetails(UUID userId, UpdateItemDto data) throws TakeawayException {

    }

    public void deactivateItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void renewItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void deleteItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void changeItemAttachmentOrder(UUID userId, ChangeItemAttachmentOrderDto data) throws TakeawayException {

    }

    public void addAttachmentToItem(UUID userId, CreateAttachmentDto attachmentDto) throws TakeawayException {

    }

    public void deleteAttachmentFromItem(UUID userId, UUID itemId, UUID attachmentId) throws TakeawayException {

    }
}
