package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.enums.GenderTypes;
import com.takeaway.takeaway.dataaccess.model.geo.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "email_idx", columnList = "email", unique = true),
        @Index(name = "username_idx", columnList = "username", unique = true)
})
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 5)
    private String phoneNumberCountryCode;

    @Column(length = 200)
    private String username;

    // a user might not have a password set
    @Column(length = 300)
    private String hashedPassword;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column
    private GenderTypes gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_address_id")
    private Location address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_id")
    private Attachment profilePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_original_id")
    private Attachment profilePictureOriginal;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private List<Item> items = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private List<DirectMessage> directMessagesFrom = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private List<DirectMessage> directMessagesTo = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<ItemReport> itemReports = new ArrayList<>();
}
