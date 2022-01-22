package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetAttachmentDto {
    private String filename;
    private byte[] fileData;
    private AttachmentTypes type;
}
