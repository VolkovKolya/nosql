package ru.kpfu.itis.group11501.cinema.config;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQConfig {
    private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static Connection connection;

    static { connect();}
    private static void connect() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            factory.setHost(HOST);
            factory.setPort(PORT);
            connection = factory.newConnection();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();

            throw new RuntimeException("Cannot connect to RabbitMQ");
        }
    }
    public static Connection getConnection(){
        if (connection != null) return connection;
        throw new RuntimeException("Can't find connection");
    }

    public static void closeConnection() throws IOException {
        connection.close();
    }

}
