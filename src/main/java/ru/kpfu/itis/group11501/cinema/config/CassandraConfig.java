package ru.kpfu.itis.group11501.cinema.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConfig {
    private static String[] CONTACT_POINTS = {"127.0.0.1"};
    private static int PORT = 9042;
    private static Cluster cluster;
    private static Session session;

    static { connect();}
    private static void connect() {
        cluster = Cluster.builder().addContactPoints(CONTACT_POINTS).withPort(PORT).build();
        System.out.printf("Connected to cluster: %s%n", cluster.getMetadata().getClusterName());
        session = cluster.connect();
    }

    public static Session getSession(){
        if (session != null) return session;
        throw new RuntimeException("Cant find session");
    }

}
