package com.example.dominantsoftdevelopment.model.enums;

import java.util.*;

public enum Country {
    UZBEKISTAN(List.of("Qashqadaryo","Surxondaryo","Toshkent")),
    KAZAKHSTAN(List.of("Nur-Sulton","qazzaq","Toshkent-qazaq"));

    private List<String> region;

    Country(List<String> region) {
        this.region = region;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }
}
