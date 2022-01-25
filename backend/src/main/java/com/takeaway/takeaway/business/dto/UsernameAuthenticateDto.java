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
public class UsernameAuthenticateDto implements Serializable {
    private String username;
    private String password;
}
