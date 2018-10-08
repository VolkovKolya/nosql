package ru.kpfu.itis.group11501.cinema;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;

public class TestConnection {
    public static void main(String[] args) {
        Session session = CassandraConfig.getSession();
        ResultSet resultSet = session.execute("SELECT * FROM test_keyspace.film_table;");
        System.out.println(resultSet.one());
    }
}
