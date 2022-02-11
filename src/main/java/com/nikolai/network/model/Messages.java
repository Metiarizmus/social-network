package com.nikolai.network.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@Data
public class Messages extends BaseEntity implements Serializable{

    @OneToOne
    @JoinColumn(name = "user_from", referencedColumnName = "id")
    private User sender;

    @OneToOne
    @JoinColumn(name = "user_to", referencedColumnName = "id")
    private User recipient;

    @Column(name = "message")
    private String mess;

    public Messages(User sender, User recipient, String mess) {
        this.sender = sender;
        this.recipient = recipient;
        this.mess = mess;
    }


}
