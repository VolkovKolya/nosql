package ru.kpfu.itis.group11501.cinema.util;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import ru.kpfu.itis.group11501.cinema.config.Neo4jConfig;
import ru.kpfu.itis.group11501.cinema.entity.Genre;
import ru.kpfu.itis.group11501.cinema.entity.Movie;
import ru.kpfu.itis.group11501.cinema.entity.User;
import ru.kpfu.itis.group11501.cinema.repository.neo4j.MovieRepositoryNeo4j;
import ru.kpfu.itis.group11501.cinema.repository.neo4j.UserRepositoryNeo4j;

import java.util.List;
import java.util.Random;

import static org.neo4j.driver.v1.Values.parameters;

public class Neo4jUtil {

    public static void main(String[] args) {
        System.out.println(Genre.getRandomGenre().name());
    }
    public static void getMoviesByUser(){
        MovieRepositoryNeo4j movieRepositoryNeo4j = new MovieRepositoryNeo4j(Neo4jConfig.getDriver());
        List<Movie> movies = movieRepositoryNeo4j.getPreferenceMovie(new User("Serazetdinov Damir"));
        System.out.println(movies);
    }
    public static void addUser(){
        UserRepositoryNeo4j userRepositoryNeo4j = new UserRepositoryNeo4j(Neo4jConfig.getDriver());
        User user = new User("1");
        userRepositoryNeo4j.addUser(user);
    }

    public static void addUserPreference(){
        UserRepositoryNeo4j userRepositoryNeo4j = new UserRepositoryNeo4j(Neo4jConfig.getDriver());
        User user = new User("1");
        Movie movie = new Movie("1",new Random().nextLong());
        userRepositoryNeo4j.addUserPreference(user,movie);
    }
}

