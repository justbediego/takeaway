package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.geo.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
    @Query("SELECT x.countryCode FROM Country x WHERE x.countryCode is not null")
    List<String> findAllCountryCodes();
}