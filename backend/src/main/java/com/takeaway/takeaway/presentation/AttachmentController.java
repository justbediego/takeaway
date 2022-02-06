package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AttachmentLogic;
import com.takeaway.takeaway.business.dto.GetAttachmentDto;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentLogic attachmentLogic;

    public AttachmentController(AttachmentLogic attachmentLogic) {
        this.attachmentLogic = attachmentLogic;
    }

    @GetMapping(path = "/getImage/{attachmentId}/{securityKey}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable UUID attachmentId, @PathVariable UUID securityKey) throws TakeawayException {
        GetAttachmentDto attachmentDto = attachmentLogic.getAttachment(attachmentId, securityKey, AttachmentTypes.IMAGE);
        return attachmentDto.getFileData();
    }

    @GetMapping(path = "/getAttachment/{attachmentId}/{securityKey}", produces = MediaType.ALL_VALUE)
    public @ResponseBody
    byte[] getAttachment(@PathVariable UUID attachmentId, @PathVariable UUID securityKey) throws TakeawayException {
        GetAttachmentDto attachmentDto = attachmentLogic.getAttachment(attachmentId, securityKey, null);
        return attachmentDto.getFileData();
    }
}
