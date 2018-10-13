package ru.kpfu.itis.group11501.cinema.util;


import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Country;
import ru.kpfu.itis.group11501.cinema.entity.CountryViews;
import ru.kpfu.itis.group11501.cinema.repository.CountryViewsRepository;
import ru.kpfu.itis.group11501.cinema.repository.cassandra.CountryViewsRepositoryCassandra;

import java.time.LocalDateTime;
import java.util.*;

public class UserViewsEmulator {

    static class MyTask2 extends TimerTask {
        private Session session;

        MyTask2(Session session) {
            this.session = session;
        }

        public void run() {
            CountryViewsRepository repository= new CountryViewsRepositoryCassandra(session);
            repository.incCountryViews(Country.getRandomCountry().name());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Session session = CassandraConfig.getSession();
        Timer timer = new Timer();
        timer.schedule(new MyTask2(session), 0, 100);
    }
}
