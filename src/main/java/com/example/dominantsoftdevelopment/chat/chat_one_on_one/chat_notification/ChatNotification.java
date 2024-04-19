package com.example.dominantsoftdevelopment.chat.chat_one_on_one.chat_notification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String context;
}
