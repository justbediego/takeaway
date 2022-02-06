package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.business.exception.ItemWithoutLocationException;
import com.takeaway.takeaway.business.exception.TakeawayException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateItemDto extends BaseDto {
    private String title;
    private String description;
    private UUID itemCategoryId;
    private ModifyAddressDto location;
    private List<CreateAttachmentDto> images;

    public static CreateItemDto fromOutside(UpdateItemDto data, MultipartFile[] files) throws TakeawayException {
        List<CreateAttachmentDto> attachmentList = new ArrayList<>();
        for (var file : files) {
            attachmentList.add(CreateAttachmentDto.fromFile(file));
        }
        if (data.getLocation() == null) {
            throw new ItemWithoutLocationException();
        }
        return CreateItemDto.builder()
                .title(trim(data.getTitle()))
                .description(trim(data.getDescription()))
                .itemCategoryId(data.getItemCategoryId())
                .location(ModifyAddressDto.fromOutside(data.getLocation()))
                .images(attachmentList)
                .build();
    }
}
