package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.dto.ChangePasswordDto;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController extends BaseController {

    private final AuthenticationLogic authenticationLogic;

    public AuthenticationController(AuthenticationLogic authenticationLogic) {
        super(authenticationLogic);
        this.authenticationLogic = authenticationLogic;
    }

    @PatchMapping(path = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto data) throws TakeawayException {
        authenticationLogic.changePassword(getUserId(), ChangePasswordDto.fromOutside(data));
    }
}
