package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateBasicInfoDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;

    public static UpdateBasicInfoDto fromOutside(UpdateBasicInfoDto data) {
        return UpdateBasicInfoDto.builder()
                .firstName(trim(data.getFirstName()))
                .lastName(trim(data.getLastName()))
                .phoneNumber(trim(data.getPhoneNumber()))
                .phoneNumberCountryCode(trim(data.getPhoneNumberCountryCode()))
                .build();
    }
}
