package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAttachmentDto implements Serializable {
    private String filename;
    private byte[] fileData;
    private AttachmentTypes type;
}
