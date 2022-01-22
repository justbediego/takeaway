package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateAttachmentDto {
    private String filename;
    private byte[] fileData;
}
