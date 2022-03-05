package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangeItemPictureOrderDto extends BaseDto {
    private List<UUID> pictureIdsInOrder;

    public static ChangeItemPictureOrderDto fromOutside(ChangeItemPictureOrderDto data) {
        return ChangeItemPictureOrderDto.builder()
                .pictureIdsInOrder(data.getPictureIdsInOrder())
                .build();
    }
}
