package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUsernameDto {
    private String username;
}
