package com.nikolai.network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
@Getter
@Setter
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

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "avatar", columnDefinition = "LONGBLOB")
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "role_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @ManyToMany(mappedBy = "groupUsers", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Group> groups;

    @OneToMany(mappedBy = "user")
    private Set<Image> images;

    @OneToOne(mappedBy = "userFrom")
    private Friend friendFrom;

    @OneToOne(mappedBy = "userTo")
    private Friend friendTo;

    @OneToOne(mappedBy = "sender")
    private ChatMessage sender;

    @OneToOne(mappedBy = "recipient")
    private ChatMessage recipient;

}
