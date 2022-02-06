package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyAddressDto extends BaseDto {
    private String title;
    private UUID countryId;
    private UUID stateId;
    private UUID cityId;
    private Double longitude;
    private Double latitude;
    private Integer accuracyM;
    private String streetName;
    private String streetName2;
    private String houseNumber;
    private String additionalInfo;

    public static ModifyAddressDto fromOutside(ModifyAddressDto data) {
        return ModifyAddressDto.builder()
                .houseNumber(trim(data.getHouseNumber()))
                .streetName(trim(data.getStreetName()))
                .streetName2(trim(data.getStreetName2()))
                .title(trim(data.getTitle()))
                .additionalInfo(trim(data.getAdditionalInfo()))
                .accuracyM(data.getAccuracyM())
                .cityId(data.getCityId())
                .countryId(data.getCountryId())
                .latitude(data.getLatitude())
                .longitude(data.getLongitude())
                .stateId(data.getStateId())
                .build();
    }
}
