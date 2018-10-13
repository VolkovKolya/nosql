package ru.kpfu.itis.group11501.cinema.entity;

public class CountryViews {
    private String countryName;
    private Long views;

    public CountryViews() {
    }

    public CountryViews(String countryName, Long views) {
        this.countryName = countryName;
        this.views = views;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "CountryViews{" +
                "countryName='" + countryName + '\'' +
                ", views=" + views +
                '}';
    }
}
