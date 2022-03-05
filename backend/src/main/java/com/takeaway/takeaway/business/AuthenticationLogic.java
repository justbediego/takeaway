package com.takeaway.takeaway.business;

import com.google.common.hash.Hashing;
import com.takeaway.takeaway.business.dto.ChangePasswordDto;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.ActionTypes;
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
public class AuthenticationLogic {

    @Value("${spring.application.passwordHashSalt}")
    private String passwordHashSalt;

    private final ValidationLogic validationLogic;
    private final UserRepository userRepository;
    private final ActionHistoryLogic actionHistoryLogic;

    public AuthenticationLogic(ValidationLogic validationLogic, UserRepository userRepository, ActionHistoryLogic actionHistoryLogic) {
        this.validationLogic = validationLogic;
        this.userRepository = userRepository;
        this.actionHistoryLogic = actionHistoryLogic;
    }

    private String getHashedPassword(String password) {
        return Hashing.sha256()
                .hashString(String.format("%s%s", passwordHashSalt, password), StandardCharsets.UTF_8)
                .toString();
    }

    public void changePassword(UUID userId, ChangePasswordDto changePasswordDto) throws TakeawayException {
        // validation
        validationLogic.validatePassword(changePasswordDto.getNewPassword());
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordVerify())) {
            throw new TakeawayException(ExceptionTypes.VERIFY_PASSWORD);
        }
        final String hashedOldPassword = getHashedPassword(changePasswordDto.getOldPassword());
        User user = validationLogic.validateGetUserById(userId);
        if (!hashedOldPassword.equals(user.getHashedPassword())) {
            throw new TakeawayException(ExceptionTypes.WRONG_PASSWORD);
        }

        // business
        user.setHashedPassword(getHashedPassword(changePasswordDto.getNewPassword()));
        actionHistoryLogic.AddHistoryRecord(ActionTypes.AUTH_CHANGE_PASSWORD);
        userRepository.save(user);
    }

    public UUID authenticateByEmail(String email, String firstName, String lastName) throws TakeawayException {
        // validate
        validationLogic.validateEmail(email);

        // business
        UUID userId;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            userId = optionalUser.get().getId();
        } else {
            String firstNameVerified = null;
            String lastNameVerified = null;
            if (StringUtils.isNotBlank(firstName)) {
                try {
                    validationLogic.validateFirstName(firstName);
                    firstNameVerified = firstName;
                } catch (TakeawayException ex) {
                    log.warn("Couldn't use the first name from Principal");
                }
            }
            if (StringUtils.isNotBlank(lastName)) {
                try {
                    validationLogic.validateLastName(lastName);
                    lastNameVerified = lastName;
                } catch (TakeawayException ex) {
                    log.warn("Couldn't use the last name from Principal");
                }
            }

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(firstNameVerified);
            newUser.setLastName(lastNameVerified);
            userRepository.save(newUser);
            userId = newUser.getId();
        }
        return userId;
    }
}
