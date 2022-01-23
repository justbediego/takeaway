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
@Table(name = "users")
public class User extends BaseEntity {

    @Column(length = 1000, nullable = false)
    private String email;

    @Column(length = 1000, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String hashedPassword;

    @Column(length = 200, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private Location billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Location shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_id")
    private Attachment profilePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_original_id")
    private Attachment profilePictureOriginal;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private List<Item> items = new ArrayList<>();

}
