package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ModifyAddressDto {
    private String title;
    private UUID countryId;
    private UUID stateId;
    private UUID cityId;
    private Double longitude;
    private Double latitude;
    private Integer accuracyKm;
    private String StreetName;
    private String StreetName2;
    private String HouseNumber;
    private String AdditionalInfo;
}
