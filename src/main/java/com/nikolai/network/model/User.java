package com.nikolai.network.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
@Data
public class User extends BaseEntity {

    @Column(name = "first_name", length = 15)
    private String firstName;

    @Column(name = "last_name", length = 15)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", length = 90)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "birthday", columnDefinition = "DATE")
    private String birthday;

    @Column(name = "avatar", columnDefinition = "BLOB")
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "role_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @ManyToMany(mappedBy = "groupUsers")
    private Set<Group> groups;

    @OneToMany(mappedBy = "user")
    private Set<Image> images;

    @OneToMany(mappedBy = "user")
    private Set<ConversationReply> message;


}
