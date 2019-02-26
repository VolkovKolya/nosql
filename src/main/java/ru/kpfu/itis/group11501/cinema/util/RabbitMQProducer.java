package ru.kpfu.itis.group11501.cinema.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import ru.kpfu.itis.group11501.cinema.builders.MovieStatisticBuilder;
import ru.kpfu.itis.group11501.cinema.config.RabbitMQConfig;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;

import java.util.HashMap;
import java.util.Map;

public class RabbitMQProducer {
    private static final String EXCHANGE_NAME = "cinema";
    private static final String SEVERITY = "movie_statistic";

    public static void main(String[] args) {
        new Thread(()->millionRowsMovieStatistic("create")).start();
        new Thread(()->millionRowsMovieStatistic("read")).start();
        new Thread(()->millionRowsMovieStatistic("update")).start();
        new Thread(()->millionRowsMovieStatistic("delete")).start();
    }
    private static void millionRowsMovieStatistic(String operation) {
        try {
            Connection connection = RabbitMQConfig.getConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            Map<String, Object> headers = new HashMap<>();
            headers.put("operation", operation);
            AMQP.BasicProperties properties = new AMQP.BasicProperties
                    .Builder()
                    .contentType("application/json")
                    .headers(headers)
                    .build();
            for (int i = 0; i < 950000; i++) {
                MovieStatistic movieStatistic = new MovieStatisticBuilder().buildWithRandomValues();
                ObjectMapper mapper = new ObjectMapper();
                String message = mapper.writeValueAsString(movieStatistic);
                channel.basicPublish(EXCHANGE_NAME, SEVERITY, properties, message.getBytes());
            }
            channel.close();
        }
        catch (Exception e){
            System.out.println("Can't do "+operation+"!");
        }
    }
}
