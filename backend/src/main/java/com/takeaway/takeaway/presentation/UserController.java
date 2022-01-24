package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.UserLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.FileUploadException;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserLogic userLogic;

    private final UUID userID = null; // UUID.fromString("60ff024c-421a-404d-bf14-60bee1e1fd59")

    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PutMapping(path = "/updateBasicInfo")
    public void updateBasicInfo(@RequestBody UpdateBasicInfoDto updateBasicInfoDto) throws TakeawayException {
        userLogic.updateBasicInfo(userID, updateBasicInfoDto);
    }

    @PutMapping(path = "/updateUsername")
    public void updateUsername(@RequestBody UpdateUsernameDto updateUsernameDto) throws TakeawayException {
        userLogic.updateUsername(userID, updateUsernameDto);
    }

    @PutMapping(path = "/updateEmail")
    public void updateEmail(@RequestBody UpdateEmailDto updateEmailDto) throws TakeawayException {
        userLogic.updateEmail(userID, updateEmailDto);
    }

    @PutMapping(path = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws TakeawayException {
        userLogic.changePassword(userID, changePasswordDto);
    }

    @PutMapping(path = "/modifyAddress")
    public void modifyAddress(@RequestBody ModifyAddressDto modifyAddressDto) throws TakeawayException {
        userLogic.modifyAddress(userID, modifyAddressDto);
    }

    @PutMapping(path = "/updateProfilePicture")
    public void updateProfilePicture(@RequestPart MultipartFile file) throws TakeawayException {
        CreateAttachmentDto attachmentDto;
        try {
            attachmentDto = CreateAttachmentDto.builder()
                    .fileData(file.getBytes())
                    .filename(file.getOriginalFilename())
                    .build();
        } catch (IOException ioException) {
            throw new FileUploadException();
        }
        userLogic.updateProfilePicture(userID, attachmentDto);
    }

    @GetMapping(path = "/getBasicInfo")
    public GetBasicInfoDto getBasicInfo() throws TakeawayException {
        return userLogic.getBasicInfo(userID);
    }

}
