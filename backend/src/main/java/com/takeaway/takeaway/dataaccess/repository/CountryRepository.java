package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.business.dto.CountryCodeDto;
import com.takeaway.takeaway.business.dto.CountryDto;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.model.geo.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    @Query("""
            SELECT new com.takeaway.takeaway.business.dto.CountryCodeDto(
                CASE WHEN trs.id is null THEN c.englishName else trs.value END,
                c.countryCode
            ) FROM Country c
            LEFT JOIN c.translations trs ON trs.language = ?1""")
    List<CountryCodeDto> getCountryCodes(UserLanguages language);

    @Query("""
            SELECT new com.takeaway.takeaway.business.dto.CountryDto(
                c.id,
                CASE WHEN trs.id is null THEN c.englishName else trs.value END
            ) FROM Country c
            LEFT JOIN c.translations trs ON trs.language = ?1""")
    List<CountryDto> getCountries(UserLanguages language);

}