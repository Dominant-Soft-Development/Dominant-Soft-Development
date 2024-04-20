package com.example.dominantsoftdevelopment.chat.chat_one_on_one.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage
{
    @Id
    private Long id;
    private Long chatId;
    private Long senderId;
    private Long recipientId;
    private String context;
    private Date timestamp;

}
