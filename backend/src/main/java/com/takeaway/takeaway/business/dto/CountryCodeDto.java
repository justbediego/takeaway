package com.takeaway.takeaway.business.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CountryCodeDto extends BaseDto {
    private final String countryName;
    private final String countryCode;

    public CountryCodeDto(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }
}