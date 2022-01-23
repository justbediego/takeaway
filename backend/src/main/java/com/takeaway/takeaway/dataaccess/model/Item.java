package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.geo.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User user;

    @Column(length = 1000, nullable = false)
    private String title;

    @Column(length = 10000)
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_item_id")
    private List<Attachment> pictures = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    private ItemCategory category;
}
