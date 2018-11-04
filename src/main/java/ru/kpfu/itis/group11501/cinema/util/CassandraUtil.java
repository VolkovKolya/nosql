package ru.kpfu.itis.group11501.cinema.util;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.RandomStringUtils;
import ru.kpfu.itis.group11501.cinema.builders.MovieStatisticBuilder;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Movie;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.util.Random;

public class CassandraUtil {
    public static void main(String[] args) {
        millionRowsMovieStatistic();
    }
    private static void millionRows() {
        Session session = CassandraConfig.getSession();
        for (int i=0; i<950000; i++){
            Movie movie =  new Movie(RandomStringUtils.randomAlphabetic(10),new Random().nextLong());
            String query =
                    "insert into test_keyspace.film_table (name, year) values ('"
                            + movie.getName()
                            + "',"
                            + movie.getYear()
                            + ")";
            session.execute(query);
        }
    }

    private static void millionRowsMovieStatistic() {
        Session session = CassandraConfig.getSession();
        PreparedStatement ps = session.prepare(
                "insert into cinema_statistic.film_statistic "
                        + "(fid,c_name,salt,year,month,percent,f_name)"+
                        " values (?, ?, uuid(), ?, ?, ?, ?)");
        for (int i=0; i<950000; i++){
            MovieStatistic movieStatistic =  new MovieStatisticBuilder().buildWithRandomValues();
            BoundStatement bound = ps.bind(
                    movieStatistic.getMovieId(),
                    movieStatistic.getCountryName(),
                    movieStatistic.getYear(),
                    movieStatistic.getMonth(),
                    movieStatistic.getPercent(),
                    movieStatistic.getMovieName()
            );
            session.execute(bound);
        }
    }

    //Elapsed: 959.8023185002082 ops/s
    private static void benchmarkInsert() {
        Session session = CassandraConfig.getSession();
        long start = System.nanoTime();
        long n = 10000;
        for (int i=0; i<n; i++){
            String query =
                    "insert into test_keyspace.film_table (name, year) values ('"
                            + RandomStringUtils.randomAlphabetic(10)
                            + "',"
                            + new Random().nextLong()
                            + ")";
            session.execute(query);
        }
        double second = ((double)(System.nanoTime() - start))/1_000_000_000;
        System.out.println("Elapsed: "+ n/second + " ops/s");
    }


}
