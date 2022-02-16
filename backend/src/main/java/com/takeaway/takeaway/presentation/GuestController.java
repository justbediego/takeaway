package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.GuestLogic;
import com.takeaway.takeaway.business.dto.GetCountryCodesDto;
import com.takeaway.takeaway.business.dto.GetItemCategoriesDto;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/guest")
public class GuestController extends BaseController {

    private final GuestLogic guestLogic;

    public GuestController(GuestLogic guestLogic, AuthenticationLogic authenticationLogic) {
        super(authenticationLogic);
        this.guestLogic = guestLogic;
    }

    @GetMapping(path = "/getCountryCodes")
    public GetCountryCodesDto getCountryCodes(@RequestParam UserLanguages language) {
        return guestLogic.getCountryCodes(language);
    }

    @GetMapping(path = "/getItemCategories")
    public GetItemCategoriesDto getItemCategories(@RequestParam(required = false) UUID parentId, @RequestParam UserLanguages language) {
        return guestLogic.getItemCategories(parentId, language);
    }
}
