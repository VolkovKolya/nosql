package ru.kpfu.itis.group11501.cinema.repository.neo4j;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import ru.kpfu.itis.group11501.cinema.config.Neo4jConfig;
import ru.kpfu.itis.group11501.cinema.entity.Genre;
import ru.kpfu.itis.group11501.cinema.entity.Movie;
import ru.kpfu.itis.group11501.cinema.entity.User;

import java.util.List;
import java.util.stream.Collectors;

import static org.neo4j.driver.v1.Values.parameters;

public class MovieRepositoryNeo4j {

    private Driver driver;
    private static final String getPreferenceMovie = "MATCH (a:Person {name:{name}})-[:PREFERENCE]->(m:Movie) RETURN m";
    private static final String addMovie = "MERGE (m:Movie {title:{name}, released:{year}})";


    public MovieRepositoryNeo4j(Driver driver) {
        this.driver = driver;
    }


    public List<Movie> getPreferenceMovie(User user) {
        try (Session session = Neo4jConfig.getDriver().session()) {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    getPreferenceMovie,
                    parameters("name", user.getName()));
            return result.list().stream()
                    .map(record -> new Movie(record.get("m").get("title").asString(), record.get("m").get("released").asLong()))
                    .collect(Collectors.toList());

        }
    }

    public void addMovie(Movie movie) {
        try (Session session = Neo4jConfig.getDriver().session()) {
            session.run(
                    addMovie,
                    parameters("name", movie.getName(),
                            "year", movie.getYear()));

        }
    }


    public void addMovieGenre(Movie movie, Genre genre) {
        try (Session session = Neo4jConfig.getDriver().session()) {
            session.writeTransaction(tx -> makeMovieGenre(tx, movie.getName(), genre.getName()));
        }
    }

    private StatementResult makeMovieGenre(final Transaction tx, final String movieName, final String genreName )
    {
        return tx.run( "MATCH (m:Movie {name: {movieName}}) " +
                        "MATCH (g:Genre {name: {genreName}}) " +
                        "MERGE (m)-[:GENRE]->(g)",
                parameters( "movieName", movieName, "genreName",genreName ) );
    }
}
