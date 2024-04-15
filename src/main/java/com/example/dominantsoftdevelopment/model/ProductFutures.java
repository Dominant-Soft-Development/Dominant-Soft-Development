package com.example.dominantsoftdevelopment.model;

import com.example.dominantsoftdevelopment.model.baseData.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ProductFutures extends BaseModel {
    @OneToMany
    List<Product> product;
    @OneToOne
    ProductFeatureName productFeatureName;
    @OneToOne
    ProductFutureValue productFutureValue;
    String value;
}
