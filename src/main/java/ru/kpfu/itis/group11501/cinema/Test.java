package ru.kpfu.itis.group11501.cinema;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import jnr.ffi.annotations.In;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Amir Kadyrov
 * Date: 06.10.2018
 */
public class Test {
    public static void main(String[] args) {
        Session session = CassandraConfig.getSession();
        session.execute("INSERT INTO test_keyspace.film_table (name, year) VALUES ('film_name', 1956);");
        ResultSet resultSet = session.execute("SELECT * FROM test_keyspace.film_table;");
        System.out.println(resultSet.one());
    }
}
