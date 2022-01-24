package com.takeaway.takeaway.business.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateEmailDto {
    private String email;
}
