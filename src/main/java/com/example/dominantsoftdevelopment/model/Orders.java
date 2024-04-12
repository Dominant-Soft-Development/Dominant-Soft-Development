package com.example.dominantsoftdevelopment.model;

import com.example.dominantsoftdevelopment.model.baseData.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Orders extends BaseModel {
    @Column(nullable = false)
    Double totalPrice;

    @ManyToOne
    User customer;

}
