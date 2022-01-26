package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.dto.GetBasicInfoDto;
import com.takeaway.takeaway.business.dto.UpdateEmailDto;
import com.takeaway.takeaway.business.dto.UsernameAuthenticateDto;
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

        updateEmail_SameException();
        updateEmail_InUseException();
        updateEmail();
        getBasicInfo();
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

    void getBasicInfo() {
        String apiPath = String.format("%s%s", basePath, "getBasicInfo");
        GetBasicInfoDto getBasicInfoDto = restTemplate.getForObject(apiPath, GetBasicInfoDto.class);
        assertEquals("firstNew@test.com", getBasicInfoDto.getEmail());
        assertEquals("first", getBasicInfoDto.getUsername());
    }

    void updateBasicInfo() {
    }

    void updateUsername() {
    }

    void changePassword() {
    }

    void modifyAddress() {
    }

    void updateProfilePicture() {
    }
}