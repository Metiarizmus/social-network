package com.nikolai.network.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "content")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentGroup extends BaseEntity implements Serializable{

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "file_content", columnDefinition = "LONGBLOB")
    private byte[] fileContent;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group_id")
    private Group group;


}
