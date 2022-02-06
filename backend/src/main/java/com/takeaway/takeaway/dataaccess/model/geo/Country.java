package com.takeaway.takeaway.dataaccess.model.geo;

import com.takeaway.takeaway.dataaccess.model.BaseEntity;
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
@Table(name = "countries")
@EqualsAndHashCode(callSuper = true)
public class Country extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 5)
    private String countryCode;

    // relations

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private List<State> states = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private Geolocation geolocation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private List<Location> inLocations = new ArrayList<>();
}
