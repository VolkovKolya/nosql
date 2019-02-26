package ru.kpfu.itis.group11501.cinema.repository.neo4j;

import org.neo4j.driver.v1.*;
import ru.kpfu.itis.group11501.cinema.config.Neo4jConfig;
import ru.kpfu.itis.group11501.cinema.entity.Movie;
import ru.kpfu.itis.group11501.cinema.entity.User;


import static org.neo4j.driver.v1.Values.parameters;

public class UserRepositoryNeo4j {

    private Driver driver;
    private static final String addUser = "MERGE (u:Person {name:{name}})";
    private static final String addUserPreference = "MERGE (m:Movie {title:{name}, released:{year}})";

    public UserRepositoryNeo4j(Driver driver) {
        this.driver = driver;
    }

    public void addUser(User user) {
        try (Session session = Neo4jConfig.getDriver().session()) {
            session.run(
                    addUser,
                    parameters("name", user.getName()));

        }
    }

    public void addUserPreference(User user, Movie movie) {
        try (Session session = Neo4jConfig.getDriver().session()) {
            session.writeTransaction(tx -> makeUserPreference(tx, user.getName(), movie.getName()));
        }
    }

    private StatementResult makeUserPreference(final Transaction tx, final String userName, final String movieName) {
        return tx.run("MATCH (p:Person {name: {userName}}) " +
                        "MATCH (m:Movie {name: {movieName}}) " +
                        "MERGE (p)-[:PREFERENCE]->(m)",
                parameters("userName", userName, "movieName", movieName));
    }


}
