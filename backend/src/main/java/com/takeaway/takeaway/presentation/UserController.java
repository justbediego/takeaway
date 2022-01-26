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

    private UUID userID = null;

    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PatchMapping(path = "/updateBasicInfo")
    public void updateBasicInfo(@RequestBody UpdateBasicInfoDto updateBasicInfoDto) throws TakeawayException {
        userLogic.updateBasicInfo(userID, updateBasicInfoDto);
    }

    @PatchMapping(path = "/updateUsername")
    public void updateUsername(@RequestBody UpdateUsernameDto updateUsernameDto) throws TakeawayException {
        userLogic.updateUsername(userID, updateUsernameDto);
    }

    @PatchMapping(path = "/updateEmail")
    public void updateEmail(@RequestBody UpdateEmailDto updateEmailDto) throws TakeawayException {
        userLogic.updateEmail(userID, updateEmailDto);
    }

    @PatchMapping(path = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws TakeawayException {
        userLogic.changePassword(userID, changePasswordDto);
    }

    @PatchMapping(path = "/modifyAddress")
    public void modifyAddress(@RequestBody ModifyAddressDto modifyAddressDto) throws TakeawayException {
        userLogic.modifyAddress(userID, modifyAddressDto);
    }

    @PatchMapping(path = "/updateProfilePicture")
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

    @PostMapping(path = "/authenticateUsername")
    public void authenticateUsername(@RequestBody UsernameAuthenticateDto authenticateDto) throws TakeawayException {
        userID = userLogic.authenticateByUsername(authenticateDto);
    }

    @PostMapping(path = "/authenticateEmail")
    public void authenticateEmail(@RequestBody EmailAuthenticateDto authenticateDto) throws TakeawayException {
        userID = userLogic.authenticateByEmail(authenticateDto);
    }

}
