package ru.kpfu.itis.group11501.cinema.repository;

import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

public interface MovieStatisticRepository {
    MovieStatistic getMovieStatisticById(String id);
    void addMovieStatistic(MovieStatistic movieStatistic);
}
