package com.takeaway.takeaway.dataaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_categories", indexes = {
        @Index(name = "item_category_id_idx", columnList = "id", unique = true),
        @Index(name="category_code_idx", columnList = "categoryCode", unique = true),
})
@EqualsAndHashCode(callSuper = true)
public class ItemCategory extends BaseEntity {

    // an algorithm to best find children
    @Column(nullable = false)
    private Long categoryCode;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    private List<DataTranslation> translations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private ItemCategory parentCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private List<ItemCategory> childCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    private List<Item> items = new ArrayList<>();
}
