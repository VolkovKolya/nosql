package ru.kpfu.itis.group11501.cinema.repository.cassandra;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Delete;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;
import ru.kpfu.itis.group11501.cinema.repository.MovieStatisticRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MovieStatisticRepositoryCassandra implements MovieStatisticRepository {

    private Session session;
    private static final String SELECT = "SELECT * FROM cinema_statistic.film_statistic where fid = ? ";
    private static final String DELETE = "DELETE FROM cinema_statistic.film_statistic where fid = ? ";
    private static final String INSERT =
            "insert into cinema_statistic.film_statistic "
            + "(fid,c_name,salt,year,month,percent,f_name)"
            + " values (?, ?, uuid(), ?, ?, ?, ?)";


    public MovieStatisticRepositoryCassandra(Session session) {
        this.session = session;
    }

    @Override
    public List<MovieStatistic> getMovieStatisticByMovieId(Long id) {
        PreparedStatement ps = session.prepare(SELECT);
        BoundStatement bound = ps.bind(id);
        ResultSet resultSet = session.execute(bound);
        System.out.println(resultSet.one().toString());
        System.out.println(resultSet.one().getString("c_name"));
        return resultSet.all().stream()
                .map(row->new MovieStatistic(row.getLong("fid")
                        ,row.getString("c_name")
                        ,row.getInt("year")
                        ,row.getInt("month")
                        ,row.getDouble("percent")
                        ,row.getString("f_name")))
                .collect(Collectors.toList());

    }

    @Override
    public void addMovieStatistic(MovieStatistic movieStatistic) {
        PreparedStatement ps = session.prepare(INSERT);
        BoundStatement bound = ps.bind(
                movieStatistic.getMovieId(),
                movieStatistic.getCountryName(),
                movieStatistic.getYear(),
                movieStatistic.getMonth(),
                movieStatistic.getPercent(),
                movieStatistic.getMovieName()
        );
        this.session.execute(bound);
    }

    @Override
    public void updateMovieStatistic(MovieStatistic movieStatistic) {
        addMovieStatistic(movieStatistic);
    }

    @Override
    public void deleteMovieStatisticByMovieId(Long id) {
        PreparedStatement ps = session.prepare(DELETE);
        BoundStatement bound = ps.bind(id);
        this.session.execute(bound);
    }


}
