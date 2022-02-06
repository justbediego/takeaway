package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.geo.Location;
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
@Table(name = "items", indexes = {
        @Index(name = "category_idx", columnList = "item_category_id")
})
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 5000)
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<DirectMessage> directMessages = new ArrayList<>();
}
