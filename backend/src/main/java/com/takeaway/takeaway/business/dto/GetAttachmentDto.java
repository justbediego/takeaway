package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetAttachmentDto extends BaseDto {
    private String filename;
    private byte[] fileData;
    private AttachmentTypes type;
}
