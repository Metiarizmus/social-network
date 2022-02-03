package com.nikolai.network.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "associations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends BaseEntity {

    @Column(name = "name", length = 50)
    private String nameGroup;

    @Column(name = "avatar", columnDefinition = "BLOB")
    private byte[] avatar;

    @Column(name = "creater_id")
    private Integer createrId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "group_user",
            joinColumns = {@JoinColumn(name = "associations_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<User> groupUsers;


    @OneToMany(mappedBy = "group")
    private Set<ContentGroup> contentGroups;


    public Group(String nameGroup, byte[] avatar, Integer createrId) {
        this.nameGroup = nameGroup;
        this.avatar = avatar;
        this.createrId = createrId;
    }
}
