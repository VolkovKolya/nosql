package ru.kpfu.itis.group11501.cinema.entity;

import java.util.Random;

public enum Country {
    ALD, BJN, CNM, CYN, ESB, IOA, ISR, KAB, KAS, NOR, PSX, SCR, SDS, SER, SOL, UMI, USG, WSB, RUS;

    public static Country getRandomCountry() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
