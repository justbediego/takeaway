package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.GenderTypes;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetBasicInfoDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;
    private String email;
    private String username;
    private GenderTypes gender;
    private String profilePictureLink;
    private String profilePictureOriginalLink;
}
