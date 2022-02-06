package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.CountryCodeDto;
import com.takeaway.takeaway.business.dto.GetCountryCodesDto;
import com.takeaway.takeaway.dataaccess.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class GeneralLogic {

    private final CountryRepository countryRepository;

    public GeneralLogic(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public GetCountryCodesDto getCountryCodes() {
        List<CountryCodeDto> countries = countryRepository.findAll().stream()
                .filter(c -> StringUtils.isNotBlank(c.getCountryCode()))
                .map(c -> CountryCodeDto.builder()
                        .countryName(c.getName())
                        .countryCode(c.getCountryCode())
                        .build())
                .collect(Collectors.toList());
        return GetCountryCodesDto.builder()
                .countries(countries)
                .build();
    }
}
