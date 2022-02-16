package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.geo.City;
import com.takeaway.takeaway.dataaccess.model.geo.State;
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

    @Query("SELECT x FROM City x WHERE x.state.id = ?1")
    List<City> findByStateId(UUID stateId);
}