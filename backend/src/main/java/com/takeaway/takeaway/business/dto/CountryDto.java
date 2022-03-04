package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryDto extends BaseDto {
    private UUID id;
    private String name;

    public CountryDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}