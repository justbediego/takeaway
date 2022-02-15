package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.ModifyLocationDto;
import com.takeaway.takeaway.business.dto.ValidModifyLocationDto;
import com.takeaway.takeaway.business.exception.*;
import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.EntityTypes;
import com.takeaway.takeaway.dataaccess.model.geo.*;
import com.takeaway.takeaway.dataaccess.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
    private final ItemCategoryRepository itemCategoryRepository;

    @Value("${spring.application.maxUploadFileSizeInBytes}")
    private Long maxUploadFileSizeInBytes;

    public ValidationLogic(UserRepository userRepository, CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, ItemCategoryRepository itemCategoryRepository) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.itemCategoryRepository = itemCategoryRepository;
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

    @SuppressWarnings("squid:S5998")
    public void validateEmail(String email) throws TakeawayException {
        if (StringUtils.isBlank(email) || email.length() > 200) {
            throw new InvalidEmailException();
        }
        final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(emailPattern)) {
            throw new InvalidEmailException();
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
        if (newUsername.equalsIgnoreCase(oldUsername.toLowerCase())) {
            throw new NewUsernameSameAsOldException();
        }
        Optional<User> optionalNewUser = userRepository.findByUsername(newUsername);
        if (!optionalNewUser.isEmpty()) {
            throw new UsernameAlreadyInUseException();
        }
    }

    public void validateChangeEmail(String newEmail, String oldEmail) throws TakeawayException {
        validateEmail(newEmail);
        if (newEmail.equalsIgnoreCase(oldEmail.toLowerCase())) {
            throw new NewEmailSameAsOldException();
        }
        Optional<User> optionalNewUser = userRepository.findByEmail(newEmail);
        if (!optionalNewUser.isEmpty()) {
            throw new EmailAlreadyInUseException();
        }
    }

    public void validatePassword(String password) throws TakeawayException {
        // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        final String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new InvalidPasswordException();
        }
    }

    public void validateLongitudeLatitude(Double longitude, Double latitude, Integer accuracyM) throws TakeawayException {
        // latitude in degrees is -90 and +90
        // Longitude is in the range -180 and +180
        if (longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90 || accuracyM < 0 || accuracyM > 10000) {
            throw new InvalidGeolocationValuesException();
        }
    }

    public void validateLocationTitle(String title) throws TakeawayException {
        String locationTitlePattern = "^[a-zA-Z0-9'._\\- ]{3,80}$";
        if (!title.matches(locationTitlePattern)) {
            throw new InvalidLocationTitleException();
        }
    }

    public void validateLocationAddress(String streetName, String streetName2, String houseNumber, String additionalInfo) throws TakeawayException {
        String streetPattern = "^[a-zA-Z0-9'._\\-$#&*()+\\/\\\\<>\"{}|;:@ ]{3,80}$";
        if (!streetName.matches(streetPattern)) {
            throw new InvalidAddressException();
        }
        if (StringUtils.isNotBlank(streetName2) && !streetName2.matches(streetPattern)) {
            throw new InvalidAddressException();
        }
        String houseNumberPattern = "^[a-zA-Z0-9'._\\-$#&*()+\\/\\\\<>\"{}|;:@ ]{1,20}$";
        if (!houseNumber.matches(houseNumberPattern)) {
            throw new InvalidAddressException();
        }
        String additionalPattern = "^[a-zA-Z0-9'._\\-$#&*()+\\/\\\\<>\"{}|;:@ ]{1,200}$";
        if (!additionalInfo.matches(additionalPattern)) {
            throw new InvalidAddressException();
        }
    }

    public void validateFilename(String filename) throws TakeawayException {
        final String filenamePattern = "^[\\p{L}0-9\\-_ (),.*:\\\\\\/]{1,300}$";
        if (!filename.matches(filenamePattern)) {
            throw new InvalidFilenameException();
        }
    }

    public void validateFileData(byte[] fileData) throws TakeawayException {
        if (fileData == null || fileData.length == 0) {
            throw new EmptyFileUploadedException();
        }
        if (fileData.length > maxUploadFileSizeInBytes) {
            throw new FileSizeExceededException();
        }
    }

    public void validateItemDescription(String description)throws TakeawayException {

    }

    public void validateItemTitle(String title)throws TakeawayException {

    }

    public ItemCategory validateGetItemCategoryById(UUID categoryId) throws EntityNotFound {
        Optional<ItemCategory> optionalCategory = itemCategoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()){
            throw new EntityNotFound(EntityTypes.ITEM_CATEGORY, categoryId);
        }
        return optionalCategory.get();
    }

    public ValidModifyLocationDto validateGetLocation(ModifyLocationDto modifyLocationDto)  throws TakeawayException{
        boolean hasGeolocation = false;
        if (modifyLocationDto.getLatitude() != null || modifyLocationDto.getLongitude() != null) {
            validateLongitudeLatitude(
                    modifyLocationDto.getLongitude(),
                    modifyLocationDto.getLatitude(),
                    modifyLocationDto.getAccuracyM()
            );
            hasGeolocation = true;
        }
        if (StringUtils.isNotBlank(modifyLocationDto.getTitle())) {
            validateLocationTitle(modifyLocationDto.getTitle());
        }
        validateLocationAddress(
                modifyLocationDto.getStreetName(),
                modifyLocationDto.getStreetName2(),
                modifyLocationDto.getHouseNumber(),
                modifyLocationDto.getAdditionalInfo()
        );
        Country country = validateGetCountryById(modifyLocationDto.getCountryId());
        State state = validateGetStateById(country.getId(), modifyLocationDto.getStateId());
        City city = validateGetCityById(state.getId(), modifyLocationDto.getCityId());
        return ValidModifyLocationDto.builder()
                .title(modifyLocationDto.getTitle())
                .streetName(modifyLocationDto.getStreetName())
                .streetName2(modifyLocationDto.getStreetName2())
                .houseNumber(modifyLocationDto.getHouseNumber())
                .additionalInfo(modifyLocationDto.getAdditionalInfo())
                .hasGeolocation(hasGeolocation)
                .country(country)
                .state(state)
                .city(city)
                .latitude(hasGeolocation ? modifyLocationDto.getLatitude(): null)
                .longitude(hasGeolocation ? modifyLocationDto.getLongitude(): null)
                .accuracyM(hasGeolocation ? modifyLocationDto.getAccuracyM(): null)
                .build();
    }
}
