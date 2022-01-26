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

    public void validateFirstName(String firsName) throws TakeawayException {
        String firstNamePattern = "^[a-zA-Z]([- ',.a-zA-Z]{0,40}[a-zA-Z])$";
        if (!firsName.matches(firstNamePattern)) {
            throw new InvalidFirstNameException();
        }
    }

    public void validateLastName(String lastName) throws TakeawayException {
        String lastNamePattern = "^[a-zA-Z]([- ',.a-zA-Z]{0,90}[a-zA-Z])$";
        if (!lastName.matches(lastNamePattern)) {
            throw new InvalidLastNameException();
        }
    }

    public void validatePhoneNumber(String countryCode, String number) throws TakeawayException {
        final String countryCodePattern = "^\\+\\d{1,3}$";
        final String phoneNumberPattern = "^\\d{5,15}$";
        if (!countryCode.matches(countryCodePattern)) {
            throw new InvalidCountryCodeException();
        }
        if (!number.matches(phoneNumberPattern)) {
            throw new InvalidPhoneNumberException();
        }
    }

    public void validateUsername(String username) throws TakeawayException {
        final String usernamePattern = "^[a-zA-Z][a-zA-Z0-9._]{3,40}[a-zA-Z0-9]$";
        if (!username.matches(usernamePattern)) {
            throw new InvalidUsernameException();
        }
    }

    public void validateChangeUsername(String newUsername, String oldUsername) throws TakeawayException {
        validateUsername(newUsername);
        if (newUsername.toLowerCase().equals(oldUsername.toLowerCase())) {
            throw new NewUsernameSameAsOldException();
        }
        Optional<User> optionalNewUser = userRepository.findByUsername(newUsername);
        if (!optionalNewUser.isEmpty()) {
            throw new UsernameAlreadyInUseException();
        }
    }

    public void validateEmail(String email) throws TakeawayException {
        final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(emailPattern)) {
            throw new InvalidEmailException();
        }
    }

    public void validateChangeEmail(String newEmail, String oldEmail) throws TakeawayException {
        validateEmail(newEmail);
        if (newEmail.toLowerCase().equals(oldEmail.toLowerCase())) {
            throw new NewEmailSameAsOldException();
        }
        Optional<User> optionalNewUser = userRepository.findByEmail(newEmail);
        if (!optionalNewUser.isEmpty()) {
            throw new EmailAlreadyInUseException();
        }
    }

    public void validatePassword(String password) {

    }

    public void validateLongitudeLatitude(Double longitude, Double latitude, Integer accuracyKm) {

    }

    public void validateLocationTitle(String title) {

    }

    public void validateLocationAddress(String streetName, String streetName2, String houseNumber, String additionalInfo) {

    }

    public void validateFilename(String filename) {

    }

    public void validateFileData(byte[] fileData) {

    }
}
