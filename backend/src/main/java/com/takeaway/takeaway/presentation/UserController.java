package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.UserLogic;
import com.takeaway.takeaway.business.dto.CreateAttachmentDto;
import com.takeaway.takeaway.business.dto.GetBasicInfoDto;
import com.takeaway.takeaway.business.dto.UpdateBasicInfoDto;
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

    @PutMapping(path = "/updateProfilePicture")
    public void updateProfilePicture(@RequestPart MultipartFile file) throws TakeawayException {
        CreateAttachmentDto attachmentDto = null;
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
