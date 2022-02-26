package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateAttachmentDto extends BaseDto {
    private String filename;
    private byte[] fileData;

    public static CreateAttachmentDto fromFile(MultipartFile file) throws TakeawayException {
        CreateAttachmentDto attachmentDto;
        try {
            attachmentDto = CreateAttachmentDto.builder()
                    .fileData(file.getBytes())
                    .filename(trim(file.getOriginalFilename()))
                    .build();
        } catch (IOException ioException) {
            throw new TakeawayException(ExceptionTypes.FILE_UPLOAD);
        }
        return attachmentDto;
    }
}
