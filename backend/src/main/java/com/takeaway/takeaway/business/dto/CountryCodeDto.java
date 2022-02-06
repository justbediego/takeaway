package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryCodeDto extends BaseDto {
    private String countryName;
    private String countryCode;
}