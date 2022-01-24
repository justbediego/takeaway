package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String newPasswordVerify;
}
