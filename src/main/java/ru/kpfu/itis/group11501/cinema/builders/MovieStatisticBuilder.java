package ru.kpfu.itis.group11501.cinema.builders;

import ru.kpfu.itis.group11501.cinema.entity.Country;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.time.LocalDateTime;
import java.util.Random;

public class MovieStatisticBuilder {
    public MovieStatistic buildWithRandomValues() {
        MovieStatistic movieStatistic = new MovieStatistic();
        movieStatistic.setCountryName(Country.getRandomCountry().name());

        movieStatistic.setMonth(LocalDateTime.now().getMonth().getValue());
        movieStatistic.setYear(LocalDateTime.now().getYear());

        Long id = new Random().nextLong();
        movieStatistic.setMovieId(id);
        movieStatistic.setMovieName(id.toString());
        movieStatistic.setPercent(100 * new Random().nextDouble());
        return movieStatistic;
    }
}
