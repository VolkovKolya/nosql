package ru.kpfu.itis.group11501.cinema.repository;

import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.util.List;

public interface MovieStatisticRepository {

    List<MovieStatistic> getMovieStatisticByMovieId(Long id);

    void addMovieStatistic(MovieStatistic movieStatistic);

    void updateMovieStatistic(MovieStatistic movieStatistic);

    void deleteMovieStatisticByMovieId(Long id);
}
