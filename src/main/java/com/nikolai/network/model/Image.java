package com.nikolai.network.model;

import com.nikolai.network.enums.TypeImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image extends BaseEntity{

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "location", length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeImage typeImage;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
