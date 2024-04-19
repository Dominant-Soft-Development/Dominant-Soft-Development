package com.example.dominantsoftdevelopment.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    @JsonProperty
    private String title;
    @JsonProperty
    private String body;
    @JsonProperty
    private String topic;

    private List<String> token;
}