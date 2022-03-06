package com.takeaway.takeaway.business;

import com.google.gson.Gson;
import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.ExceptionEntities;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class GuestLogic {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemRepository itemRepository;

    public GuestLogic(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
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

    public GetItemsDto getItems(GetItemsFiltersDto filtersDto, UserLanguages language) throws TakeawayException {
        return null;
    }

    public GetItemDto getItem(UUID itemId, UserLanguages language) throws TakeawayException {
        Optional<ItemRepository.GetPublicItemResponse> publicItem = itemRepository.getPublicItem(itemId, language.ordinal());
        if (publicItem.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.ITEM,
                    itemId.toString()
            );
        }
        ItemRepository.GetPublicItemResponse dbItem = publicItem.get();
        List<String> pictureUrls = null;
        if (dbItem.getImage_Urls_Json() != null) {
            Gson gson = new Gson();
            pictureUrls = gson.fromJson(dbItem.getImage_Urls_Json(), List.class);
        } else {
            pictureUrls = null;
        }
        return GetItemDto.builder()
                .id(itemId)
                .title(dbItem.getTitle())
                .description(dbItem.getDescription())
                .publishStart(dbItem.getPublish_start())
                .firstName(dbItem.getFirst_name())
                .lastName(dbItem.getLast_name())
                .publicEmail(dbItem.getPublic_email())
                .publicPhoneNumber(dbItem.getPublic_phone_number())
                .itemCategoryId(UUID.fromString(dbItem.getItem_category_id()))
                .countryId(UUID.fromString(dbItem.getCountry_id()))
                .countryName(dbItem.getCountry_name())
                .stateId(UUID.fromString(dbItem.getState_id()))
                .stateName(dbItem.getState_name())
                .cityId(UUID.fromString(dbItem.getCity_id()))
                .cityName(dbItem.getCity_name())
                .pictureUrls(pictureUrls)
                .build();
    }

}
