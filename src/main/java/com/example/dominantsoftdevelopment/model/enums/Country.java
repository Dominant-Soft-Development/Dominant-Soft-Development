package com.example.dominantsoftdevelopment.model.enums;

import com.example.dominantsoftdevelopment.model.User;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public enum Country {
    UZBEKISTAN(List.of(
            "Tashkent",
            "Andijon",
            "Buxoro",
            "Farg’ona",
            "Jizzax",
            "Xorazm",
            "Namangan",
            "Navoiy",
            "Qashqadaryo",
            "Samarqand",
            "Sirdaryo",
            "Surxondaryo",
            "")),
    KAZAKHSTAN(List.of(
            "Akmola",
            "Aktobe",
            "Almaty",
            "Astana",
            "Atyrau",
            "Baikonur",
            "East Kazakhstan",
            "Jambyl",
            "Jetisu",
            "Karaganda",
            "Kostanay",
            "Kyzylorda",
            "Mangystau",
            "North Kazakhstan",
            "Pavlodar",
            "Shymkent",
            "Ulytau",
            "West Kazakhstan"
            )),
    TAJIKISTAN(List.of(
            "Tashkent",
            "Dushanbe",
            "Badaxshon",
            "Sugʻd ",
            "Xatlon")),
    KYRGYZSTAN(List.of(
            "Bishkek",
            "Batken",
            "Jalal-Abad",
            "Naryn",
            "Osh",
            "Talas",
            "Karakol"));


    @Setter
    private List<String> region;

    Country(List<String> region) {
        this.region = region;
    }

}
