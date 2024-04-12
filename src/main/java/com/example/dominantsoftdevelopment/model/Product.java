package com.example.dominantsoftdevelopment.model;

import com.example.dominantsoftdevelopment.model.baseData.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product extends BaseModel {

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    Double price;

    @ManyToOne
    Category productCategory;

    String productBrand;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    User seller;

    @OneToMany
    List<Attachment> attachment;



}
