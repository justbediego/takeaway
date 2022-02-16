package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.dataaccess.model.DataTranslation;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.model.geo.City;
import com.takeaway.takeaway.dataaccess.model.geo.Country;
import com.takeaway.takeaway.dataaccess.model.geo.State;
import com.takeaway.takeaway.dataaccess.repository.CityRepository;
import com.takeaway.takeaway.dataaccess.repository.CountryRepository;
import com.takeaway.takeaway.dataaccess.repository.StateRepository;
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

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;


    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    private String basePath = null;
    private UUID countryId, stateId, cityId, badStateId;

    @Test
    void oneGiantTest() {
        // init
        basePath = String.format("http://localhost:%d/user/", port);
        initUsers();
        initLocation();

        // tests
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
        changePassword_InvalidPassword();
        changePassword_VerifyPassword();
        changePassword_WrongPassword();
        changePassword();
        modifyAddress_InvalidGeolocation();
        modifyAddress_InvalidTitle();
        modifyAddress_BadState();
        modifyAddress_InvalidAddress();
        modifyAddress();
        getBasicInfo();
    }

    void initUsers() {
        final User first = new User();
        first.setUsername("first");
        first.setEmail("first@test.com");
        // testPassword@123
        first.setHashedPassword("01940ac311221054a4184f415ac82a6e94ed5664c0b1d426088a07336d57f6ec");
        final User second = new User();
        second.setUsername("second");
        second.setEmail("second@test.com");
        userRepository.save(first);
        userRepository.save(second);
        // todo: fix tests
//        restTemplate.postForObject(
//                String.format("%s%s", basePath, "authenticateUsername"),
//                UsernameAuthenticateDto.builder()
//                        .username("first")
//                        .password("testPassword@123")
//                        .build(),
//                Void.class
//        );
    }

    void initLocation() {
        Country c1 = new Country();
        DataTranslation c1T = new DataTranslation();
        c1T.setTitle("CNT1");
        c1.getTranslations().add(c1T);
        Country c2 = new Country();
        DataTranslation c2T = new DataTranslation();
        c2T.setTitle("CNT2");
        c2.getTranslations().add(c2T);
        countryRepository.save(c1);
        countryRepository.save(c2);

        State s1 = new State();
        DataTranslation s1T = new DataTranslation();
        s1T.setTitle("CNT1_S1");
        s1.getTranslations().add(s1T);
        State s2 = new State();
        DataTranslation s2T = new DataTranslation();
        s2T.setTitle("CNT1_S2");
        s2.getTranslations().add(s2T);
        State s3 = new State();
        DataTranslation s3T = new DataTranslation();
        s3T.setTitle("CNT2_S1");
        s3.getTranslations().add(s3T);
        s1.setCountry(c1);
        s2.setCountry(c1);
        s3.setCountry(c2);
        stateRepository.save(s1);
        stateRepository.save(s2);
        stateRepository.save(s3);

        City ct1 = new City();
        DataTranslation ct1T = new DataTranslation();
        ct1T.setTitle("CNT1_S1_CT1");
        ct1.getTranslations().add(ct1T);
        City ct2 = new City();
        DataTranslation ct2T = new DataTranslation();
        ct2T.setTitle("CNT1_S1_CT2");
        ct2.getTranslations().add(ct2T);
        City ct3 = new City();
        DataTranslation ct3T = new DataTranslation();
        ct3T.setTitle("CNT1_S2_CT1");
        ct3.getTranslations().add(ct3T);
        City ct4 = new City();
        DataTranslation ct4T = new DataTranslation();
        ct4T.setTitle("CNT2_S1_CT1");
        ct4.getTranslations().add(ct4T);
        ct1.setState(s1);
        ct2.setState(s1);
        ct3.setState(s2);
        ct4.setState(s3);
        cityRepository.save(ct1);
        cityRepository.save(ct2);
        cityRepository.save(ct3);
        cityRepository.save(ct4);
        countryId = c1.getId();
        stateId = s1.getId();
        cityId = ct1.getId();
        badStateId = s3.getId();
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
                            .email("firST@test.com")
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
                            .email("seCOnd@test.com")
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
                        .email("   firstNew@test.com   ")
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
                            .username("fiRst")
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
                            .username("seCoNd")
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
                        .username(" firstNew ")
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
        restTemplate.patchForObject(
                apiPath,
                UpdateBasicInfoDto.builder()
                        .firstName(" TestFirst ")
                        .lastName(" TestLast ")
                        .phoneNumberCountryCode(" +49 ")
                        .phoneNumber(" 123456789 ")
                        .build(),
                Void.class
        );
    }

    void changePassword_InvalidPassword() {
        String apiPath = String.format("%s%s", basePath, "changePassword");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ChangePasswordDto.builder()
                            .newPassword("Fun")
                            .newPasswordVerify("Fun")
                            .oldPassword("testPassword@123")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidPasswordException"));
    }

    void changePassword_VerifyPassword() {
        String apiPath = String.format("%s%s", basePath, "changePassword");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ChangePasswordDto.builder()
                            .newPassword("Fun@1234567")
                            .newPasswordVerify("Fun@1234568")
                            .oldPassword("testPassword@123")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("VerifyPasswordException"));
    }

    void changePassword_WrongPassword() {
        String apiPath = String.format("%s%s", basePath, "changePassword");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ChangePasswordDto.builder()
                            .newPassword("Fun@1234567")
                            .newPasswordVerify("Fun@1234567")
                            .oldPassword("testPassword@124")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("WrongPasswordException"));
    }

    void changePassword() {
        String apiPath = String.format("%s%s", basePath, "changePassword");
        restTemplate.patchForObject(
                apiPath,
                ChangePasswordDto.builder()
                        .newPassword("Fun@1234567")
                        .newPasswordVerify("Fun@1234567")
                        .oldPassword("testPassword@123")
                        .build(),
                Void.class
        );
    }


    void modifyAddress_InvalidGeolocation() {
        String apiPath = String.format("%s%s", basePath, "modifyAddress");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ModifyLocationDto.builder()
                            .latitude(500.0)
                            .longitude(20.0)
                            .title("my-title's test")
                            .streetName("Street name")
                            .streetName2("Street name 2")
                            .countryId(countryId)
                            .stateId(stateId)
                            .cityId(cityId)
                            .houseNumber("12 f")
                            .accuracyM(10)
                            .additionalInfo("Additional")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidGeolocationValuesException"));
    }

    void modifyAddress_InvalidTitle() {
        String apiPath = String.format("%s%s", basePath, "modifyAddress");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ModifyLocationDto.builder()
                            .latitude(-80.0)
                            .longitude(20.0)
                            .title("m")
                            .streetName("Street name")
                            .streetName2("Street name 2")
                            .countryId(countryId)
                            .stateId(stateId)
                            .cityId(cityId)
                            .houseNumber("12 f")
                            .accuracyM(10)
                            .additionalInfo("Additional")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidLocationTitleException"));
    }

    void modifyAddress_BadState() {
        String apiPath = String.format("%s%s", basePath, "modifyAddress");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ModifyLocationDto.builder()
                            .latitude(-80.0)
                            .longitude(20.0)
                            .title("my-title's test")
                            .streetName("Street name")
                            .streetName2("Street name 2")
                            .countryId(countryId)
                            .stateId(badStateId)
                            .cityId(cityId)
                            .houseNumber("12 f")
                            .accuracyM(10)
                            .additionalInfo("Additional")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("EntityNotFound"));
        assertEquals(true, takeawayException.getMessage().contains("STATE"));
    }

    void modifyAddress_InvalidAddress() {
        String apiPath = String.format("%s%s", basePath, "modifyAddress");
        HttpClientErrorException takeawayException = null;
        try {
            restTemplate.patchForObject(
                    apiPath,
                    ModifyLocationDto.builder()
                            .latitude(-80.0)
                            .longitude(20.0)
                            .title("my-title's test")
                            .streetName("S")
                            .streetName2("Street name 2")
                            .countryId(countryId)
                            .stateId(stateId)
                            .cityId(cityId)
                            .houseNumber("12 f")
                            .accuracyM(10)
                            .additionalInfo("Additional")
                            .build(),
                    Void.class
            );
        } catch (HttpClientErrorException ex) {
            takeawayException = ex;
        }
        assertNotNull(takeawayException);
        assertEquals(true, takeawayException.getMessage().contains("InvalidAddressException"));
    }

    void modifyAddress() {
        String apiPath = String.format("%s%s", basePath, "modifyAddress");
        restTemplate.patchForObject(
                apiPath,
                ModifyLocationDto.builder()
                        .latitude(-80.0)
                        .longitude(20.0)
                        .title("my-title's test")
                        .streetName("Street name")
                        .streetName2("Street name 2")
                        .countryId(countryId)
                        .stateId(stateId)
                        .cityId(cityId)
                        .houseNumber("12 f")
                        .accuracyM(10)
                        .additionalInfo("Additional")
                        .build(),
                Void.class
        );
    }

    void updateProfilePicture() {
    }
}