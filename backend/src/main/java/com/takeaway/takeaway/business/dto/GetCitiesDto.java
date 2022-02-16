package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetCitiesDto extends BaseDto {
    private UUID stateId;
    private List<CityDto> cities;
}
