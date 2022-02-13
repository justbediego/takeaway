package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.GuesLogic;
import com.takeaway.takeaway.business.dto.GetCountryCodesDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController extends BaseController{

    private final GuesLogic guesLogic;

    public GuestController(GuesLogic guesLogic) {
        this.guesLogic = guesLogic;
    }

    @GetMapping(path = "/getCountryCodes")
    public GetCountryCodesDto getCountryCodes() {
        return guesLogic.getCountryCodes();
    }

}
