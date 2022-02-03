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
    @JoinColumn(name = "user_from", referencedColumnName = "id")
    private User userFrom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_to", referencedColumnName = "id")
    private User userTo;


}
