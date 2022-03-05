package com.takeaway.takeaway.business.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CountryDto extends BaseDto {
    private final UUID id;
    private final String name;

    public CountryDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}