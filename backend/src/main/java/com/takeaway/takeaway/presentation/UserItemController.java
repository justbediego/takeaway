package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.UserItemLogic;
import com.takeaway.takeaway.business.dto.ChangeItemAttachmentOrderDto;
import com.takeaway.takeaway.business.dto.CreateAttachmentDto;
import com.takeaway.takeaway.business.dto.CreateItemDto;
import com.takeaway.takeaway.business.dto.UpdateItemDto;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/userItem")
public class UserItemController {

    private final UserItemLogic userItemLogic;

    private UUID userID = UUID.fromString("19cc7d52-7369-46e9-aae0-60139e5f19dd");

    public UserItemController(UserItemLogic userItemLogic) {
        this.userItemLogic = userItemLogic;
    }

    @PostMapping(path = "/createNewItem")
    public UUID createNewItem(@RequestBody UpdateItemDto data, @RequestPart MultipartFile[] files) throws TakeawayException {
        return userItemLogic.createNewItem(userID, CreateItemDto.fromOutside(data, files));
    }

    @PatchMapping(path = "/updateItemDetails")
    public void updateItemDetails(@RequestBody UpdateItemDto data) throws TakeawayException {
        userItemLogic.updateItemDetails(userID, UpdateItemDto.fromOutside(data));
    }

    @DeleteMapping(path = "/deactivateItem/{itemId}")
    public void deactivateItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.deactivateItem(userID, itemId);
    }

    @PostMapping(path = "/renewItem/{itemId}")
    public void renewItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.renewItem(userID, itemId);
    }

    @DeleteMapping(path = "/deleteItem/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.deleteItem(userID, itemId);
    }

    @PatchMapping(path = "/changeItemAttachmentOrder")
    public void changeItemAttachmentOrder(@RequestBody ChangeItemAttachmentOrderDto data) throws TakeawayException {
        userItemLogic.changeItemAttachmentOrder(userID, ChangeItemAttachmentOrderDto.fromOutside(data));
    }

    @PostMapping(path = "/addAttachmentToItem")
    public void addAttachmentToItem(@RequestPart MultipartFile file) throws TakeawayException {
        userItemLogic.addAttachmentToItem(userID, CreateAttachmentDto.fromFile(file));
    }

    @DeleteMapping(path = "/deleteAttachmentFromItem/{itemId}/{attachmentId}")
    public void deleteAttachmentFromItem(@PathVariable UUID itemId, @PathVariable UUID attachmentId) throws TakeawayException {
        userItemLogic.deleteAttachmentFromItem(userID, itemId, attachmentId);
    }
}
