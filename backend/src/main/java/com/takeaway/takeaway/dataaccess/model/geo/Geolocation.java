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
@Table(name = "geolocations", indexes = {
        @Index(name = "geolocation_id_idx", columnList = "id", unique = true)
})
@EqualsAndHashCode(callSuper = true)
public class Geolocation extends BaseEntity {

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Integer accuracyM;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private List<Country> inCountries = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private List<City> inCities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private List<State> inStates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private List<Location> inLocations = new ArrayList<>();
}
