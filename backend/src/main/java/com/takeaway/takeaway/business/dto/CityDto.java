package com.takeaway.takeaway.business.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CityDto extends BaseDto {
    private final UUID id;
    private final String name;

    public CityDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}