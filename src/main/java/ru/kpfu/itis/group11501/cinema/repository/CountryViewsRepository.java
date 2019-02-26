package ru.kpfu.itis.group11501.cinema.repository;

import ru.kpfu.itis.group11501.cinema.entity.CountryViews;

import java.util.List;

public interface CountryViewsRepository {
    List<CountryViews> getCountryViews();
    void incCountryViews(String country);
    void decCountryViews(String country);
}
