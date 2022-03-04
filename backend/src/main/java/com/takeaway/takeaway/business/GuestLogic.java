package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.Item;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.repository.CityRepository;
import com.takeaway.takeaway.dataaccess.repository.CountryRepository;
import com.takeaway.takeaway.dataaccess.repository.ItemCategoryRepository;
import com.takeaway.takeaway.dataaccess.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class GuestLogic {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ValidationLogic validationLogic;

    public GuestLogic(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, ItemCategoryRepository itemCategoryRepository, ValidationLogic validationLogic) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.validationLogic = validationLogic;
    }

    public GetCountryCodesDto getCountryCodes(UserLanguages language) {
        return GetCountryCodesDto.builder()
                .countries(countryRepository.getCountryCodes(language))
                .build();
    }

    public GetItemCategoriesDto getItemCategories(UserLanguages language) {
        return GetItemCategoriesDto.builder()
                .categories(itemCategoryRepository.getItemCategories(language))
                .build();
    }

    public GetCountriesDto getCountries(UserLanguages language) {
        return GetCountriesDto.builder()
                .countries(countryRepository.getCountries(language))
                .build();
    }

    public GetStatesDto getStates(UUID countryId, UserLanguages language) {
        return GetStatesDto.builder()
                .countryId(countryId)
                .states(stateRepository.getStates(countryId, language))
                .build();
    }

    public GetCitiesDto getCities(UUID stateId, UserLanguages language) {
        return GetCitiesDto.builder()
                .stateId(stateId)
                .cities(cityRepository.getCities(stateId, language))
                .build();
    }

    public GetItemsDto getItems(GetItemsFiltersDto filtersDto) {
        return null;
    }

    public GetSingleItemDto getItem(UUID itemId) throws TakeawayException {
        Item item = validationLogic.validateGetItemById(itemId);
        return GetSingleItemDto.builder()
                .imageUrls(item.getPictures().stream()
                        .map(Attachment::getMediaLink)
                        .toList())
                .build();
    }

}
