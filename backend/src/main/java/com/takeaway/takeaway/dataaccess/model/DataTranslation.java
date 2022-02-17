package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.model.geo.City;
import com.takeaway.takeaway.dataaccess.model.geo.Country;
import com.takeaway.takeaway.dataaccess.model.geo.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_translations", indexes = {
        @Index(name = "data_translation_id_idx", columnList = "id", unique = true),
        @Index(name = "language_idx", columnList = "language"),
        @Index(name = "language_country_idx", columnList = "language, country_id", unique = true),
        @Index(name = "language_state_idx", columnList = "language, state_id", unique = true),
        @Index(name = "language_city_idx", columnList = "language, city_id", unique = true),
        @Index(name = "language_item_category_idx", columnList = "language, item_category_id", unique = true),
})
@EqualsAndHashCode(callSuper = true)
public class DataTranslation extends BaseEntity {

    @Column(nullable = false)
    private UserLanguages language;

    @Column(length = 100, nullable = false)
    private String value;

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
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;
}
