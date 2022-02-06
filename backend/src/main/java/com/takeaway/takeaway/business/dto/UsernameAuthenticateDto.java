package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UsernameAuthenticateDto extends BaseDto {
    private String username;
    private String password;

    public static UsernameAuthenticateDto fromOutside(UsernameAuthenticateDto data) {
        return UsernameAuthenticateDto.builder()
                .username(trim(data.getUsername()))
                .password(data.getPassword())
                .build();
    }
}
