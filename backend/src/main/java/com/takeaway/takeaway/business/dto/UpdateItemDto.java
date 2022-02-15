package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.business.exception.ItemWithoutLocationException;
import com.takeaway.takeaway.business.exception.TakeawayException;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateItemDto extends BaseDto {
    private String title;
    private String description;
    private UUID itemCategoryId;
    private ModifyLocationDto location;

    public static UpdateItemDto fromOutside(UpdateItemDto data) throws TakeawayException {
        if (data.getLocation() == null) {
            throw new ItemWithoutLocationException();
        }
        return UpdateItemDto.builder()
                .title(trim(data.getTitle()))
                .description(trim(data.getDescription()))
                .itemCategoryId(data.getItemCategoryId())
                .location(ModifyLocationDto.fromOutside(data.getLocation()))
                .build();
    }
}
