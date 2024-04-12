package com.example.dominantsoftdevelopment.model.enums;

import lombok.Getter;

import java.util.*;

@Getter
public enum Country {
    UZBEKISTAN(List.of("Qashqadaryo","Surxondaryo","Toshkent")),
    KAZAKHSTAN(List.of("Nur-Sulton","qazzaq","Toshkent-qazaq"));

    private List<String> region;

    Country(List<String> region) {
        this.region = region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }
}
