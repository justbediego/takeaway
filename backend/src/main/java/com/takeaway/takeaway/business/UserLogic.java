package com.takeaway.takeaway.business;

import com.google.common.hash.Hashing;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.*;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import com.takeaway.takeaway.dataaccess.model.enums.EntityTypes;
import com.takeaway.takeaway.dataaccess.model.geo.*;
import com.takeaway.takeaway.dataaccess.repository.AttachmentRepository;
import com.takeaway.takeaway.dataaccess.repository.GeolocationRepository;
import com.takeaway.takeaway.dataaccess.repository.LocationRepository;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class UserLogic {

    @Value("${spring.application.profilePictureSize}")
    private Integer profilePictureSize;

    @Value("${spring.application.passwordHashSalt}")
    private String passwordHashSalt;

    private final ValidationLogic validationLogic;

    private final AttachmentLogic attachmentLogic;

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final GeolocationRepository geolocationRepository;

    private final AttachmentRepository attachmentRepository;

    public UserLogic(ValidationLogic validationLogic, AttachmentLogic attachmentLogic, UserRepository userRepository, LocationRepository locationRepository, GeolocationRepository geolocationRepository, AttachmentRepository attachmentRepository) {
        this.validationLogic = validationLogic;
        this.attachmentLogic = attachmentLogic;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.geolocationRepository = geolocationRepository;
        this.attachmentRepository = attachmentRepository;
    }

    private String getHashedPassword(String password) {
        return Hashing.sha256()
                .hashString(String.format("%s%s", passwordHashSalt, password), StandardCharsets.UTF_8)
                .toString();
    }

    public void deleteProfilePicture(UUID userId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        if (user.getProfilePicture() == null) {
            throw new EntityNotFound(EntityTypes.ATTACHMENT);
        }
        if (user.getProfilePictureOriginal() == null) {
            throw new EntityNotFound(EntityTypes.ATTACHMENT);
        }
        attachmentLogic.removeAttachment(user.getProfilePicture().getId());
        attachmentLogic.removeAttachment(user.getProfilePictureOriginal().getId());
        userRepository.save(user);
    }

    public void updateProfilePicture(UUID userId, CreateAttachmentDto attachmentDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        byte[] originalData = attachmentLogic.prepareImage(attachmentDto.getFileData(), null);
        byte[] smallData = attachmentLogic.prepareImage(attachmentDto.getFileData(), profilePictureSize);

        // business
        UUID originalId = attachmentLogic.createAttachment(
                CreateAttachmentDto.builder()
                        // not trusting the incoming data
                        .fileData(originalData)
                        .filename(attachmentDto.getFilename())
                        .build(),
                AttachmentTypes.IMAGE);
        UUID smallId = attachmentLogic.createAttachment(
                CreateAttachmentDto.builder()
                        .fileData(smallData)
                        .filename(attachmentDto.getFilename())
                        .build(),
                AttachmentTypes.IMAGE);

        Optional<Attachment> optionalOriginal = attachmentRepository.findById(originalId);
        Optional<Attachment> optionalSmall = attachmentRepository.findById(smallId);
        if (optionalOriginal.isEmpty() || optionalSmall.isEmpty()) {
            throw new UnrecognizedException("Attachments are not accessible after being created");
        }
        Attachment originalPicture = optionalOriginal.get();
        Attachment smallPicture = optionalSmall.get();

        if (user.getProfilePicture() != null) {
            attachmentLogic.removeAttachment(user.getProfilePicture().getId());
        }
        if (user.getProfilePictureOriginal() != null) {
            attachmentLogic.removeAttachment(user.getProfilePictureOriginal().getId());
        }
        user.setProfilePicture(smallPicture);
        user.setProfilePictureOriginal(originalPicture);
        user.updateDateModified();
        userRepository.save(user);
    }

    public GetBasicInfoDto getBasicInfo(UUID userId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Attachment profilePicture = user.getProfilePicture();
        Attachment profilePictureOriginal = user.getProfilePictureOriginal();
        return GetBasicInfoDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .phoneNumberCountryCode(user.getPhoneNumberCountryCode())
                .profilePictureId(profilePicture == null ? null : profilePicture.getId())
                .profilePictureKey(profilePicture == null ? null : profilePicture.getSecurityKey())
                .profilePictureOriginalId(profilePictureOriginal == null ? null : profilePictureOriginal.getId())
                .profilePictureOriginalKey(profilePictureOriginal == null ? null : profilePictureOriginal.getSecurityKey())
                .build();
    }

    public void updateBasicInfo(UUID userId, UpdateBasicInfoDto updateBasicInfoDto) throws TakeawayException {
        // validation
        if (StringUtils.isNotBlank(updateBasicInfoDto.getFirstName())) {
            validationLogic.validateFirstName(updateBasicInfoDto.getFirstName());
        }
        if (StringUtils.isNotBlank(updateBasicInfoDto.getLastName())) {
            validationLogic.validateLastName(updateBasicInfoDto.getLastName());
        }
        if (StringUtils.isNotBlank(updateBasicInfoDto.getPhoneNumberCountryCode()) ||
                StringUtils.isNotBlank(updateBasicInfoDto.getPhoneNumber())) {
            validationLogic.validatePhoneNumber(
                    updateBasicInfoDto.getPhoneNumberCountryCode(),
                    updateBasicInfoDto.getPhoneNumber()
            );
        }

        User user = validationLogic.validateGetUserById(userId);

        // business
        user.setFirstName(updateBasicInfoDto.getFirstName());
        user.setLastName(updateBasicInfoDto.getLastName());
        user.setPhoneNumber(updateBasicInfoDto.getPhoneNumber());
        user.setPhoneNumberCountryCode(updateBasicInfoDto.getPhoneNumberCountryCode());
        user.updateDateModified();
        userRepository.save(user);
    }

    public void updateUsername(UUID userId, UpdateUsernameDto updateUsernameDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        validationLogic.validateChangeUsername(
                updateUsernameDto.getUsername(),
                user.getUsername()
        );

        // business
        user.setUsername(updateUsernameDto.getUsername());
        user.updateDateModified();
        userRepository.save(user);
    }

    public void updateEmail(UUID userId, UpdateEmailDto updateEmailDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        validationLogic.validateChangeEmail(
                updateEmailDto.getEmail(),
                user.getEmail()
        );

        // business
        user.setEmail(updateEmailDto.getEmail());
        user.updateDateModified();
        userRepository.save(user);
    }

    public void changePassword(UUID userId, ChangePasswordDto changePasswordDto) throws TakeawayException {
        // validation
        validationLogic.validatePassword(changePasswordDto.getNewPassword());
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordVerify())) {
            throw new VerifyPasswordException();
        }
        final String hashedOldPassword = getHashedPassword(changePasswordDto.getOldPassword());
        User user = validationLogic.validateGetUserById(userId);
        if (!hashedOldPassword.equals(user.getHashedPassword())) {
            throw new WrongPasswordException();
        }

        // business
        user.setHashedPassword(getHashedPassword(changePasswordDto.getNewPassword()));
        user.updateDateModified();
        userRepository.save(user);
    }

    public void modifyAddress(UUID userId, ModifyAddressDto modifyAddressDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        boolean hasGeolocation = false;
        if (modifyAddressDto.getLatitude() != null || modifyAddressDto.getLongitude() != null) {
            validationLogic.validateLongitudeLatitude(
                    modifyAddressDto.getLongitude(),
                    modifyAddressDto.getLatitude(),
                    modifyAddressDto.getAccuracyM()
            );
            hasGeolocation = true;
        }
        if (StringUtils.isNotBlank(modifyAddressDto.getTitle())) {
            validationLogic.validateLocationTitle(modifyAddressDto.getTitle());
        }
        validationLogic.validateLocationAddress(
                modifyAddressDto.getStreetName(),
                modifyAddressDto.getStreetName2(),
                modifyAddressDto.getHouseNumber(),
                modifyAddressDto.getAdditionalInfo()
        );

        // business
        Country country = validationLogic.validateGetCountryById(modifyAddressDto.getCountryId());
        State state = validationLogic.validateGetStateById(country.getId(), modifyAddressDto.getStateId());
        City city = validationLogic.validateGetCityById(state.getId(), modifyAddressDto.getCityId());
        if (user.getAddress() == null) {
            user.setAddress(new Location());
        }
        Location address = user.getAddress();
        if (address.getGeolocation() == null) {
            address.setGeolocation(new Geolocation());
        }
        Geolocation geolocation = address.getGeolocation();
        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        if (hasGeolocation) {
            geolocation.setLatitude(modifyAddressDto.getLatitude());
            geolocation.setLongitude(modifyAddressDto.getLongitude());
            geolocation.setAccuracyM(modifyAddressDto.getAccuracyM());
        } else {
            geolocation.setLatitude(null);
            geolocation.setLongitude(null);
            geolocation.setAccuracyM(null);
        }
        address.setTitle(modifyAddressDto.getTitle());
        address.setStreetName(modifyAddressDto.getStreetName());
        address.setStreetName2(modifyAddressDto.getStreetName2());
        address.setHouseNumber(modifyAddressDto.getHouseNumber());
        address.setAdditionalInfo(modifyAddressDto.getAdditionalInfo());
        address.updateDateModified();
        geolocation.updateDateModified();
        geolocationRepository.save(geolocation);
        locationRepository.save(address);
        userRepository.save(user);
    }

    public UUID authenticateByUsername(UsernameAuthenticateDto authenticateDto) throws TakeawayException {
        // validate
        validationLogic.validateUsername(authenticateDto.getUsername());
        validationLogic.validatePassword(authenticateDto.getPassword());

        // business
        String hashedPassword = getHashedPassword(authenticateDto.getPassword());
        Optional<User> optionalUser = userRepository.findByUsernamePassword(authenticateDto.getUsername(), hashedPassword);
        if (optionalUser.isEmpty()) {
            throw new UserOrPasswordWrongException();
        }
        return optionalUser.get().getId();
    }

    public UUID authenticateByEmail(EmailAuthenticateDto authenticateDto) throws TakeawayException {
        // validate
        validationLogic.validateEmail(authenticateDto.getEmail());
        validationLogic.validatePassword(authenticateDto.getPassword());

        // business
        String hashedPassword = getHashedPassword(authenticateDto.getPassword());
        Optional<User> optionalUser = userRepository.findByEmailPassword(authenticateDto.getEmail(), hashedPassword);
        if (optionalUser.isEmpty()) {
            throw new UserOrPasswordWrongException();
        }
        return optionalUser.get().getId();
    }

    public UUID createNewItem(UUID userId, CreateItemDto data) throws TakeawayException {
        return null;
    }

    public void updateItemDetails(UUID userId, UpdateItemDto data) throws TakeawayException {

    }

    public void deactivateItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void renewItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void deleteItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void changeItemAttachmentOrder(UUID userId, ChangeItemAttachmentOrderDto data) throws TakeawayException {

    }

    public void addAttachmentToItem(UUID userId, CreateAttachmentDto attachmentDto) throws TakeawayException {

    }

    public void deleteAttachmentFromItem(UUID userId, UUID itemId, UUID attachmentId) throws TakeawayException {

    }
}
