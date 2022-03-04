package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetSingleItemDto extends BaseDto {
    private List<String> imageUrls;
}
