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
@Table(name = "cities", indexes = {
        @Index(name = "city_id_idx", columnList = "id", unique = true)
})
@EqualsAndHashCode(callSuper = true)
public class City extends BaseEntity {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private List<DataTranslation> translations = new ArrayList<>();

    // relations

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geolocation_id")
    private Geolocation geolocation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private List<Location> inLocations = new ArrayList<>();
}
