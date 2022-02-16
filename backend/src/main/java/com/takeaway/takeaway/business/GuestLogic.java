package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.CountryCodeDto;
import com.takeaway.takeaway.business.dto.GetCountryCodesDto;
import com.takeaway.takeaway.business.dto.GetItemCategoriesDto;
import com.takeaway.takeaway.business.dto.ItemCategoryDto;
import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.repository.CountryRepository;
import com.takeaway.takeaway.dataaccess.repository.ItemCategoryRepository;
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
    private final ItemCategoryRepository itemCategoryRepository;

    public GuestLogic(CountryRepository countryRepository, ItemCategoryRepository itemCategoryRepository) {
        this.countryRepository = countryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public GetCountryCodesDto getCountryCodes(UserLanguages language) {
        List<CountryCodeDto> countries = countryRepository.findAll().stream()
                .filter(c -> StringUtils.isNotBlank(c.getCountryCode()))
                .map(c -> CountryCodeDto.builder()
                        .countryName(c.getTranslations().stream()
                                .filter(t -> t.getLanguage().equals(language))
                                .map(t -> t.getTitle())
                                .findFirst()
                                .orElseThrow())
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
                                .map(t -> t.getTitle())
                                .findFirst()
                                .orElseThrow())
                        .categoryCode(ic.getCategoryCode())
                        .build())
                .collect(Collectors.toList());
        return GetItemCategoriesDto.builder()
                .categories(itemCategories)
                .build();
    }


}
