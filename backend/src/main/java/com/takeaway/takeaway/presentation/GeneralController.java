package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.GeneralLogic;
import com.takeaway.takeaway.business.dto.GetCountryCodesDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class GeneralController {

    private final GeneralLogic generalLogic;

    public GeneralController(GeneralLogic generalLogic) {
        this.generalLogic = generalLogic;
    }

    @GetMapping(path = "/getCountryCodes")
    public GetCountryCodesDto getCountryCodes() {
        return generalLogic.getCountryCodes();
    }

}
