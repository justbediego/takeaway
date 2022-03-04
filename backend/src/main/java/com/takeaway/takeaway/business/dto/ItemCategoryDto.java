package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItemCategoryDto extends BaseDto {
    private UUID id;
    private String categoryName;

    public ItemCategoryDto(UUID id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}