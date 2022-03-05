package com.takeaway.takeaway.dataaccess.model;

import com.takeaway.takeaway.dataaccess.model.enums.ActionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "action_histories")
@EqualsAndHashCode(callSuper = true)
public class ActionHistory extends BaseEntity {

    @Column(nullable = false)
    private ActionTypes type;

    @Column(length = 200)
    private String userEmail;

    @Column(length = 1000)
    private String payload;

}
