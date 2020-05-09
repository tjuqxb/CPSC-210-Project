package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.ClimateData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class DataDisplay extends JDialog {
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 1200;

    public DataDisplay() {
        super();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where the data will display
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH / 2, HEIGHT / 2));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS:  display the climate data
    public void displayClimates(ArrayList<ClimateData> cds) {
        int y = cds.size() / 2;
        JPanel jp = new JPanel(new GridLayout(2, y));
        for (int i = 0; i < cds.size(); i++) {
            jp.add(displaySingleClimate(cds.get(i)));
        }
        this.add(jp);
        setVisible(true);
    }

    // EFFECTS:  return ChartPanel display a single data
    public ChartPanel displaySingleClimate(ClimateData cd) {
        String name = cd.name;
        XYDataset dataSet = createDataset(proSingleClimateData(cd), name);
        JFreeChart chart =
                ChartFactory.createXYLineChart(name.toUpperCase() + " CHART",
                        "months", name, dataSet, PlotOrientation.VERTICAL, true, true,
                        false);
        ChartPanel cp = new ChartPanel(chart);
        return cp;
    }



    // EFFECTS: return a data set of a 2d array and a name
    private XYDataset createDataset(double[][] data, String name) {
        DefaultXYDataset ds = new DefaultXYDataset();
        ds.addSeries(name, data);
        return ds;
    }

    //EFFECTS: return the 2d array for display climate
    private double[][] proSingleClimateData(ClimateData cd) {
        double[][] array = new double[2][12];
        for (int i = 0; i < 12; i++) {
            array[0][i] = i + 1;
            array[1][i] = cd.data.get(i);
        }
        return array;
    }


}
