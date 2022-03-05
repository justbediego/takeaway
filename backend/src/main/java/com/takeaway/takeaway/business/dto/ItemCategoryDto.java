package com.takeaway.takeaway.business.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ItemCategoryDto extends BaseDto {
    private final UUID id;
    private final String categoryName;

    public ItemCategoryDto(UUID id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}