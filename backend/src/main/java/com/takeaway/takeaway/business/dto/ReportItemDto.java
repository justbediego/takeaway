package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.enums.ItemReportCategories;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReportItemDto extends BaseDto {

    private ItemReportCategories category;
    private String description;

    public static ReportItemDto fromOutside(ReportItemDto data) {
        return ReportItemDto.builder()
                .category(data.getCategory())
                .description(trim(data.getDescription()))
                .build();
    }
}
