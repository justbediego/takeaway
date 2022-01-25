package com.takeaway.takeaway.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto implements Serializable {
    private String oldPassword;
    private String newPassword;
    private String newPasswordVerify;
}
