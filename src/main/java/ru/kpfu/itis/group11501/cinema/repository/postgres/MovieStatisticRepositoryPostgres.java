package ru.kpfu.itis.group11501.cinema.repository.postgres;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;
import ru.kpfu.itis.group11501.cinema.repository.MovieStatisticRepository;
import ru.kpfu.itis.group11501.cinema.rowmapper.MovieStatisticRowMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MovieStatisticRepositoryPostgres implements MovieStatisticRepository {

    private JdbcTemplate[] jdbcTemplates;

    public MovieStatisticRepositoryPostgres(JdbcTemplate[] jdbcTemplates) {
        this.jdbcTemplates = jdbcTemplates;
    }

    private static final String sqlGetById = "SELECT * FROM film_statistic WHERE id = ?";
    private static final String sqlInsert = "INSERT INTO film_statistic VALUES(?,?)";
    //to do
    private static final String sqlStatistic = "";


    @Override
    public MovieStatistic getMovieStatisticById(String id) {
        JdbcTemplate jdbcTemplate = jdbcTemplates[0];
        return jdbcTemplate.queryForObject(sqlGetById, new Object[]{id}, new MovieStatisticRowMapper());
    }

    @Override
    public void addMovieStatistic(MovieStatistic movieStatistic) {
        JdbcTemplate jdbcTemplate = jdbcTemplates[0];
        jdbcTemplate.update(sqlInsert,getKey(movieStatistic),getDoc(movieStatistic));
    }

    private String getKey(MovieStatistic movieStatistic) {
        /*return movieStatistic.getMovieId() + "_"
                + movieStatistic.getCountryName() + "_"
                + Timestamp.valueOf(movieStatistic.getLocalDateTime()) + "_"
                + movieStatistic.getSalt();*/
        return null;
    }

    private String getDoc(MovieStatistic movieStatistic) {
        //to do return json
        return new String();
    }

    //delete after pick mode
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());
        System.out.println(localDateTime.getNano());
        System.out.println(Timestamp.valueOf(localDateTime));
    }

}
