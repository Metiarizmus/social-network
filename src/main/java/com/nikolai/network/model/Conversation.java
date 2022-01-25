package com.nikolai.network.model;

import com.nikolai.network.enums.StatusConversation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "conversation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation extends BaseEntity{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    private User firstUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    private User secondUser;

    @Column(name = "status")
    private StatusConversation statusConversation;
}
