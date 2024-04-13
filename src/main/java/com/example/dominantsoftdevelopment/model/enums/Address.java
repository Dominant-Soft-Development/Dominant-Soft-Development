package com.example.dominantsoftdevelopment.model.enums;

import com.example.dominantsoftdevelopment.model.baseData.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Address extends BaseModel {
    @Enumerated(EnumType.STRING)
    Country country;
    String region;
}
