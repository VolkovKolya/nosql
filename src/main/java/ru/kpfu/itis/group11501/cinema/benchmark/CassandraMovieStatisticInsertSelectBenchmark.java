package ru.kpfu.itis.group11501.cinema.benchmark;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.kpfu.itis.group11501.cinema.builders.MovieStatisticBuilder;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Movie;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class CassandraMovieStatisticInsertSelectBenchmark {

    @State(Scope.Benchmark)
    public static class MyState {

        @Setup(Level.Trial)
        public void doSetup() {
            this.session = CassandraConfig.getSession();
            //todo
            this.preparedStatementInsert = this.session.prepare(
                    "insert into cinema_statistic.film_statistic "
                            + "(fid,c_name,salt,year,month,percent,f_name)"+
                            " values (?, ?, uuid(), ?, ?, ?, ?)");

            //todo
            this.preparedStatementSelect = this.session.prepare(
                    "SELECT * FROM cinema_statistic.film_statistic where fid = ? ");
        }

        @TearDown(Level.Trial)
        public void doTearDown() {
            CassandraConfig.closeConnection();
        }


        @Setup(Level.Invocation)
        public void setupMovie() {
            this.movieStatistic = new MovieStatisticBuilder().buildWithRandomValues();
        }


        public Session session;
        public MovieStatistic movieStatistic;
        public PreparedStatement preparedStatementInsert;
        public PreparedStatement preparedStatementSelect;

    }

    @Benchmark
    public void insertMethodTest(MyState state) {
        MovieStatistic movieStatistic = state.movieStatistic;
        BoundStatement bound = state.preparedStatementInsert.bind(
                movieStatistic.getMovieId(),
                movieStatistic.getCountryName(),
                movieStatistic.getYear(),
                movieStatistic.getMonth(),
                movieStatistic.getPercent(),
                movieStatistic.getMovieName()
        );
        state.session.execute(bound);
    }

    @Benchmark
    public void selectMethodTest(Blackhole blackhole,MyState state) {
        MovieStatistic movieStatistic = state.movieStatistic;
        BoundStatement bound = state.preparedStatementSelect.bind(movieStatistic.getMovieId());
        ResultSet rs = state.session.execute(bound);
        blackhole.consume(rs);
    }


}