package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.dataaccess.model.DataTranslation;
import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.repository.CityRepository;
import com.takeaway.takeaway.dataaccess.repository.CountryRepository;
import com.takeaway.takeaway.dataaccess.repository.ItemCategoryRepository;
import com.takeaway.takeaway.dataaccess.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class GuestLogic {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public GuestLogic(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, ItemCategoryRepository itemCategoryRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public GetCountryCodesDto getCountryCodes(UserLanguages language) {
        List<CountryCodeDto> countries = countryRepository.findAll().stream()
                .filter(c -> StringUtils.isNotBlank(c.getCountryCode()))
                .map(c -> CountryCodeDto.builder()
                        .countryName(c.getTranslations().stream()
                                .filter(t -> t.getLanguage().equals(language))
                                .map(DataTranslation::getValue)
                                .findFirst()
                                .orElse(c.getEnglishName()))
                        .countryCode(c.getCountryCode())
                        .build())
                .collect(Collectors.toList());
        return GetCountryCodesDto.builder()
                .countries(countries)
                .build();
    }

    public GetItemCategoriesDto getItemCategories(UUID parentId, UserLanguages languages) {
        List<ItemCategory> categories;
        if (parentId == null) {
            categories = itemCategoryRepository.findRootCategories();
        } else {
            categories = itemCategoryRepository.findByParentId(parentId);
        }
        List<ItemCategoryDto> itemCategories = categories.stream()
                .map(ic -> ItemCategoryDto.builder()
                        .id(ic.getId())
                        .categoryName(ic.getTranslations().stream()
                                .filter(t -> t.getLanguage().equals(languages))
                                .map(DataTranslation::getValue)
                                .findFirst()
                                .orElse(ic.getEnglishName()))
                        .categoryCode(ic.getCategoryCode())
                        .build())
                .collect(Collectors.toList());
        return GetItemCategoriesDto.builder()
                .categories(itemCategories)
                .build();
    }

    public GetCountriesDto getCountries(UserLanguages language) {
        List<CountryDto> countries = countryRepository.findAll().stream()
                .map(c -> CountryDto.builder()
                        .id(c.getId())
                        .name(c.getTranslations().stream()
                                .filter(t -> t.getLanguage().equals(language))
                                .map(DataTranslation::getValue)
                                .findFirst()
                                .orElse(c.getEnglishName()))
                        .build())
                .collect(Collectors.toList());
        return GetCountriesDto.builder()
                .countries(countries)
                .build();
    }

    public GetStatesDto getStates(UUID countryId, UserLanguages language) {
        List<StateDto> states = stateRepository.findByCountryId(countryId).stream()
                .map(s -> StateDto.builder()
                        .id(s.getId())
                        .name(s.getTranslations().stream()
                                .filter(t -> t.getLanguage().equals(language))
                                .map(DataTranslation::getValue)
                                .findFirst()
                                .orElse(s.getEnglishName()))
                        .build())
                .collect(Collectors.toList());
        return GetStatesDto.builder()
                .countryId(countryId)
                .states(states)
                .build();
    }

    public GetCitiesDto getCities(UUID stateId, UserLanguages language) {
        List<CityDto> cities = cityRepository.findByStateId(stateId).stream()
                .map(c -> CityDto.builder()
                        .id(c.getId())
                        .name(c.getTranslations().stream()
                                .filter(t -> t.getLanguage().equals(language))
                                .map(DataTranslation::getValue)
                                .findFirst()
                                .orElse(c.getEnglishName()))
                        .build())
                .collect(Collectors.toList());
        return GetCitiesDto.builder()
                .stateId(stateId)
                .cities(cities)
                .build();
    }

}
