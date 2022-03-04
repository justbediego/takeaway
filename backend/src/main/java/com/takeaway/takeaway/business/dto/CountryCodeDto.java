package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryCodeDto extends BaseDto {
    private String countryName;
    private String countryCode;

    public CountryCodeDto(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }
}