package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateBasicInfoDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;
}
