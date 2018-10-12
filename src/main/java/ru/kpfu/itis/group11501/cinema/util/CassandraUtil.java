package ru.kpfu.itis.group11501.cinema.util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.RandomStringUtils;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Movie;

import java.util.Random;

public class CassandraUtil {
    public static void main(String[] args) {
        benchmarkInsert();
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
