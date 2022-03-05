package com.takeaway.takeaway.business.dto;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class GetSingleItemDto extends BaseDto {

    private final String title;
    private final String description;
    private final String firstName;
    private final String lastName;
    private final String publicPhoneNumber;
    private final String publicEmail;
    private final Date publishStart;
    private final UUID itemCategoryId;
    private final UUID countryId;
    private final String countryName;
    private final UUID stateId;
    private final String stateName;
    private final UUID cityId;
    private final String cityName;
    private final List<String> pictureUrls;

    public GetSingleItemDto(String title, String description, String firstName, String lastName, String publicPhoneNumber, String publicEmail, Date publishStart, UUID itemCategoryId, UUID countryId, String countryName, UUID stateId, String stateName, UUID cityId, String cityName, String pictureUrlsJson) {
        this.title = title;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publicPhoneNumber = publicPhoneNumber;
        this.publicEmail = publicEmail;
        this.publishStart = publishStart;
        this.itemCategoryId = itemCategoryId;
        this.countryId = countryId;
        this.countryName = countryName;
        this.stateId = stateId;
        this.stateName = stateName;
        this.cityId = cityId;
        this.cityName = cityName;
        if (pictureUrlsJson != null) {
            Gson gson = new Gson();
            this.pictureUrls = gson.fromJson(pictureUrlsJson, List.class);
        } else {
            this.pictureUrls = null;
        }
    }
}
