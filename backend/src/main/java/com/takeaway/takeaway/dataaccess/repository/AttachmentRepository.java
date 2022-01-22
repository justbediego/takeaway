package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    @Query("SELECT x FROM Attachment x WHERE x.id = ?1 AND x.securityKey = ?2")
    Optional<Attachment> findByIdAndKey(UUID id, UUID key);
}