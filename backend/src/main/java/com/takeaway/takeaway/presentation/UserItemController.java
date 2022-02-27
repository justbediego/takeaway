package com.takeaway.takeaway.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.UserItemLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
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
        userItemLogic.unPublishItem(getUserId(), itemId);
    }

    @PostMapping(path = "/rePublishItem/{itemId}")
    public void rePublishItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.rePublishItem(getUserId(), itemId);
    }

    @DeleteMapping(path = "/deleteItem/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.deleteItem(getUserId(), itemId);
    }

    @PatchMapping(path = "/changeItemAttachmentOrder/{itemId}")
    public void changeItemAttachmentOrder(@PathVariable UUID itemId, @RequestBody ChangeItemAttachmentOrderDto data) throws TakeawayException {
        userItemLogic.changeItemAttachmentOrder(getUserId(), itemId, ChangeItemAttachmentOrderDto.fromOutside(data));
    }

    @PostMapping(path = "/addAttachmentToItem/{itemId}")
    public void addAttachmentToItem(@PathVariable UUID itemId, @RequestPart MultipartFile file) throws TakeawayException {
        userItemLogic.addAttachmentToItem(getUserId(), itemId, CreateAttachmentDto.fromFile(file));
    }

    @DeleteMapping(path = "/deleteAttachmentFromItem/{itemId}/{attachmentId}")
    public void deleteAttachmentFromItem(@PathVariable UUID itemId, @PathVariable UUID attachmentId) throws TakeawayException {
        userItemLogic.deleteAttachmentFromItem(getUserId(), itemId, attachmentId);
    }

    @PostMapping(path = "/reportItem/{itemId}")
    public void reportItem(@PathVariable UUID itemId, ReportItemDto reportItemDto) throws TakeawayException {
        userItemLogic.reportItem(getUserId(), itemId, reportItemDto);
    }

}
