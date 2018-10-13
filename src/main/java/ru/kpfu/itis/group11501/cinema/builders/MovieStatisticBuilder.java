package ru.kpfu.itis.group11501.cinema.builders;

import org.apache.commons.lang3.RandomStringUtils;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.IntStream;

public class MovieStatisticBuilder {
    public MovieStatistic buildWithRandomValues(){
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

    private enum Country{
ALD,BJN, CNM, CYN, ESB, IOA, ISR, KAB, KAS, NOR, PSX, SCR, SDS, SER, SOL, UMI, USG, WSB,  RUS;
    public static Country getRandomCountry(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
    }


}
