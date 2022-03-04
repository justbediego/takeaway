package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.business.dto.StateDto;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import com.takeaway.takeaway.dataaccess.model.geo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<State, UUID> {
    @Query("SELECT x FROM State x WHERE x.id = ?1 AND x.country.id = ?2")
    Optional<State> findByIdAndCountry(UUID id, UUID countryId);

    @Query("""
            SELECT new com.takeaway.takeaway.business.dto.StateDto(
                s.id,
                CASE WHEN trs.id is null THEN s.englishName else trs.value END
            ) FROM State s
            LEFT JOIN s.translations trs ON trs.language = ?2
            WHERE s.country.id =?1""")
    List<StateDto> getStates(UUID countryId, UserLanguages language);
}