package com.takeaway.takeaway.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.UserItemLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.enums.ItemPublishStates;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user-item")
public class UserItemController extends BaseController {

    private final UserItemLogic userItemLogic;

    public UserItemController(UserItemLogic userItemLogic, AuthenticationLogic authenticationLogic) {
        super(authenticationLogic);
        this.userItemLogic = userItemLogic;
    }

    @PostMapping(path = "/createItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UUID createNewItem(@RequestPart MultipartFile[] files, @RequestParam String data) throws TakeawayException, JsonProcessingException {
        UpdateItemDto updateItemDto = new ObjectMapper().readValue(data, UpdateItemDto.class);
        return userItemLogic.createItem(getUserId(), CreateItemDto.fromOutside(updateItemDto, files));
    }

    @PatchMapping(path = "/updateItem/{itemId}")
    public void updateItem(@PathVariable UUID itemId, @RequestBody UpdateItemDto data) throws TakeawayException {
        userItemLogic.updateItem(getUserId(), itemId, UpdateItemDto.fromOutside(data));
    }

    @DeleteMapping(path = "/unPublishItem/{itemId}")
    public void unPublishItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.setItemPublishState(getUserId(), itemId, ItemPublishStates.INACTIVE);
    }

    @PatchMapping(path = "/rePublishItem/{itemId}")
    public void rePublishItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.setItemPublishState(getUserId(), itemId, ItemPublishStates.ACTIVE);
    }

    @PatchMapping(path = "/setItemAsReserved/{itemId}")
    public void setItemAsReserved(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.setItemPublishState(getUserId(), itemId, ItemPublishStates.RESERVED);
    }

    @PatchMapping(path = "/setItemAsSold/{itemId}")
    public void setItemAsSold(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.setItemPublishState(getUserId(), itemId, ItemPublishStates.SOLD);
    }

    @DeleteMapping(path = "/deleteItem/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.deleteItem(getUserId(), itemId);
    }

    @PatchMapping(path = "/changeItemPictureOrder/{itemId}")
    public void changeItemPictureOrder(@PathVariable UUID itemId, @RequestBody ChangeItemPictureOrderDto data) throws TakeawayException {
        userItemLogic.changeItemPictureOrder(getUserId(), itemId, ChangeItemPictureOrderDto.fromOutside(data));
    }

    @PostMapping(path = "/addPictureToItem/{itemId}")
    public void addPictureToItem(@PathVariable UUID itemId, @RequestPart MultipartFile file) throws TakeawayException {
        userItemLogic.addPictureToItem(getUserId(), itemId, CreateAttachmentDto.fromFile(file));
    }

    @DeleteMapping(path = "/deletePictureFromItem/{itemId}/{attachmentId}")
    public void deletePictureFromItem(@PathVariable UUID itemId, @PathVariable UUID attachmentId) throws TakeawayException {
        userItemLogic.deletePictureFromItem(getUserId(), itemId, attachmentId);
    }

    @PostMapping(path = "/reportItem/{itemId}")
    public void reportItem(@PathVariable UUID itemId, ReportItemDto reportItemDto) throws TakeawayException {
        userItemLogic.reportItem(getUserId(), itemId, reportItemDto);
    }

}
