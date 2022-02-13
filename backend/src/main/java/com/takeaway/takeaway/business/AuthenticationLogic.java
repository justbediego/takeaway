package com.takeaway.takeaway.business;

import com.google.common.hash.Hashing;
import com.takeaway.takeaway.business.dto.ChangePasswordDto;
import com.takeaway.takeaway.business.dto.EmailAuthenticateDto;
import com.takeaway.takeaway.business.dto.UsernameAuthenticateDto;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.business.exception.UserOrPasswordWrongException;
import com.takeaway.takeaway.business.exception.VerifyPasswordException;
import com.takeaway.takeaway.business.exception.WrongPasswordException;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

    public AuthenticationLogic(ValidationLogic validationLogic, UserRepository userRepository) {
        this.validationLogic = validationLogic;
        this.userRepository = userRepository;
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
}
