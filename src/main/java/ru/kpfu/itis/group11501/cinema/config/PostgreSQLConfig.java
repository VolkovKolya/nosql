package ru.kpfu.itis.group11501.cinema.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

import java.util.stream.IntStream;


public final class PostgreSQLConfig {
    private static final String driver = "org.postgresql.Driver";
    private static final int nodes = 6;
    private static final String[] urls = {
            "jdbc:postgresql://host:port/cinema",
            "jdbc:postgresql://host:port/cinema",
            "jdbc:postgresql://host:port/cinema",
            "jdbc:postgresql://host:port/cinema",
            "jdbc:postgresql://host:port/cinema",
            "jdbc:postgresql://host:port/cinema",};
    private static final String user = "postgres";
    private static final String password = "postgres";
    private static JdbcTemplate[] jdbcTemplates;

    static {
        buildJdbcTemplates();
    }

    private static DataSource buildDataSource(String url) {
        HikariConfig dataSource = new HikariConfig();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return new HikariDataSource(dataSource);
    }

    private static void buildJdbcTemplates() {
        JdbcTemplate[] jdbcTemplates = IntStream.range(0, nodes)
                .mapToObj(i -> new JdbcTemplate(buildDataSource(urls[i])))
                .toArray(JdbcTemplate[]::new);

        PostgreSQLConfig.jdbcTemplates = jdbcTemplates;
    }

    public static JdbcTemplate[] getJdbcTemplates() {
        if (jdbcTemplates != null) return jdbcTemplates;
        else throw new RuntimeException("Cant find jdbcTemplates");
    }

    private PostgreSQLConfig() {
    }

    ;
}
