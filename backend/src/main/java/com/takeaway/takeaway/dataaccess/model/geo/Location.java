package com.takeaway.takeaway.dataaccess.model.geo;

import com.takeaway.takeaway.dataaccess.model.BaseEntity;
import com.takeaway.takeaway.dataaccess.model.Item;
import com.takeaway.takeaway.dataaccess.model.User;
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
@Table(name = "locations")
public class Location extends BaseEntity {

    @Column(length = 100)
    private String title;

    // relations

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private Geolocation geolocation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private List<User> forUserBillingAddresses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private List<User> forUserShippingAddresses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private List<Item> forItems = new ArrayList<>();
}
