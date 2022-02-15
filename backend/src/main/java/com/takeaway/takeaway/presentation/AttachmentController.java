package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AttachmentLogic;
import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {

    private final AttachmentLogic attachmentLogic;

    public AttachmentController(AttachmentLogic attachmentLogic, AuthenticationLogic authenticationLogic) {
        super(authenticationLogic);
        this.attachmentLogic = attachmentLogic;
    }

    @GetMapping(path = "/getAttachmentLink/{attachmentId}/{securityKey}")
    public @ResponseBody
    String getAttachmentLink(@PathVariable UUID attachmentId, @PathVariable UUID securityKey) throws TakeawayException {
        return attachmentLogic.getAttachmentLink(attachmentId, securityKey);
    }
}
