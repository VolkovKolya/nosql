package ru.kpfu.itis.group11501.cinema.builders;

import ru.kpfu.itis.group11501.cinema.Country;
import ru.kpfu.itis.group11501.cinema.entity.ActualStatistic;

import java.util.Random;

public class ActualStatisticBuilder {
    public ActualStatistic buildWithRandomValues() {
        ActualStatistic actualStatistic = new ActualStatistic();
        actualStatistic.setCountryName(Country.getRandomCountry().name());

        Long id = new Random().nextLong();
        actualStatistic.setMovieId(id);
        actualStatistic.setMovieName(id.toString());
        return actualStatistic;
    }
}
