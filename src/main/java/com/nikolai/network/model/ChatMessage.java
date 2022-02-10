package com.nikolai.network.model;

import com.nikolai.network.enums.StatusMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_message")
@Builder
public class ChatMessage extends BaseEntity{

    @Column(name = "chat_id")
    private String chatId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_sender", referencedColumnName = "id")
    private User sender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_recipient", referencedColumnName = "id")
    private User recipient;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "message")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusMessage status;
}
