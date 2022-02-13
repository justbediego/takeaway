package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.UserLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserLogic userLogic;

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
}
