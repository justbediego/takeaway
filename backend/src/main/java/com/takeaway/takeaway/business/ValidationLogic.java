package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.ModifyLocationDto;
import com.takeaway.takeaway.business.dto.ValidModifyLocationDto;
import com.takeaway.takeaway.business.exception.ExceptionEntities;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.User;
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


    public User validateGetUserById(UUID userId) throws TakeawayException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.USER,
                    userId.toString()
            );
        }
        return optionalUser.get();
    }

    public Country validateGetCountryById(UUID countryId) throws TakeawayException {
        if (countryId == null) {
            throw new TakeawayException(ExceptionTypes.COUNTRY_NOT_SELECTED);
        }
        Optional<Country> optionalCountry = countryRepository.findById(countryId);
        if (optionalCountry.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.COUNTRY,
                    countryId.toString()
            );
        }
        return optionalCountry.get();
    }

    public State validateGetStateById(UUID countryId, UUID stateId) throws TakeawayException {
        if (stateId == null) {
            throw new TakeawayException(ExceptionTypes.STATE_NOT_SELECTED);
        }
        Optional<State> optionalState = stateRepository.findByIdAndCountry(stateId, countryId);
        if (optionalState.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.STATE,
                    stateId.toString()
            );
        }
        return optionalState.get();
    }

    public City validateGetCityById(UUID stateId, UUID cityId) throws TakeawayException {
        if (cityId == null) {
            throw new TakeawayException(ExceptionTypes.CITY_NOT_SELECTED);
        }
        Optional<City> optionalCity = cityRepository.findByIdAndState(cityId, stateId);
        if (optionalCity.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.CITY,
                    cityId.toString()
            );
        }
        return optionalCity.get();
    }

    public void validateFirstName(String firsName) throws TakeawayException {
        String firstNamePattern = "^[\\p{L}]([\\- '\",.\\p{L}]{0,90}[\\p{L}])$";
        if (!firsName.matches(firstNamePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_FIRST_NAME);
        }
    }

    public void validateLastName(String lastName) throws TakeawayException {
        String lastNamePattern = "^[\\p{L}]([\\- '\",.\\p{L}]{0,90}[\\p{L}])$";
        if (!lastName.matches(lastNamePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_LAST_NAME);
        }
    }

    public void validatePhoneNumber(String countryCode, String number) throws TakeawayException {
        final String countryCodePattern = "^\\+\\d{1,3}$";
        final String phoneNumberPattern = "^\\d{5,15}$";
        if (!countryCode.matches(countryCodePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_COUNTRY_CODE);
        }
        if (!number.matches(phoneNumberPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_PHONE_NUMBER);
        }
    }

    @SuppressWarnings("squid:S5998")
    public void validateEmail(String email) throws TakeawayException {
        if (StringUtils.isBlank(email) || email.length() > 200) {
            throw new TakeawayException(ExceptionTypes.INVALID_EMAIL);
        }
        final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(emailPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_EMAIL);
        }
    }

    public void validateUsername(String username) throws TakeawayException {
        final String usernamePattern = "^[a-zA-Z][a-zA-Z0-9._]{3,190}[a-zA-Z0-9]$";
        if (!username.matches(usernamePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_USERNAME);
        }
    }

    public void validateChangeUsername(String newUsername, String oldUsername) throws TakeawayException {
        validateUsername(newUsername);
        if (newUsername.equalsIgnoreCase(oldUsername.toLowerCase())) {
            throw new TakeawayException(ExceptionTypes.NEW_USERNAME_SAME_AS_OLD);
        }
        Optional<User> optionalNewUser = userRepository.findByUsername(newUsername);
        if (optionalNewUser.isPresent()) {
            throw new TakeawayException(ExceptionTypes.USERNAME_ALREADY_IN_USE);
        }
    }

    public void validateChangeEmail(String newEmail, String oldEmail) throws TakeawayException {
        validateEmail(newEmail);
        if (newEmail.equalsIgnoreCase(oldEmail.toLowerCase())) {
            throw new TakeawayException(ExceptionTypes.NEW_EMAIL_SAME_AS_OLD);
        }
        Optional<User> optionalNewUser = userRepository.findByEmail(newEmail);
        if (optionalNewUser.isPresent()) {
            throw new TakeawayException(ExceptionTypes.EMAIL_ALREADY_IN_USE);
        }
    }

    public void validatePassword(String password) throws TakeawayException {
        // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        final String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_PASSWORD);
        }
    }

    public void validateLongitudeLatitude(Double longitude, Double latitude, Integer accuracyM) throws TakeawayException {
        // latitude in degrees is -90 and +90
        // Longitude is in the range -180 and +180
        if (longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90 || accuracyM < 0 || accuracyM > 10000) {
            throw new TakeawayException(ExceptionTypes.INVALID_GEOLOCATION_VALUES);
        }
    }

    public void validateLocationTitle(String title) throws TakeawayException {
        String locationTitlePattern = "^[\\p{L}\\p{N}\\d\"\\[\\]~,;:<>|{}()*&%#'._\\-+ ]{3,90}$";
        if (!title.matches(locationTitlePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_LOCATION_TITLE);
        }
    }

    public void validateLocationAddress(String streetName, String streetName2, String houseNumber, String additionalInfo) throws TakeawayException {
        String streetPattern = "^[\\p{L}\\p{N}\\d\"\\[\\]~,;:<>|{}()*&#'._\\-+ /\\\\@]{3,100}$";
        if (!streetName.matches(streetPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_ADDRESS);
        }
        if (StringUtils.isNotBlank(streetName2) && !streetName2.matches(streetPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_ADDRESS);
        }
        String houseNumberPattern = "^[\\p{L}\\p{N}\\d\"\\[\\]~,;:<>|{}()*&#'._\\-+ /\\\\@]{1,20}$";
        if (!houseNumber.matches(houseNumberPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_ADDRESS);
        }
        String additionalPattern = "^[\\p{L}\\p{N}\\d\"\\[\\]~,;:<>|{}()*&#'._\\-+ /\\\\@]{1,200}$";
        if (!additionalInfo.matches(additionalPattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_ADDRESS);
        }
    }

    public void validateFilename(String filename) throws TakeawayException {
        final String filenamePattern = "^[\\p{L}\\p{N}\\d\"\\[\\]~,;:<>|{}()*&#'._\\-+ /\\\\@$^!]{1,300}$";
        if (!filename.matches(filenamePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_FILENAME);
        }
    }

    public void validateFileData(byte[] fileData) throws TakeawayException {
        if (fileData == null || fileData.length == 0) {
            throw new TakeawayException(ExceptionTypes.EMPTY_FILE_UPLOADED);
        }
        if (fileData.length > maxUploadFileSizeInBytes) {
            throw new TakeawayException(ExceptionTypes.FILE_SIZE_EXCEEDED);
        }
    }

    public void validateItemDescription(String description) throws TakeawayException {
        String titlePattern = "^.{20,5000}$";
        if (!description.matches(titlePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_ITEM_DESCRIPTION);
        }
    }

    public void validateItemTitle(String title) throws TakeawayException {
        String titlePattern = "^[\\p{L}\\p{N}\\d\"\\[\\]~,;:<>|{}()*&%#'._\\-+ ]{5,100}$";
        if (!title.matches(titlePattern)) {
            throw new TakeawayException(ExceptionTypes.INVALID_ITEM_TITLE);
        }
    }

    public ItemCategory validateGetItemCategoryById(UUID categoryId) throws TakeawayException {
        Optional<ItemCategory> optionalCategory = itemCategoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.ITEM_CATEGORY,
                    categoryId.toString()
            );
        }
        if (!optionalCategory.get().getChildCategories().isEmpty()) {
            throw new TakeawayException(ExceptionTypes.INVALID_ITEM_CATEGORY);
        }
        return optionalCategory.get();
    }

    public Location validateNewLocation(ModifyLocationDto modifyLocationDto) throws TakeawayException {
        ValidModifyLocationDto validLocationDto = validateGetLocation(modifyLocationDto);
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(validLocationDto.getLatitude());
        geolocation.setLongitude(validLocationDto.getLongitude());
        geolocation.setAccuracyM(validLocationDto.getAccuracyM());

        Location location = new Location();
        location.setGeolocation(geolocation);
        location.setCountry(validLocationDto.getCountry());
        location.setState(validLocationDto.getState());
        location.setCity(validLocationDto.getCity());
        location.setTitle(validLocationDto.getTitle());
        location.setStreetName(validLocationDto.getStreetName());
        location.setStreetName2(validLocationDto.getStreetName2());
        location.setHouseNumber(validLocationDto.getHouseNumber());
        location.setAdditionalInfo(validLocationDto.getAdditionalInfo());
        return location;
    }

    public ValidModifyLocationDto validateGetLocation(ModifyLocationDto modifyLocationDto) throws TakeawayException {
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
                .latitude(hasGeolocation ? modifyLocationDto.getLatitude() : null)
                .longitude(hasGeolocation ? modifyLocationDto.getLongitude() : null)
                .accuracyM(hasGeolocation ? modifyLocationDto.getAccuracyM() : null)
                .build();
    }
}
