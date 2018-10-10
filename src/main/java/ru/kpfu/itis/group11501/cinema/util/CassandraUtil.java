package ru.kpfu.itis.group11501.cinema.util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.RandomStringUtils;
import ru.kpfu.itis.group11501.cinema.entity.Movie;

import java.util.Random;

public class CassandraUtil {
    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoints("192.168.56.101").withPort(9042).build();
        System.out.printf("Connected to cluster: %s%n", cluster.getMetadata().getClusterName());
        Session session = cluster.connect();
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
}
