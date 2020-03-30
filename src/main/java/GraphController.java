import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.function.Function;

public class GraphController {

    public void buildGraph(Function<Double, Double> func, double a, double b){
        String title = null;

        if(func.apply((double) 10) == 994.0){
            title = "x^3 - x + 4";
        }else{
            title = "x^3 + 2.28x^2 - 1.934x - 3.907";
        }

        /*------Построение графика-------*/
        XYSeries series = new XYSeries(title);

        for(double i = a; i < b; i+=0.1){
            series.add(i, func.apply(i));
        }

        XYDataset xyDataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory
                .createXYLineChart(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("График функции");

        /*------Помещаем график на фрейм-------*/
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.show();
    }
}
