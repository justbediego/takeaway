package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.dto.GetBasicInfoDto;
import com.takeaway.takeaway.business.dto.UsernameAuthenticateDto;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

// import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class UserControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

    private User first = null;
    private User second = null;
    private String basePath = null;

    @BeforeEach
    void setUp() {
        first = new User();
        first.setId(UUID.randomUUID());
        first.setUsername("first");
        first.setEmail("first@test.com");
        // testpassword
        first.setHashedPassword("a4cb35912220081cde4edfb9e220b815956ff3f4cd476914d3346abb5f52700c");
        second = new User();
        second.setId(UUID.randomUUID());
        second.setUsername("second");
        second.setEmail("second@test.com");
        userRepository.save(first);
        userRepository.save(second);
        basePath = String.format("http://localhost:%d/user/", port);
        restTemplate.postForObject(
                String.format("%s%s", basePath, "authenticateUsername"),
                UsernameAuthenticateDto.builder()
                        .username("first")
                        .password("testpassword")
                        .build(),
                Void.class
        );
    }

    @Test
    void updateBasicInfo() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void modifyAddress() {
    }

    @Test
    void updateProfilePicture() {
    }

    @Test
    void getBasicInfo() {
        String apiPath = String.format("%s%s", basePath, "getBasicInfo");
        GetBasicInfoDto getBasicInfoDto = restTemplate.getForObject(apiPath, GetBasicInfoDto.class);
    }
}