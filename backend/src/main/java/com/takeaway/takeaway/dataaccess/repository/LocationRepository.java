package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.geo.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Query("SELECT x FROM Location x WHERE (x.ownerUser IS NULL OR x.ownerUser.id = ?1) AND x.id = ?2")
    Optional<Location> findByUser(UUID userId, UUID id);
}