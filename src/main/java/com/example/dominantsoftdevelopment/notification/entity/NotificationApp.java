package com.example.dominantsoftdevelopment.notification.entity;

import com.example.dominantsoftdevelopment.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class NotificationApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Boolean newMessage;
    private Boolean newAnnouncements;
    private Boolean priceReduction;
    private Boolean discount;
    @OneToOne(mappedBy = "notificationApp")
    private User user;
}
