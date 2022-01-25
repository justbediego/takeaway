package com.takeaway.takeaway.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyAddressDto implements Serializable {
    private String title;
    private UUID countryId;
    private UUID stateId;
    private UUID cityId;
    private Double longitude;
    private Double latitude;
    private Integer accuracyKm;
    private String streetName;
    private String streetName2;
    private String houseNumber;
    private String additionalInfo;
}
