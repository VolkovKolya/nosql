package ru.kpfu.itis.group11501.cinema;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;

public class Test {
    static class MyTask extends TimerTask {
        private Session session;

        public MyTask(Session session) {
            this.session = session;
        }

        public void run() {
            ResultSet resultSet = this.session.execute("SELECT * from test_keyspace.film_table");
            Map map = new HashMap();
//            map.put(resultSet.one().getPartitionKeyToken(), )
        }
    }

    static class MyTask2 extends TimerTask {
        private Session session;

        public MyTask2(Session session) {
            this.session = session;
        }

        public void run() {
            PreparedStatement preparedStatementUpdate = this.session.prepare(
                    "update  cinema_statistic.country_views "
                            + "set views = views+1"+
                            " where c_name=?");
            BoundStatement bound = preparedStatementUpdate.bind(Country.getRandomCountry());
            this.session.execute(bound);
        }
    }

    public static void main(String[] args) {
        Session session = CassandraConfig.getSession();
        Timer timer = new Timer();
        timer.schedule(new MyTask(session), 0, 10000);

    }
}
