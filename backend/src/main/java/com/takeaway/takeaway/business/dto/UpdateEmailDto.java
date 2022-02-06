package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateEmailDto extends BaseDto {
    private String email;

    public static UpdateEmailDto fromOutside(UpdateEmailDto data) {
        return UpdateEmailDto.builder()
                .email(trim(data.getEmail()))
                .build();
    }
}
