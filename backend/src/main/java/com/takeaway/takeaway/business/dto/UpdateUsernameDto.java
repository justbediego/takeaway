package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateUsernameDto extends BaseDto {
    private String username;

    public static UpdateUsernameDto fromOutside(UpdateUsernameDto data) {
        return UpdateUsernameDto.builder()
                .username(trim(data.getUsername()))
                .build();
    }
}
