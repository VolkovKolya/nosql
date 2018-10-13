package ru.kpfu.itis.group11501.cinema.benchmark;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Movie;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class CassandraInsertSelectPreparedStatementBenchmark {

    @State(Scope.Benchmark)
    public static class MyState {

        @Setup(Level.Trial)
        public void doSetup() {
            this.session = CassandraConfig.getSession();
            this.preparedStatementInsert= this.session.prepare(
                    "insert into test_keyspace.film_table (name, year) values (?, ?)");
            this.preparedStatementSelect= this.session.prepare(
                    "SELECT * FROM test_keyspace.film_table where name = ?");
        }

        @TearDown(Level.Trial)
        public void doTearDown() {
            CassandraConfig.closeConnection();
        }

        @Setup(Level.Invocation)
        public void setupMovie() {
            this.movie =  new Movie(RandomStringUtils.randomAlphabetic(10),new Random().nextLong());
        }

        public Session session;
        public Movie movie;

        public PreparedStatement preparedStatementInsert;
        public PreparedStatement preparedStatementSelect;

    }


    @Benchmark
    public void insertMethodTest(MyState state) {
        Movie movie = state.movie;
        BoundStatement bound = state.preparedStatementInsert.bind(movie.getName(),movie.getYear());
        state.session.execute(bound);
    }

    @Benchmark
    public void selectMethodTest(Blackhole blackhole,MyState state) {
        Movie movie = state.movie;
        BoundStatement bound = state.preparedStatementSelect.bind(movie.getName());
        ResultSet rs = state.session.execute(bound);
        blackhole.consume(rs);
    }



}