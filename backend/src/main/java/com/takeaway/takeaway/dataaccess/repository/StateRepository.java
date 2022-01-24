package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.geo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<State, UUID> {
    @Query("SELECT x FROM State x WHERE x.id = ?1 AND x.country.id = ?2")
    Optional<State> findByIdAndCountry(UUID id, UUID countryId);
}