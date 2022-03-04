package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetItemsFiltersDto extends BaseDto {
    private String keyword;
    private UUID itemCategoryId;
    private Integer pageIndex;
    private Integer pageSize;
    private UUID countryId;
    private UUID stateId;
    private UUID cityId;
    private Integer radiusKm;

    public static GetItemsFiltersDto fromOutside(GetItemsFiltersDto data) {
        return GetItemsFiltersDto.builder()
                .keyword(trimLower(data.getKeyword()))
                .itemCategoryId(data.getItemCategoryId())
                .pageIndex(data.getPageIndex())
                .pageSize(data.getPageSize())
                .countryId(data.getCountryId())
                .stateId(data.getStateId())
                .cityId(data.getCityId())
                .radiusKm(data.getRadiusKm())
                .build();
    }
}
