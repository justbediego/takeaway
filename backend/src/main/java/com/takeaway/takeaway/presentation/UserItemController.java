package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.UserItemLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
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

    @PostMapping(path = "/createNewItem")
    public UUID createNewItem(@RequestBody UpdateItemDto data, @RequestPart MultipartFile[] files) throws TakeawayException {
        return userItemLogic.createNewItem(getUserId(), CreateItemDto.fromOutside(data, files));
    }

    @PatchMapping(path = "/updateItemDetails")
    public void updateItemDetails(@RequestBody UpdateItemDto data) throws TakeawayException {
        userItemLogic.updateItemDetails(getUserId(), UpdateItemDto.fromOutside(data));
    }

    @DeleteMapping(path = "/deactivateItem/{itemId}")
    public void deactivateItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.deactivateItem(getUserId(), itemId);
    }

    @PostMapping(path = "/renewItem/{itemId}")
    public void renewItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.renewItem(getUserId(), itemId);
    }

    @DeleteMapping(path = "/deleteItem/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) throws TakeawayException {
        userItemLogic.deleteItem(getUserId(), itemId);
    }

    @PatchMapping(path = "/changeItemAttachmentOrder")
    public void changeItemAttachmentOrder(@RequestBody ChangeItemAttachmentOrderDto data) throws TakeawayException {
        userItemLogic.changeItemAttachmentOrder(getUserId(), ChangeItemAttachmentOrderDto.fromOutside(data));
    }

    @PostMapping(path = "/addAttachmentToItem")
    public void addAttachmentToItem(@RequestPart MultipartFile file) throws TakeawayException {
        userItemLogic.addAttachmentToItem(getUserId(), CreateAttachmentDto.fromFile(file));
    }

    @DeleteMapping(path = "/deleteAttachmentFromItem/{itemId}/{attachmentId}")
    public void deleteAttachmentFromItem(@PathVariable UUID itemId, @PathVariable UUID attachmentId) throws TakeawayException {
        userItemLogic.deleteAttachmentFromItem(getUserId(), itemId, attachmentId);
    }

    @PostMapping(path = "/reportItem")
    public void reportItem(ReportItemDto reportItemDto) throws TakeawayException{
        userItemLogic.reportItem(getUserId(), reportItemDto);
    }

}
