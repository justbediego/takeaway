package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.UserLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.FileUploadException;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserLogic userLogic;

    private UUID userID = UUID.fromString("19cc7d52-7369-46e9-aae0-60139e5f19dd");

    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PatchMapping(path = "/updateBasicInfo")
    public void updateBasicInfo(@RequestBody UpdateBasicInfoDto data) throws TakeawayException {
        userLogic.updateBasicInfo(userID,
                UpdateBasicInfoDto.builder()
                        .firstName(trim(data.getFirstName()))
                        .lastName(trim(data.getLastName()))
                        .phoneNumber(trim(data.getPhoneNumber()))
                        .phoneNumberCountryCode(trim(data.getPhoneNumberCountryCode()))
                        .build()
        );
    }

    @PatchMapping(path = "/updateUsername")
    public void updateUsername(@RequestBody UpdateUsernameDto data) throws TakeawayException {
        userLogic.updateUsername(userID,
                UpdateUsernameDto.builder()
                        .username(trim(data.getUsername()))
                        .build()
        );
    }

    @PatchMapping(path = "/updateEmail")
    public void updateEmail(@RequestBody UpdateEmailDto data) throws TakeawayException {
        userLogic.updateEmail(userID,
                UpdateEmailDto.builder()
                        .email(trim(data.getEmail()))
                        .build()
        );
    }

    @PatchMapping(path = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto data) throws TakeawayException {
        // no trimming for passwords
        userLogic.changePassword(userID, data);
    }

    @PatchMapping(path = "/modifyAddress")
    public void modifyAddress(@RequestBody ModifyAddressDto data) throws TakeawayException {
        userLogic.modifyAddress(userID,
                ModifyAddressDto.builder()
                        .houseNumber(trim(data.getHouseNumber()))
                        .streetName(trim(data.getStreetName()))
                        .streetName2(trim(data.getStreetName2()))
                        .title(trim(data.getTitle()))
                        .additionalInfo(trim(data.getAdditionalInfo()))
                        .accuracyM(data.getAccuracyM())
                        .cityId(data.getCityId())
                        .countryId(data.getCountryId())
                        .latitude(data.getLatitude())
                        .longitude(data.getLongitude())
                        .stateId(data.getStateId())
                        .build()
        );
    }

    @PatchMapping(path = "/updateProfilePicture")
    public void updateProfilePicture(@RequestPart MultipartFile file) throws TakeawayException {
        CreateAttachmentDto attachmentDto;
        try {
            attachmentDto = CreateAttachmentDto.builder()
                    .fileData(file.getBytes())
                    .filename(trim(file.getOriginalFilename()))
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
    public void authenticateUsername(@RequestBody UsernameAuthenticateDto data) throws TakeawayException {
        userID = userLogic.authenticateByUsername(
                UsernameAuthenticateDto.builder()
                        .username(trim(data.getUsername()))
                        .password(data.getPassword())
                        .build()
        );
    }

    @PostMapping(path = "/authenticateEmail")
    public void authenticateEmail(@RequestBody EmailAuthenticateDto data) throws TakeawayException {
        userID = userLogic.authenticateByEmail(
                EmailAuthenticateDto.builder()
                        .email(trim(data.getEmail()))
                        .password(data.getPassword())
                        .build()
        );
    }

    @GetMapping(path = "/getCountryCodes")
    public GetCountryCodesDto getCountryCodes() {
        return userLogic.getCountryCodes();
    }
}
