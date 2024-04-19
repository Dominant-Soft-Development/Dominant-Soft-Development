package com.example.dominantsoftdevelopment.dto;

import com.example.dominantsoftdevelopment.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfilePatchDto {
    Long id;
    String firstName;
    String lastName;
    @JsonProperty("attachmentId")
    Long attachment;
    Address address;
}
