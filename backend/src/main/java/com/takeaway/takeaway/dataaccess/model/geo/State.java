package com.takeaway.takeaway.dataaccess.model.geo;

import com.takeaway.takeaway.dataaccess.model.BaseEntity;
import com.takeaway.takeaway.dataaccess.model.DataTranslation;
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
@Table(name = "states", indexes = {
        @Index(name = "state_id_idx", columnList = "id", unique = true)
})
@EqualsAndHashCode(callSuper = true)
public class State extends BaseEntity {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private List<DataTranslation> translations = new ArrayList<>();

    // relations

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private List<City> cities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private Geolocation geolocation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private List<Location> inLocations = new ArrayList<>();
}
