package ru.kpfu.itis.group11501.cinema.config;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class Neo4jConfig  {

    private static final String uri= "bolt://localhost:7687";
    private static final String username = "neo4j";
    private static final String password = "password";
    private static final Driver driver;

    static {
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        }
        catch (Exception e){
            throw new RuntimeException("Cannot connect to neo4j");
        }
    }

    public static Driver getDriver(){
        if (driver != null) return driver;
        throw new RuntimeException("Cant find driver");
    }

    public static void closeDriver(){
        driver.close();
    }
}


