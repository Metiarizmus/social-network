package com.nikolai.network.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "conversation_reply")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationReply extends BaseEntity{

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private Conversation conversation;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
