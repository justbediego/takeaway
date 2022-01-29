package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.GenderTypes;
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
public class GetBasicInfoDto implements Serializable {
    private UUID profilePictureId;
    private UUID profilePictureKey;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberCountryCode;
    private String email;
    private String username;
    private GenderTypes gender;
}
