package com.takeaway.takeaway.business.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StateDto extends BaseDto {
    private UUID id;
    private String name;

    public StateDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}