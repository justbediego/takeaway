package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class UserControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    private String basePath = null;

    @Test
    void oneGiantTest() {
        // init
        basePath = String.format("http://localhost:%d/user/", port);
        final User first = new User();
        first.setId(UUID.randomUUID());
        first.setUsername("first");
        first.setEmail("first@test.com");
        // testpassword
        first.setHashedPassword("a4cb35912220081cde4edfb9e220b815956ff3f4cd476914d3346abb5f52700c");
        final User second = new User();
        second.setId(UUID.randomUUID());
        second.setUsername("second");
        second.setEmail("second@test.com");
        userRepository.save(first);
        userRepository.save(second);
        restTemplate.postForObject(
                String.format("%s%s", basePath, "authenticateUsername"),
                UsernameAuthenticateDto.builder()
                        .username("first")
                        .password("testpassword")
                        .build(),
                Void.class
        );

        updateEmail_InvalidEmail();
        updateEmail_SameException();
        updateEmail_InUseException();
        updateEmail();
        updateUsername_InvalidUsername();
        updateUsername_SameException();
        updateUsername_InUseException();
        updateUsername();
        updateBasicInfo_InvalidCountryCode();
        updateBasicInfo_InvalidPhoneNumber();
        updateBasicInfo_InvalidFirstName();
        updateBasicInfo_InvalidLastName();
        updateBasicInfo();
        getBasicInfo();
    }

    void updateEmail_InvalidEmail() {
        String apiPath = String.format("%s%s", basePath, "updateEmail");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateEmailDto.builder()
                            .email("funny no way")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidEmailException"));
    }

    void updateEmail_SameException() {
        String apiPath = String.format("%s%s", basePath, "updateEmail");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateEmailDto.builder()
                            .email("first@test.com")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("NewEmailSameAsOldException"));
    }

    void updateEmail_InUseException() {
        String apiPath = String.format("%s%s", basePath, "updateEmail");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateEmailDto.builder()
                            .email("second@test.com")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("EmailAlreadyInUseException"));
    }

    void updateEmail() {
        String apiPath = String.format("%s%s", basePath, "updateEmail");
        restTemplate.patchForObject(
                apiPath,
                UpdateEmailDto.builder()
                        .email("firstNew@test.com")
                        .build(),
                Void.class
        );
    }

    void updateUsername_InvalidUsername() {
        String apiPath = String.format("%s%s", basePath, "updateUsername");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateUsernameDto.builder()
                            .username("funny no way")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidUsernameException"));
    }

    void updateUsername_SameException() {
        String apiPath = String.format("%s%s", basePath, "updateUsername");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateUsernameDto.builder()
                            .username("first")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("NewUsernameSameAsOldException"));
    }

    void updateUsername_InUseException() {
        String apiPath = String.format("%s%s", basePath, "updateUsername");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateUsernameDto.builder()
                            .username("second")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("UsernameAlreadyInUseException"));
    }

    void updateUsername() {
        String apiPath = String.format("%s%s", basePath, "updateUsername");
        restTemplate.patchForObject(
                apiPath,
                UpdateUsernameDto.builder()
                        .username("firstNew")
                        .build(),
                Void.class
        );
    }

    void getBasicInfo() {
        String apiPath = String.format("%s%s", basePath, "getBasicInfo");
        GetBasicInfoDto getBasicInfoDto = restTemplate.getForObject(apiPath, GetBasicInfoDto.class);
        assertEquals("firstNew@test.com", getBasicInfoDto.getEmail());
        assertEquals("firstNew", getBasicInfoDto.getUsername());
        assertEquals("TestFirst", getBasicInfoDto.getFirstName());
        assertEquals("TestLast", getBasicInfoDto.getLastName());
        assertEquals("+49", getBasicInfoDto.getPhoneNumberCountryCode());
        assertEquals("123456789", getBasicInfoDto.getPhoneNumber());
    }

    void updateBasicInfo_InvalidCountryCode() {
        String apiPath = String.format("%s%s", basePath, "updateBasicInfo");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateBasicInfoDto.builder()
                            .phoneNumberCountryCode("BAD COUNTRY CODE")
                            .phoneNumber("12345678")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidCountryCodeException"));
    }

    void updateBasicInfo_InvalidPhoneNumber() {
        String apiPath = String.format("%s%s", basePath, "updateBasicInfo");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateBasicInfoDto.builder()
                            .phoneNumberCountryCode("+32")
                            .phoneNumber("BAD PHONE NUMBER")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidPhoneNumberException"));
    }

    void updateBasicInfo_InvalidFirstName() {
        String apiPath = String.format("%s%s", basePath, "updateBasicInfo");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateBasicInfoDto.builder()
                            .firstName("F")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidFirstNameException"));
    }

    void updateBasicInfo_InvalidLastName() {
        String apiPath = String.format("%s%s", basePath, "updateBasicInfo");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    UpdateBasicInfoDto.builder()
                            .lastName("F")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidLastNameException"));
    }

    void updateBasicInfo() {
        String apiPath = String.format("%s%s", basePath, "updateBasicInfo");
        HttpClientErrorException takeawayException = null;
        restTemplate.patchForObject(
                apiPath,
                UpdateBasicInfoDto.builder()
                        .firstName("TestFirst")
                        .lastName("TestLast")
                        .phoneNumberCountryCode("+49")
                        .phoneNumber("123456789")
                        .build(),
                Void.class
        );
    }

    void changePassword() {
    }

    void modifyAddress() {
    }

    void updateProfilePicture() {
    }
}