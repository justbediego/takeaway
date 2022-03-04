package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

}