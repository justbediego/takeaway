package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetItemDto extends BaseDto {
    private UUID id;
    private String title;
    private String description;
    private String firstName;
    private String lastName;
    private String publicPhoneNumber;
    private String publicEmail;
    private Date publishStart;
    private UUID itemCategoryId;
    private UUID countryId;
    private String countryName;
    private UUID stateId;
    private String stateName;
    private UUID cityId;
    private String cityName;
    private List<String> pictureUrls;
}
