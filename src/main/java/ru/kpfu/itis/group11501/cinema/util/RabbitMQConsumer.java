package ru.kpfu.itis.group11501.cinema.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.config.RabbitMQConfig;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;
import ru.kpfu.itis.group11501.cinema.repository.MovieStatisticRepository;
import ru.kpfu.itis.group11501.cinema.repository.cassandra.MovieStatisticRepositoryCassandra;

import java.io.IOException;

public class RabbitMQConsumer {
    private static final String EXCHANGE_NAME = "cinema";
    private static final String SEVERITY = "movie_statistic";
    private static final String QUEUE_NAME = "crud";

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQConfig.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        String queueName = channel.queueDeclare(QUEUE_NAME,true,false,false,null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, SEVERITY);


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                MovieStatistic movieStatistic = mapper.readValue(new String(body), MovieStatistic.class);
                //System.out.println(" [x] Received '" + envelope.getRoutingKey() + "': movieId='" + movieStatistic.getMovieId() + "'");
                String operation = properties.getHeaders().get("operation").toString();
                MovieStatisticRepository movieStatisticRepository = new MovieStatisticRepositoryCassandra(CassandraConfig.getSession());
                switch (operation){
                    case "create":
                        movieStatisticRepository.addMovieStatistic(movieStatistic);
                        break;
                    case "read":
                        movieStatisticRepository.getMovieStatisticByMovieId(movieStatistic.getMovieId());
                        break;
                    case "update":
                        movieStatisticRepository.updateMovieStatistic(movieStatistic);
                        break;
                    case "delete":
                        movieStatisticRepository.deleteMovieStatisticByMovieId(movieStatistic.getMovieId());
                        break;
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }
}
