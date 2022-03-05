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
    private boolean emailIsPublic;
    private boolean phoneNumberIsPublic;

    public boolean getPhoneNumberIsPublic(){
        return phoneNumberIsPublic;
    }

    public boolean getEmailIsPublic(){
        return emailIsPublic;
    }

    public static UpdateBasicInfoDto fromOutside(UpdateBasicInfoDto data) {
        return UpdateBasicInfoDto.builder()
                .firstName(trim(data.getFirstName()))
                .lastName(trim(data.getLastName()))
                .phoneNumber(trim(data.getPhoneNumber()))
                .phoneNumberCountryCode(trim(data.getPhoneNumberCountryCode()))
                .emailIsPublic(data.getEmailIsPublic())
                .phoneNumberIsPublic(data.getPhoneNumberIsPublic())
                .build();
    }
}
