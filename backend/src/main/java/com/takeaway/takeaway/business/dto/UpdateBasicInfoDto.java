package com.takeaway.takeaway.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBasicInfoDto implements Serializable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;
}
