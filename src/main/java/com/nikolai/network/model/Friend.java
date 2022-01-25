package com.nikolai.network.model;

import com.nikolai.network.enums.StatusFriends;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "friend")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusFriends statusFriends;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    private User firstUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    private User secondUser;



}
