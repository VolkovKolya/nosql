package ru.kpfu.itis.group11501.cinema.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieStatisticRowMapper implements RowMapper<MovieStatistic> {
    @Override
    public MovieStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieStatistic movieStatistic = new MovieStatistic();
        String id = resultSet.getString("id");
        String doc = resultSet.getString("doc");
        //timestamp.toLocalDateTime()
        //to do setup command
        return movieStatistic;
    }
}
