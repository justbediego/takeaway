package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT x FROM User x WHERE lower(x.username) = lower(?1) AND lower(x.hashedPassword) = lower(?2)")
    Optional<User> findByUsernamePassword(String username, String hashedPassword);

    @Query("SELECT x FROM User x WHERE lower(x.email) = lower(?1) AND lower(x.hashedPassword) = lower(?2)")
    Optional<User> findByEmailPassword(String email, String hashedPassword);

    @Query("SELECT x FROM User x WHERE lower(x.email) = lower(?1)")
    Optional<User> findByEmail(String email);

    @Query("SELECT x FROM User x WHERE lower(x.username) = lower(?1)")
    Optional<User> findByUsername(String username);
}