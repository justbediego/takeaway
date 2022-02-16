package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetItemCategoriesDto extends BaseDto {
    private List<ItemCategoryDto> categories;
}
