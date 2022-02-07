package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.UserLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserLogic userLogic;

    private UUID userID = UUID.fromString("19cc7d52-7369-46e9-aae0-60139e5f19dd");

    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PatchMapping(path = "/updateBasicInfo")
    public void updateBasicInfo(@RequestBody UpdateBasicInfoDto data) throws TakeawayException {
        userLogic.updateBasicInfo(userID, UpdateBasicInfoDto.fromOutside(data));
    }

    @PatchMapping(path = "/updateUsername")
    public void updateUsername(@RequestBody UpdateUsernameDto data) throws TakeawayException {
        userLogic.updateUsername(userID, UpdateUsernameDto.fromOutside(data));
    }

    @PatchMapping(path = "/updateEmail")
    public void updateEmail(@RequestBody UpdateEmailDto data) throws TakeawayException {
        userLogic.updateEmail(userID, UpdateEmailDto.fromOutside(data));
    }

    @PatchMapping(path = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto data) throws TakeawayException {
        userLogic.changePassword(userID, ChangePasswordDto.fromOutside(data));
    }

    @PatchMapping(path = "/modifyAddress")
    public void modifyAddress(@RequestBody ModifyAddressDto data) throws TakeawayException {
        userLogic.modifyAddress(userID, ModifyAddressDto.fromOutside(data));
    }

    @DeleteMapping(path = "/deleteProfilePicture")
    public void deleteProfilePicture() throws TakeawayException {
        userLogic.deleteProfilePicture(userID);
    }

    @PatchMapping(path = "/updateProfilePicture")
    public void updateProfilePicture(@RequestPart MultipartFile file) throws TakeawayException {
        userLogic.updateProfilePicture(userID, CreateAttachmentDto.fromFile(file));
    }

    @GetMapping(path = "/getBasicInfo")
    public GetBasicInfoDto getBasicInfo() throws TakeawayException {
        return userLogic.getBasicInfo(userID);
    }

    @PostMapping(path = "/authenticateUsername")
    public void authenticateUsername(@RequestBody UsernameAuthenticateDto data) throws TakeawayException {
        userID = userLogic.authenticateByUsername(UsernameAuthenticateDto.fromOutside(data));
    }

    @PostMapping(path = "/authenticateEmail")
    public void authenticateEmail(@RequestBody EmailAuthenticateDto data) throws TakeawayException {
        userID = userLogic.authenticateByEmail(EmailAuthenticateDto.fromOutside(data));
    }

    @PostMapping(path = "/createNewItem")
    public UUID createNewItem(@RequestBody UpdateItemDto data, @RequestPart MultipartFile[] files) throws TakeawayException {
        return userLogic.createNewItem(userID, CreateItemDto.fromOutside(data, files));
    }

    @PatchMapping(path = "/updateItemDetails")
    public void updateItemDetails(@RequestBody UpdateItemDto data) throws TakeawayException {
        userLogic.updateItemDetails(userID, UpdateItemDto.fromOutside(data));
    }

    @DeleteMapping(path = "/deactivateItem/{itemId}")
    public void deactivateItem(@PathVariable UUID itemId) throws TakeawayException {
        userLogic.deactivateItem(userID, itemId);
    }

    @PostMapping(path = "/renewItem/{itemId}")
    public void renewItem(@PathVariable UUID itemId) throws TakeawayException {
        userLogic.renewItem(userID, itemId);
    }

    @DeleteMapping(path = "/deleteItem/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) throws TakeawayException {
        userLogic.deleteItem(userID, itemId);
    }

    @PatchMapping(path = "/changeItemAttachmentOrder")
    public void changeItemAttachmentOrder(@RequestBody ChangeItemAttachmentOrderDto data) throws TakeawayException {
        userLogic.changeItemAttachmentOrder(userID, ChangeItemAttachmentOrderDto.fromOutside(data));
    }

    @PostMapping(path = "/addAttachmentToItem")
    public void addAttachmentToItem(@RequestPart MultipartFile file) throws TakeawayException {
        userLogic.addAttachmentToItem(userID, CreateAttachmentDto.fromFile(file));
    }

    @DeleteMapping(path = "/deleteAttachmentFromItem/{itemId}/{attachmentId}")
    public void deleteAttachmentFromItem(@PathVariable UUID itemId, @PathVariable UUID attachmentId) throws TakeawayException {
        userLogic.deleteAttachmentFromItem(userID, itemId, attachmentId);
    }
}
