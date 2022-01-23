package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment extends BaseEntity {

    @Column(nullable = false)
    private AttachmentTypes type;

    // used together with securityKey to retrieve the file
    @Column(nullable = false)
    private UUID fileId;

    // used together with fileId to retrieve the file
    @Column(nullable = false)
    private UUID securityKey;

    @Column(nullable = false)
    private Integer fileSize;

    @Column(length = 300, nullable = false)
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_item_id")
    private Item pictureItem;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_id")
    private List<User> profilePictureUser = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_original_id")
    private List<User> profilePictureOriginalUser = new ArrayList<>();
}
