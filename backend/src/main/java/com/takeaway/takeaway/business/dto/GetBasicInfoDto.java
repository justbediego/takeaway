package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.GenderTypes;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetBasicInfoDto extends BaseDto {
    private UUID profilePictureId;
    private UUID profilePictureKey;
    private UUID profilePictureOriginalId;
    private UUID profilePictureOriginalKey;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;
    private String email;
    private String username;
    private GenderTypes gender;
}
