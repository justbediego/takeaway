package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.geo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT x FROM User x WHERE x.username = ?1 AND x.hashedPassword = ?2")
    Optional<User> findByUsernamePassword(String username, String hashedPassword);

    @Query("SELECT x FROM User x WHERE x.email = ?1 AND x.hashedPassword = ?2")
    Optional<User> findByEmailPassword(String email, String hashedPassword);
}