package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class GetBasicInfoDto {
    private UUID profilePictureId;
    private UUID profilePictureKey;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;
}
