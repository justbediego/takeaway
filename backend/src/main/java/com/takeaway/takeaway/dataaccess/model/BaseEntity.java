package com.takeaway.takeaway.dataaccess.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Date dateCreated;

    protected BaseEntity() {
        this.dateCreated = new Date();
    }
}
