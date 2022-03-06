package com.takeaway.takeaway.presentation;

import com.takeaway.takeaway.business.AuthenticationLogic;
import com.takeaway.takeaway.business.GuestLogic;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import org.springframework.web.bind.annotation.*;

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
    public GetItemCategoriesDto getItemCategories(@RequestParam UserLanguages language) {
        return guestLogic.getItemCategories(language);
    }

    @GetMapping(path = "/getCountries")
    public GetCountriesDto getCountries(@RequestParam UserLanguages language) {
        return guestLogic.getCountries(language);
    }

    @GetMapping(path = "/getStates")
    public GetStatesDto getStates(@RequestParam UUID countryId, @RequestParam UserLanguages language) {
        return guestLogic.getStates(countryId, language);
    }

    @GetMapping(path = "/getCities")
    public GetCitiesDto getCities(@RequestParam UUID stateId, @RequestParam UserLanguages language) {
        return guestLogic.getCities(stateId, language);
    }

    @GetMapping(path = "/getItems")
    public GetItemsDto getItems(@RequestParam GetItemsFiltersDto filtersDto, @RequestParam UserLanguages language) throws TakeawayException {
        return guestLogic.getItems(GetItemsFiltersDto.fromOutside(filtersDto), language);
    }

    @GetMapping(path = "/getItem/{itemId}")
    public GetItemDto getItem(@PathVariable UUID itemId, @RequestParam UserLanguages language) throws TakeawayException {
        return guestLogic.getItem(itemId, language);
    }

}
