package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.business.dto.CityDto;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.model.geo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    @Query("SELECT x FROM City x WHERE x.id = ?1 AND x.state.id = ?2")
    Optional<City> findByIdAndState(UUID id, UUID stateId);

    @Query("""
            SELECT new com.takeaway.takeaway.business.dto.CityDto(
                c.id,
                CASE WHEN trs.id is null THEN c.englishName else trs.value END
            ) FROM City c
            LEFT JOIN c.translations trs ON trs.language = ?2
            WHERE c.state.id =?1""")
    List<CityDto> getCities(UUID stateId, UserLanguages language);
}