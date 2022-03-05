package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.ExceptionEntities;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.ActionTypes;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import com.takeaway.takeaway.dataaccess.model.geo.Location;
import com.takeaway.takeaway.dataaccess.repository.GeolocationRepository;
import com.takeaway.takeaway.dataaccess.repository.LocationRepository;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class UserLogic {

    @Value("${spring.application.profilePictureSize}")
    private Integer profilePictureSize;
    private final ValidationLogic validationLogic;
    private final AttachmentLogic attachmentLogic;
    private final ActionHistoryLogic actionHistoryLogic;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final GeolocationRepository geolocationRepository;

    public UserLogic(ValidationLogic validationLogic, AttachmentLogic attachmentLogic, ActionHistoryLogic actionHistoryLogic, UserRepository userRepository, LocationRepository locationRepository, GeolocationRepository geolocationRepository) {
        this.validationLogic = validationLogic;
        this.attachmentLogic = attachmentLogic;
        this.actionHistoryLogic = actionHistoryLogic;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.geolocationRepository = geolocationRepository;
    }

    public void deleteProfilePicture(UUID userId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        if (user.getProfilePicture() == null) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.ATTACHMENT
            );
        }
        if (user.getProfilePictureOriginal() == null) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.ATTACHMENT
            );
        }
        attachmentLogic.removeAttachment(user.getProfilePicture().getId());
        attachmentLogic.removeAttachment(user.getProfilePictureOriginal().getId());
        actionHistoryLogic.AddHistoryRecord(ActionTypes.USER_DELETE_PROFILE_PICTURE);
        userRepository.save(user);
    }

    public void updateProfilePicture(UUID userId, CreateAttachmentDto attachmentDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        byte[] originalData = attachmentLogic.prepareImage(attachmentDto.getFileData(), null);
        byte[] smallData = attachmentLogic.prepareImage(attachmentDto.getFileData(), profilePictureSize);

        // business
        Attachment originalPicture = attachmentLogic.createAttachment(
                CreateAttachmentDto.builder()
                        // not trusting the incoming data
                        .fileData(originalData)
                        .filename(attachmentDto.getFilename())
                        .build(),
                AttachmentTypes.IMAGE);
        Attachment smallPicture = attachmentLogic.createAttachment(
                CreateAttachmentDto.builder()
                        .fileData(smallData)
                        .filename(attachmentDto.getFilename())
                        .build(),
                AttachmentTypes.IMAGE);

        if (user.getProfilePicture() != null) {
            attachmentLogic.removeAttachment(user.getProfilePicture().getId());
        }
        if (user.getProfilePictureOriginal() != null) {
            attachmentLogic.removeAttachment(user.getProfilePictureOriginal().getId());
        }
        user.setProfilePicture(smallPicture);
        user.setProfilePictureOriginal(originalPicture);
        actionHistoryLogic.AddHistoryRecord(ActionTypes.USER_UPDATE_PROFILE_PICTURE);
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
                .profilePictureLink(profilePicture == null ? null : profilePicture.getMediaLink())
                .profilePictureOriginalLink(profilePictureOriginal == null ? null : profilePictureOriginal.getMediaLink())
                .emailIsPublic(user.getEmailIsPublic())
                .phoneNumberIsPublic(user.getPhoneNumberIsPublic())
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
        user.setEmailIsPublic(updateBasicInfoDto.getEmailIsPublic());
        user.setPhoneNumberIsPublic(updateBasicInfoDto.getPhoneNumberIsPublic());
        actionHistoryLogic.AddHistoryRecord(ActionTypes.USER_UPDATE_BASIC_INFO);
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
        actionHistoryLogic.AddHistoryRecord(ActionTypes.USER_UPDATE_USERNAME);
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
        actionHistoryLogic.AddHistoryRecord(ActionTypes.USER_UPDATE_EMAIL);
        userRepository.save(user);
    }

    public void modifyAddress(UUID userId, ModifyLocationDto modifyLocationDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        Location newLocation = validationLogic.validateNewLocation(modifyLocationDto);

        // business
        if (newLocation.getGeolocation() != null) {
            geolocationRepository.save(newLocation.getGeolocation());
        }
        locationRepository.save(newLocation);

        if (user.getAddress() != null) {
            if (user.getAddress().getGeolocation() != null) {
                geolocationRepository.delete(user.getAddress().getGeolocation());
            }
            locationRepository.delete(user.getAddress());
        }
        user.setAddress(newLocation);
        actionHistoryLogic.AddHistoryRecord(ActionTypes.USER_MODIFY_ADDRESS);
        userRepository.save(user);
    }
}
