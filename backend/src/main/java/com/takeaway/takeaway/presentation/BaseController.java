package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.exception.ExceptionEntities;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.UUID;

public abstract class BaseController {

    private final AuthenticationLogic authenticationLogic;

    protected BaseController(AuthenticationLogic authenticationLogic) {
        this.authenticationLogic = authenticationLogic;
    }

    protected UUID getUserId() throws TakeawayException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof DefaultOidcUser)) {
            throw new TakeawayException(
                    ExceptionTypes.UNRECOGNIZED,
                    ExceptionEntities.USER,
                    "Unable to get the principal user"
            );
        }
        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
        String email = user.getClaims().get("email").toString();
        String name = user.getClaims().get("name").toString();
        return authenticationLogic.authenticateByEmail(email, name, "");
    }

}
