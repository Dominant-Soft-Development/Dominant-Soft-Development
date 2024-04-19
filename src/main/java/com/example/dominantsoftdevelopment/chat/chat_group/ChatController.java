package com.example.dominantsoftdevelopment.chat.chat_group;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageGroup sendMessage(@Payload ChatMessageGroup chatMessageGroup) {
        return chatMessageGroup;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageGroup addUser(@Payload ChatMessageGroup chatMessageGroup, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username" , chatMessageGroup.getSender());
        return chatMessageGroup;
    }
}
