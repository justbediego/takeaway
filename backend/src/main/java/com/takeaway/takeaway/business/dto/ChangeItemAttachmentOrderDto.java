package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangeItemAttachmentOrderDto extends BaseDto {
    private List<UUID> attachmentIdsInOrder;

    public static ChangeItemAttachmentOrderDto fromOutside(ChangeItemAttachmentOrderDto data) {
        return ChangeItemAttachmentOrderDto.builder()
                .attachmentIdsInOrder(data.getAttachmentIdsInOrder())
                .build();
    }
}
