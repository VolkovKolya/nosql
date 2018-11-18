package ru.kpfu.itis.group11501.cinema.entity;

import java.util.Random;

public enum Genre {
    ACTION("Action"),
    ANIME("Anime"),
    ADVENTURE("Adventure"),
    THRILLER("Thriller"),
    COMEDY("Comedy");
    private final String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Genre getRandomGenre() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
