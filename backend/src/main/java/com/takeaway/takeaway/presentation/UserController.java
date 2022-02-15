package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.UserLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserLogic userLogic;

    public UserController(UserLogic userLogic, AuthenticationLogic authenticationLogic) {
        super(authenticationLogic);
        this.userLogic = userLogic;
    }

    @PatchMapping(path = "/updateBasicInfo")
    public void updateBasicInfo(@RequestBody UpdateBasicInfoDto data) throws TakeawayException {
        userLogic.updateBasicInfo(getUserId(), UpdateBasicInfoDto.fromOutside(data));
    }

    @PatchMapping(path = "/updateUsername")
    public void updateUsername(@RequestBody UpdateUsernameDto data) throws TakeawayException {
        userLogic.updateUsername(getUserId(), UpdateUsernameDto.fromOutside(data));
    }

    @PatchMapping(path = "/updateEmail")
    public void updateEmail(@RequestBody UpdateEmailDto data) throws TakeawayException {
        userLogic.updateEmail(getUserId(), UpdateEmailDto.fromOutside(data));
    }

    @PatchMapping(path = "/modifyAddress")
    public void modifyAddress(@RequestBody ModifyLocationDto data) throws TakeawayException {
        userLogic.modifyAddress(getUserId(), ModifyLocationDto.fromOutside(data));
    }

    @DeleteMapping(path = "/deleteProfilePicture")
    public void deleteProfilePicture() throws TakeawayException {
        userLogic.deleteProfilePicture(getUserId());
    }

    @PatchMapping(path = "/updateProfilePicture")
    public void updateProfilePicture(@RequestPart MultipartFile file) throws TakeawayException {
        userLogic.updateProfilePicture(getUserId(), CreateAttachmentDto.fromFile(file));
    }

    @GetMapping(path = "/getBasicInfo")
    public GetBasicInfoDto getBasicInfo() throws TakeawayException {
        return userLogic.getBasicInfo(getUserId());
    }
}
