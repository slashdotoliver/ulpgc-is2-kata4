package software.ulpgc.kata4.apps.swing.view.displays;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import software.ulpgc.kata4.architecture.model.entities.Histogram;
import software.ulpgc.kata4.architecture.view.HistogramDisplay;

import javax.swing.*;
import java.awt.*;

public class JFreeChartHistogramDisplay extends JPanel implements HistogramDisplay {

    private final ChartPanel chart;

    public JFreeChartHistogramDisplay() {
        add(chart = new ChartPanel(null));
    }

    @Override
    public void display(Histogram histogram) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        addValuesTo(dataset, histogram);
        JFreeChart barChart = ChartFactory.createBarChart(
                histogram.title(),
                "Categories",
                "Frequency",
                dataset
        );
        chart.setChart(barChart);
    }

    private static void addValuesTo(DefaultCategoryDataset dataset, Histogram histogram) {
        histogram.labels()
                .forEach(label -> dataset.addValue(
                        histogram.valueOf(label),
                        label,
                        "Absolute Frequency"
                ));
    }

    public void updateSize(Dimension newSize) {
        chart.setPreferredSize(newSize);
        setPreferredSize(newSize);
    }
}
