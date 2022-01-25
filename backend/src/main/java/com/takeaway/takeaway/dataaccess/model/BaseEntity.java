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

    @Column(nullable = false)
    private Date dateModified;

    protected BaseEntity() {
        this.dateCreated = new Date();
        this.dateModified = new Date();
    }

    public void updateDateModified() {
        this.dateModified = new Date();
    }
}
