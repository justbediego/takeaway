package com.takeaway.takeaway.business.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangePasswordDto extends BaseDto {
    private String oldPassword;
    private String newPassword;
    private String newPasswordVerify;

    public static ChangePasswordDto fromOutside(ChangePasswordDto data) {
        return ChangePasswordDto.builder()
                .oldPassword(data.getOldPassword())
                .newPassword(data.getNewPassword())
                .newPasswordVerify(data.getNewPasswordVerify())
                .build();
    }
}
