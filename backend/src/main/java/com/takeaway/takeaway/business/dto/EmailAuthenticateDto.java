package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailAuthenticateDto extends BaseDto {
    private String email;
    private String password;

    public static EmailAuthenticateDto fromOutside(EmailAuthenticateDto data) {
        return EmailAuthenticateDto.builder()
                .email(trim(data.getEmail()))
                .password(data.getPassword())
                .build();
    }
}
