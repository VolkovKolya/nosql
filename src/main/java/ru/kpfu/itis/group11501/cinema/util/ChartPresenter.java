package ru.kpfu.itis.group11501.cinema.util;

import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Kadyrov
 * Date: 13.10.2018
 */
public class ChartPresenter {

    public static List<XYChart> createChart(){
        List<XYChart> charts = new ArrayList<XYChart>();
        XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
        XYSeries series = chart.addSeries("Random Series", null, getRandomWalk(200));
        series.setMarker(SeriesMarkers.NONE);
        charts.add(chart);
        return charts;
    }

    public static void main(String[] args) {
        SwingWrapper chart = new SwingWrapper<XYChart>(createChart());
        chart.displayChart();
        while (true){
            chart.repaintChart(0);
        }
    }

    /**
     * Generates a set of random walk data
     *
     * @param numPoints
     * @return
     */
    private static double[] getRandomWalk(int numPoints) {

        double[] y = new double[numPoints];
        y[0] = 0;
        for (int i = 1; i < y.length; i++) {
            y[i] = y[i - 1] + Math.random() - .5;
        }
        return y;
    }
}
