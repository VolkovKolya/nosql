package ru.kpfu.itis.group11501.cinema.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


public class CassandraConfig {
    private static String[] CONTACT_POINTS = {"192.168.56.101"};
    //9042 default, 9160 for rpc
    private static int PORT = 9042;
    private static Cluster cluster;
    private static Session session;

    static { connect();}
    private static void connect() {
        try {
            cluster = Cluster.builder().addContactPoints(CONTACT_POINTS).withPort(PORT).build();
            System.out.printf("Connected to cluster: %s%n", cluster.getMetadata().getClusterName());
            session = cluster.connect();
        }
        catch (Exception e){
            throw new RuntimeException("Cannot connect to cassandra");
        }
    }

    public static Session getSession(){
        if (session != null) return session;
        throw new RuntimeException("Cant find session");
    }

    public static void closeConnection(){
        session.close();
        cluster.close();
    }



}
