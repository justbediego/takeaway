package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.dto.ChangePasswordDto;
import com.takeaway.takeaway.business.dto.EmailAuthenticateDto;
import com.takeaway.takeaway.business.dto.UsernameAuthenticateDto;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController extends BaseController {

    private final AuthenticationLogic authenticationLogic;

    public AuthenticationController(AuthenticationLogic authenticationLogic) {
        this.authenticationLogic = authenticationLogic;
    }

    @PatchMapping(path = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto data) throws TakeawayException {
        authenticationLogic.changePassword(userID, ChangePasswordDto.fromOutside(data));
    }

    @PostMapping(path = "/authenticateUsername")
    public void authenticateUsername(@RequestBody UsernameAuthenticateDto data) throws TakeawayException {
        userID = authenticationLogic.authenticateByUsername(UsernameAuthenticateDto.fromOutside(data));
    }

    @PostMapping(path = "/authenticateEmail")
    public void authenticateEmail(@RequestBody EmailAuthenticateDto data) throws TakeawayException {
        userID = authenticationLogic.authenticateByEmail(EmailAuthenticateDto.fromOutside(data));
    }
}
