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

    @Column(length = 100)
    private String StreetName;

    @Column(length = 100)
    private String StreetName2;

    @Column(length = 10)
    private String HouseNumber;

    @Column(length = 200)
    private String AdditionalInfo;

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
    @JoinColumn(name = "user_address_id")
    private List<User> forUserAddresses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private List<Item> forItems = new ArrayList<>();
}
