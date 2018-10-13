package ru.kpfu.itis.group11501.cinema.util;


import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import ru.kpfu.itis.group11501.cinema.config.CassandraConfig;
import ru.kpfu.itis.group11501.cinema.entity.Country;
import ru.kpfu.itis.group11501.cinema.entity.CountryViews;
import ru.kpfu.itis.group11501.cinema.repository.CountryViewsRepository;
import ru.kpfu.itis.group11501.cinema.repository.cassandra.CountryViewsRepositoryCassandra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Amir Kadyrov
 * Date: 13.10.2018
 */
public class ChartPresenter {

    private static CategoryChart createChart(List<CountryViews> data){
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Current Views").xAxisTitle("Country").yAxisTitle("Views").build();

        CategorySeries series = chart.addSeries("Cassandra",
                //Arrays.stream(Country.values()).map(Enum::name).collect(Collectors.toList()),
                data.stream().map(CountryViews::getCountryName).collect(Collectors.toList()),
                data.stream().map(CountryViews::getViews).collect(Collectors.toList()));
        series.setMarker(SeriesMarkers.NONE);

        chart.getStyler().setLegendVisible(false);
        return chart;
    }

    public static void main(String[] args) throws InterruptedException {
        CountryViewsRepository repository = new CountryViewsRepositoryCassandra(CassandraConfig.getSession());
        SwingWrapper<CategoryChart> chart = new SwingWrapper<>(createChart(repository.getCountryViews()));
        chart.displayChart();
        while (true){
            Thread.sleep(1000);
            List<CountryViews> newData = repository.getCountryViews();
            chart.getXChartPanel().getChart().getSeriesMap().get("Cassandra").replaceData(
                    newData.stream().map(CountryViews::getCountryName).collect(Collectors.toList()),
                    newData.stream().map(CountryViews::getViews).collect(Collectors.toList()),
                    null);
            chart.repaintChart(0);
        }
    }






}
