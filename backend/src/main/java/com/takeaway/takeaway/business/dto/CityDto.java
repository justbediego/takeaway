package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CityDto extends BaseDto {
    private UUID id;
    private String name;

    public CityDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}