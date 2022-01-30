package com.nikolai.network.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", length = 15)
    private String date;
}
