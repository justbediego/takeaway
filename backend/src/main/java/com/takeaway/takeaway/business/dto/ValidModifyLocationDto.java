package com.takeaway.takeaway.business.dto;

import com.takeaway.takeaway.dataaccess.model.geo.City;
import com.takeaway.takeaway.dataaccess.model.geo.Country;
import com.takeaway.takeaway.dataaccess.model.geo.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidModifyLocationDto {
    private String title;
    private Country country;
    private State state;
    private City city;
    private boolean hasGeolocation;
    private Double longitude;
    private Double latitude;
    private Integer accuracyM;
    private String streetName;
    private String streetName2;
    private String houseNumber;
    private String additionalInfo;

    public boolean getHasGeolocation() {
        return hasGeolocation;
    }
}
