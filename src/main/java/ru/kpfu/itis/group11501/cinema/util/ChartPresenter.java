package ru.kpfu.itis.group11501.cinema.util;


import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Amir Kadyrov
 * Date: 13.10.2018
 */
public class ChartPresenter {

    public static CategoryChart createChart(){
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Current Views").xAxisTitle("Country").yAxisTitle("Views").build();
        CategorySeries series = chart.addSeries("Random Series", Arrays.asList(0, 1, 2, 3, 4), Arrays.asList(4, 5, 9, 6, 5));
        series.setMarker(SeriesMarkers.NONE);
        chart.getStyler().setLegendVisible(false);
        return chart;
    }

    public static void main(String[] args) throws InterruptedException {
        SwingWrapper<CategoryChart> chart = new SwingWrapper<>(createChart());
        chart.displayChart();
        while (true){
            Thread.sleep(1000);
            chart.getXChartPanel().getChart().getSeriesMap().get("Random Series").replaceData(Arrays.asList(0, 1, 2, 3, 4), Arrays.asList(10, 10, 10, 10, 10),null);
            chart.repaintChart(0);
        }
    }




}
