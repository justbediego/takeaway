package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.enums.ItemReportCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_reports")
@EqualsAndHashCode(callSuper = true)
public class ItemReport extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private ItemReportCategories category;

    @Column(length = 5000)
    private String description;

}
