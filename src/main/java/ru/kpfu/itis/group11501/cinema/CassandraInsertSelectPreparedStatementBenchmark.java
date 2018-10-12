package ru.kpfu.itis.group11501.cinema;

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

    }


    @Benchmark
    public void insertMethodTest(MyState state) {
        PreparedStatement prepared = state.session.prepare(
                "insert into test_keyspace.film_table (name, year) values (?, ?)");
        Movie movie = state.movie;
        BoundStatement bound = prepared.bind(movie.getName(),movie.getYear());
        state.session.execute(bound);
    }

    @Benchmark
    public void selectMethodTest(Blackhole blackhole,MyState state) {
        PreparedStatement prepared = state.session.prepare(
                "SELECT * FROM test_keyspace.film_table where name = ?");
        Movie movie = state.movie;
        BoundStatement bound = prepared.bind(movie.getName());
        ResultSet rs = state.session.execute(bound);
        blackhole.consume(rs);
    }



}