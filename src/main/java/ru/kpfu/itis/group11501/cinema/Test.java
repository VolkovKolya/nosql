package ru.kpfu.itis.group11501.cinema;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;

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

    public static void main(String[] args) {
        Session session = CassandraConfig.getSession();
        Timer timer = new Timer();
        timer.schedule(new MyTask(session), 0, 10000);
    }
}
