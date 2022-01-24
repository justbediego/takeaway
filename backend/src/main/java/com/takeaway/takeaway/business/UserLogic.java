package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.*;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import com.takeaway.takeaway.dataaccess.repository.AttachmentRepository;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class UserLogic {

    @Value("${spring.application.profilePictureSize}")
    private Integer profilePictureSize;

    private final AttachmentLogic attachmentLogic;

    private final UserRepository userRepository;

    private final AttachmentRepository attachmentRepository;

    public UserLogic(AttachmentLogic attachmentLogic, UserRepository userRepository, AttachmentRepository attachmentRepository) {
        this.attachmentLogic = attachmentLogic;
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
    }

    private User findUserOrThrow(UUID userId) throws TakeawayException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFound("User", userId);
        }
        return optionalUser.get();
    }

    private String getHashedPassword(String password){
        // todo: fix hashed
        return "Test";
    }

    @Transactional
    public void updateProfilePicture(UUID userId, CreateAttachmentDto attachmentDto) throws TakeawayException {
        User user = findUserOrThrow(userId);
        byte[] originalData = attachmentLogic.prepareImage(attachmentDto.getFileData(), null);
        byte[] smallData = attachmentLogic.prepareImage(attachmentDto.getFileData(), profilePictureSize);
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
        if(optionalOriginal.isEmpty() || optionalSmall.isEmpty()){
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
        userRepository.save(user);
    }

    public GetBasicInfoDto getBasicInfo(UUID userId) throws TakeawayException {
        User user = findUserOrThrow(userId);
        Attachment profilePicture = user.getProfilePicture();
        return GetBasicInfoDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .phoneNumberCountryCode(user.getPhoneNumberCountryCode())
                .profilePictureId(profilePicture == null ? null : profilePicture.getId())
                .profilePictureKey(profilePicture == null ? null : profilePicture.getSecurityKey())
                .build();
    }

    public void updateBasicInfo(UUID userId, UpdateBasicInfoDto updateBasicInfoDto) throws TakeawayException {
        User user = findUserOrThrow(userId);
        // todo: validation
        user.setFirstName(updateBasicInfoDto.getFirstName());
        user.setLastName(updateBasicInfoDto.getLastName());
        user.setPhoneNumber(updateBasicInfoDto.getPhoneNumber());
        user.setPhoneNumberCountryCode(updateBasicInfoDto.getPhoneNumberCountryCode());
        userRepository.save(user);
    }

    public void updateUsername(UUID userId, UpdateUsernameDto updateUsernameDto) throws TakeawayException{
        User user = findUserOrThrow(userId);
        // todo: validation
        user.setUsername(updateUsernameDto.getUsername());
        userRepository.save(user);
    }

    public void updateEmail(UUID userId, UpdateEmailDto updateEmailDto) throws TakeawayException{
        User user = findUserOrThrow(userId);
        // todo: validation
        user.setEmail(updateEmailDto.getEmail());
        userRepository.save(user);
    }

    public void changePassword(UUID userId, ChangePasswordDto changePasswordDto) throws TakeawayException{
        User user = findUserOrThrow(userId);
        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordVerify())){
            throw new VerifyPasswordException();
        }
        final String hashedOldPassword = getHashedPassword(changePasswordDto.getOldPassword());
        if(!hashedOldPassword.equals(user.getHashedPassword())){
            throw new WrongPasswordException();
        }
        // todo: validation
        user.setHashedPassword(getHashedPassword(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }
}
