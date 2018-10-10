package ru.kpfu.itis.group11501.cinema;

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
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class CassandraInsertSelectBenchmark {

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
        Movie movie = state.movie;
        String query =
                "insert into test_keyspace.film_table (name, year) values ('"
                        + movie.getName()
                        + "',"
                        + movie.getYear()
                        + ")";
        state.session.execute(query);
    }

    @Benchmark
    public void selectMethodTest(Blackhole blackhole, MyState state) {
        Movie movie = state.movie;
        String query =
                "SELECT * FROM test_keyspace.film_table where name ='"
                        + movie.getName()
                        + "'";
        ResultSet rs = state.session.execute(query);
        blackhole.consume(rs);
    }

}
