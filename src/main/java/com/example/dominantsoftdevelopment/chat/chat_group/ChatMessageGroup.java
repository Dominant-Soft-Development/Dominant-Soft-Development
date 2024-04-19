package com.example.dominantsoftdevelopment.chat.chat_group;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageGroup {
    private String content;
    private String sender;
    private MessageType messageType;
}
