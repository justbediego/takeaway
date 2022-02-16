package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CityDto extends BaseDto {
    private UUID id;
    private String name;
}