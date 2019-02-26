package ru.kpfu.itis.group11501.cinema.repository.cassandra;


import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import ru.kpfu.itis.group11501.cinema.entity.Country;
import ru.kpfu.itis.group11501.cinema.entity.CountryViews;
import ru.kpfu.itis.group11501.cinema.repository.CountryViewsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CountryViewsRepositoryCassandra implements CountryViewsRepository {

    private Session session;
    private static final String select= "SELECT * FROM cinema_statistic.country_views";
    private static final String incCountryViews = "update cinema_statistic.country_views set views = views+1 where c_name=?";
    private static final String decCountryViews = "update cinema_statistic.country_views set views = views-1 where c_name=?";

    public CountryViewsRepositoryCassandra(Session session) {
        this.session = session;
    }

    @Override
    public List<CountryViews> getCountryViews() {
        ResultSet resultSet = session.execute(select);
        return resultSet.all().stream()
                .map(row->new CountryViews(row.getString("c_name"),row.getLong("views")))
                .collect(Collectors.toList());
    }

    @Override
    public void incCountryViews(String country) {
        PreparedStatement preparedStatementUpdate = session.prepare(incCountryViews);
        BoundStatement bound = preparedStatementUpdate.bind(country);
        this.session.execute(bound);
    }

    @Override
    public void decCountryViews(String country) {
        PreparedStatement preparedStatementUpdate = session.prepare(decCountryViews);
        BoundStatement bound = preparedStatementUpdate.bind(country);
        this.session.execute(bound);
    }
}
