package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetStatesDto extends BaseDto {
    private UUID countryId;
    private List<StateDto> states;
}
