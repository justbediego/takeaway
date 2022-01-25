package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.exception.*;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.EntityTypes;
import com.takeaway.takeaway.dataaccess.model.geo.City;
import com.takeaway.takeaway.dataaccess.model.geo.Country;
import com.takeaway.takeaway.dataaccess.model.geo.State;
import com.takeaway.takeaway.dataaccess.repository.CityRepository;
import com.takeaway.takeaway.dataaccess.repository.CountryRepository;
import com.takeaway.takeaway.dataaccess.repository.StateRepository;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ValidationLogic {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;


    public ValidationLogic(UserRepository userRepository, CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }


    public User validateGetUserById(UUID userId) throws EntityNotFound {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFound(EntityTypes.USER, userId);
        }
        return optionalUser.get();
    }

    public Country validateGetCountryById(UUID countryId) throws TakeawayException {
        if (countryId == null) {
            throw new CountryNotSelectedException();
        }
        Optional<Country> optionalCountry = countryRepository.findById(countryId);
        if (optionalCountry.isEmpty()) {
            throw new EntityNotFound(EntityTypes.COUNTRY, countryId);
        }
        return optionalCountry.get();
    }

    public State validateGetStateById(UUID countryId, UUID stateId) throws TakeawayException {
        if (stateId == null) {
            throw new StateNotSelectedException();
        }
        Optional<State> optionalState = stateRepository.findByIdAndCountry(stateId, countryId);
        if (optionalState.isEmpty()) {
            throw new EntityNotFound(EntityTypes.STATE, stateId);
        }
        return optionalState.get();
    }

    public City validateGetCityById(UUID stateId, UUID cityId) throws TakeawayException {
        if (cityId == null) {
            throw new CityNotSelectedException();
        }
        Optional<City> optionalCity = cityRepository.findByIdAndState(cityId, stateId);
        if (optionalCity.isEmpty()) {
            throw new EntityNotFound(EntityTypes.CITY, cityId);
        }
        return optionalCity.get();
    }

    public void validateFirstName(String firsName) {

    }

    public void validateLastName(String lastName) {

    }

    public void validatePhoneNumber(String countryCode, String number) {

    }

    public void validateUsername(String newUsername, String oldUsername) {

    }

    public void validateEmail(String newEmail, String oldEmail) {

    }

    public void validatePassword(String password) {

    }
    public void validateLongitudeLatitude(Double longitude, Double latitude, Integer accuracyKm) {

    }

    public void validateLocationTitle(String title){

    }

    public void validateLocationAddress(String StreetName, String StreetName2, String HouseNumber, String AdditionalInfo){

    }

    public void validateFilename(String filename){

    }

    public void validateFileData(byte[] fileData){

    }
}
