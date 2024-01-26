import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ScatterPlotExample extends JFrame {
    private ChartPanel chartPanel;
    private JFreeChart chart;

    public ScatterPlotExample() {
        super("Scatter Plot Example");
        this.chartPanel = new ChartPanel(null);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void createShow(String title, double[] xValues, double[][] yValues, double level, double level_y[], int level_y_l, String ss[]) {
        XYSeriesCollection[] datasets = new XYSeriesCollection[yValues.length];

        // Calculating min and max for x and y values
        double minX = Arrays.stream(xValues).min().orElse(0);
        double maxX = Arrays.stream(xValues).max().orElse(10);
        double minY = Arrays.stream(yValues).flatMapToDouble(Arrays::stream).min().orElse(0);
        double maxY = Arrays.stream(yValues).flatMapToDouble(Arrays::stream).max().orElse(10);

        for (int i = 0; i < yValues.length; i++) {
            if(ss.length==0)
            datasets[i] = new XYSeriesCollection(createSeries(xValues, yValues[i], ""));
            else
            datasets[i] = new XYSeriesCollection(createSeries(xValues, yValues[i], ss[i]));
            
        }

        JFreeChart chart = createChart(datasets, minX, maxX, minY, maxY);
        chartPanel.setChart(chart);

        setTitle(title);
        validate();
        setVisible(true);

        // Store the created chart
        this.chart = createChart(datasets, minX, maxX, minY, maxY);
        addAxisAnnotations(chart.getXYPlot());
        chartPanel.setChart(this.chart);

        // Optionally, reapply horizontal and vertical lines if needed
        addHorizontalLine(this.chart, level);
        for (int i = 0; i < level_y_l; i++) {
            addVerticalLine(this.chart, level_y[i]);
        }

    }

    private JFreeChart createChart(XYSeriesCollection[] datasets, double minX, double maxX, double minY, double maxY) {
        int i1=1;
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot",
                "X Axis",
                "Y Axis",
                datasets[0],  // Primary dataset
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );

        XYPlot plot = chart.getXYPlot();
        if(i1==1)
            configurePlot(plot, minX, maxX, minY, maxY);
        else
            configurePlot(plot);

        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK, Color.WHITE, Color.GRAY};
        for (int i = 0; i < datasets.length; i++) {
            plot.setDataset(i, datasets[i]);
            setRenderer(plot, i, colors[i % colors.length]);
        }

        return chart;
    }

    private XYSeries createSeries(double[] xValues, double[] yValues, String label) {
        // Check if the label array is empty and set the label to an empty string
        String seriesLabel = (label != null && !label.isEmpty()) ? label : "";

        XYSeries series = new XYSeries(seriesLabel);
        for (int i = 0; i < xValues.length && i < yValues.length; i++) {
            series.add(xValues[i], yValues[i]);
        }
        return series;
    }

    private void configurePlot(XYPlot plot) {
        plot.getDomainAxis().setRangeWithMargins(-10, 10);
        plot.getRangeAxis().setRangeWithMargins(-10, 10);
        XYLineAnnotation xAxis = new XYLineAnnotation(0, -10, 0, 10);
        XYLineAnnotation yAxis = new XYLineAnnotation(-10, 0, 10, 0);
        plot.addAnnotation(xAxis);
        plot.addAnnotation(yAxis);
    }

    private void configurePlot(XYPlot plot, double minX, double maxX, double minY, double maxY) {
        plot.getDomainAxis().setRange(minX, maxX);
        plot.getRangeAxis().setRange(minY, maxY);

        XYLineAnnotation xAxis = new XYLineAnnotation(0, minY, 0, maxY);
        XYLineAnnotation yAxis = new XYLineAnnotation(minX, 0, maxX, 0);
        plot.addAnnotation(xAxis);
        plot.addAnnotation(yAxis);
    }

    private void setRenderer(XYPlot plot, int datasetIndex, Color color) {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, color);
        plot.setRenderer(datasetIndex, renderer);
    }

    private void addHorizontalLine(JFreeChart chart, double level) {
        XYPlot plot = chart.getXYPlot();
        XYLineAnnotation horizontalLine = new XYLineAnnotation(plot.getDomainAxis().getLowerBound(), level, plot.getDomainAxis().getUpperBound(), level, new BasicStroke(2.0f), Color.black);
        plot.addAnnotation(horizontalLine);
    }

    private void addVerticalLine(JFreeChart chart, double level) {
        XYPlot plot = chart.getXYPlot();
        XYLineAnnotation verticalLine = new XYLineAnnotation(level,plot.getDomainAxis().getLowerBound()-100000, level, plot.getDomainAxis().getUpperBound()+100000, new BasicStroke(2.0f), Color.black);
        plot.addAnnotation(verticalLine);
    }

    // Method to update chart data

    // Method to update chart data
    public void updateChartData(double[] xValues, double[][] yValues, double level, double level_y[], int level_y_l, String ss[]) {
        String ss1=ss[0];
        if (chart == null) {
            createShow("Updated Scatter Plot", xValues, yValues, level, level_y, level_y_l, ss);
            return;
        }

        // Calculate new min and max for x and y values
        double minX = Arrays.stream(xValues).min().orElse(0);
        double maxX = Arrays.stream(xValues).max().orElse(10);
        double minY = Arrays.stream(yValues).flatMapToDouble(Arrays::stream).min().orElse(0);
        double maxY = Arrays.stream(yValues).flatMapToDouble(Arrays::stream).max().orElse(10);

        XYPlot plot = chart.getXYPlot();

        // Update the range of the axes
        plot.getDomainAxis().setRange(minX, maxX);
        plot.getRangeAxis().setRange(minY, maxY);

        // Clear previous annotations and reapply them
        plot.clearAnnotations();
        addAxisAnnotations(plot);

        for (int i = 0; i < yValues.length; i++) {
            XYSeriesCollection dataset = (XYSeriesCollection) plot.getDataset(i);
            XYSeries series = dataset.getSeries(0);
            series.clear();
            for (int j = 0; j < xValues.length && j < yValues[i].length; j++) {
                series.add(xValues[j], yValues[i][j]);
            }
        }

        // Reapply horizontal and vertical lines
        addHorizontalLine(chart, level);
        for (int i = 0; i < level_y_l; i++) {
            addVerticalLine(chart, level_y[i]);
        }

        chartPanel.repaint(); // Repaint chart panel to reflect changes
    }

    private void addAxisAnnotations(XYPlot plot) {
        // Add X and Y axis lines
        XYLineAnnotation xAxis = new XYLineAnnotation(0, plot.getRangeAxis().getLowerBound(), 0, plot.getRangeAxis().getUpperBound());
        XYLineAnnotation yAxis = new XYLineAnnotation(plot.getDomainAxis().getLowerBound(), 0, plot.getDomainAxis().getUpperBound(), 0);
        plot.addAnnotation(xAxis);
        plot.addAnnotation(yAxis);
    }

    private void updateSeriesData(XYSeries series, double[] xValues, double[] yValues) {
        series.clear();
        for (int i = 0; i < xValues.length && i < yValues.length; i++) {
            series.add(xValues[i], yValues[i]);
        }
    }

    public void mainn(double[] xValues, double[][] yyValues, double level,double level_y[],int level_y_l)//(double[] xValues, double[] y0Values, double[] y1Values, double[] y2Values, double[] y3Values,double levell) 
    {
        ScatterPlotExample example = new ScatterPlotExample();
        String st[]=new String[yyValues.length];
        for (int i = 0; i < yyValues.length; i++) 
            st[i]="Data "+i;
        example.createShow("yo",xValues,yyValues, level,level_y,level_y_l,st);
    }

    public void mainn(double[] xValues, double[][] yyValues, double level,double level_y[],int level_y_l, String st[])//(double[] xValues, double[] y0Values, double[] y1Values, double[] y2Values, double[] y3Values,double levell) 
    {
        ScatterPlotExample example = new ScatterPlotExample();
        example.createShow("yo",xValues,yyValues,level,level_y,level_y_l,st);
        //example.createShow("hi",xValues, y0Values, y1Values, y2Values, y3Values,levell);
        //example.createShow("hi",xValues, y0Values, y1Values, y2Values, y3Values,levell);
    }

    public static void main(String[] args) {
        ScatterPlotExample example = new ScatterPlotExample();
        // Example data

        double[] xValues = {1, 2, 3, 4, 5};
        double[] y0Values = {1.1, 2.2, 3.3, 4.4, 5.5};
        double[] y1Values = {2.1, 3.2, 4.3, 5.4, 6.5};
        double[] y2Values = {1.5, 2.5, 3.5, 4.5, 5.5};
        double[] y3Values = {2.5, 3.5, 4.5, 5.5, 6.5};

        double yyValues[][]=new double[y0Values.length][4];
        yyValues[0]=y0Values;yyValues[1]=y1Values;
        yyValues[2]=y2Values;yyValues[3]=y3Values;

        double level=yyValues[0][0];
        String st[]=new String[yyValues.length];
        for (int i = 0; i < yyValues.length; i++) 
            st[i]="Data "+i;
        double level_y[]={1,3,5,7,9,11};//y1Values;
        int level_y_l=level_y.length;

        example.createShow("yo",xValues,yyValues, level,level_y, level_y_l,st);

        //example.createShow("yo",xValues, y0Values, y1Values, y2Values, y3Values,y0Values[0]);
        /*
        Scanner sc = new Scanner(System.in);
        System.out.println("Press enter to update the chart...");
        String ss=sc.nextLine();

        double[] newxValues={-3, -2, -1, 0, 1, 2, 3}; // Initial xValues
        double[] newy0Values= {-5, -6, -7, 5, 6, 7, 8}; // Initial y1Values
        double[] newy1Values= {-11, -10, -9, 9, 10, 11, 12}; // Initial y2Values             
        double[] newy2Values = {-11, -5, -2, 9, 3, -4, 6}; // Initial y2Values
        double[] newy3Values = {-14, -13, -12, 15, 12, 13, 14};

        example.updateChartData(newxValues, newy0Values, newy1Values, newy2Values, newy3Values);
         */
    }

    /*
    ScatterPlotExample example = new ScatterPlotExample(
    "Scatter Plot Example",
    new double[] {-3, -2, -1, 0, 1, 2, 3}, // Initial xValues
    new double[] {3, 2, 1, 0, -1, -2, -3}, // Initial y0Values
    new double[] {-5, -6, -7, 5, 6, 7, 8}, // Initial y1Values
    new double[] {-11, -10, -9, 9, 10, 11, 12}, // Initial y2Values
    new double[] {-14, -13, -12, 15, 12, 13, 14} // Initial y3Values
    );

    // Standard Swing setup
    example.setSize(800, 600);
    example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    example.setVisible(true);

    Scanner sc = new Scanner(System.in);
    System.out.println("Press enter to update the chart...");
    sc.nextLine();

    // Update data from outside the EDT
    example.updateData(
    new double[] {-3, -2, -1, 0, 1, 2, 3}, // New xValues
    new double[] {3, 2, 1, 0, -1, -2, -3}, // New y0Values
    new double[] {-11, -5, -11, -5, 5, 11, 5}, // New y1Values
    new double[] {-5, -6, -7, 5, 6, 7, 8}, // New y2Values
    new double[] {-14, -13, -12, 15, 12, 13, 14} // New y3Values
    );

    }
     */

    /*
    public void updateData(double[] xValues, double[] y0Values, double[] y1Values, double[] y2Values, double[] y3Values) {
    // Ensuring GUI updates are done on the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
    // Update datasets
    dataset0.removeAllSeries();
    dataset0.addSeries(createSeries(xValues, y0Values, "Data0"));

    dataset1.removeAllSeries();
    dataset1.addSeries(createSeries(xValues, y1Values, "Data1"));

    dataset2.removeAllSeries();
    dataset2.addSeries(createSeries(xValues, y2Values, "Data2"));

    dataset3.removeAllSeries();
    dataset3.addSeries(createSeries(xValues, y3Values, "Data3"));

    // Refresh the chart panel
    chartPanel.revalidate();
    chartPanel.repaint();
    });
    }
     */
    /*
    public ScatterPlotExample(String title, double[] xValues, double[] yValues, double[] y2Values) {
    super(title);

    // Create a dataset using XYSeriesCollection for the original line
    XYSeries series = new XYSeries("Data");
    for (int i = 0; i < xValues.length && i < yValues.length; i++) {
    series.add(xValues[i], yValues[i]);
    }

    // Create a dataset for the second line (y2Values vs xValues)
    XYSeries series2 = new XYSeries("Data2");
    for (int i = 0; i < xValues.length && i < y2Values.length; i++) {
    series2.add(xValues[i], y2Values[i]);
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series);

    // Create a new dataset for the second line
    XYSeriesCollection dataset2 = new XYSeriesCollection();
    dataset2.addSeries(series2);

    // Create the chart using ChartFactory for the original line
    JFreeChart chart = ChartFactory.createScatterPlot(
    "Scatter Plot", // Title
    "X Axis",       // X-axis label
    "Y Axis",       // Y-axis label
    dataset,        // Dataset
    PlotOrientation.VERTICAL,
    true,           // Show legend
    true,
    false
    );

    // Get the plot and adjust the range for both axes to include negative values for the original line
    XYPlot plot = chart.getXYPlot();
    plot.getDomainAxis().setRangeWithMargins(-10, 10); // X-axis range (-10 to 10)
    plot.getRangeAxis().setRangeWithMargins(-10, 10);  // Y-axis range (-10 to 10)

    // Draw X-axis and Y-axis lines using XYLineAnnotation for the original line
    XYLineAnnotation xAxis = new XYLineAnnotation(0, -10, 0, 10);
    XYLineAnnotation yAxis = new XYLineAnnotation(-10, 0, 10, 0);
    plot.addAnnotation(xAxis);
    plot.addAnnotation(yAxis);

    // Create renderer for lines connecting points and displaying points
    XYDifferenceRenderer renderer = new XYDifferenceRenderer(Color.GREEN, Color.RED, false);
    plot.setRenderer(renderer);

    // Create renderer for lines connecting points and displaying points for the original line
    //XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    //renderer.setSeriesPaint(0, Color.BLACK); // Set color for the original line
    //plot.setRenderer(renderer);

    // Create a new renderer for the second line
    XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
    renderer2.setSeriesPaint(0, Color.BLUE); // Set color for the second line

    // Add the second line to the existing plot with a new renderer
    plot.setDataset(1, dataset2);
    plot.setRenderer(1, renderer2);

    // Set the chart background and size
    chart.setBackgroundPaint(Color.WHITE);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 600));
    setContentPane(chartPanel);
    }
     */
    /*
    public ScatterPlotExample(String title, double[] xValues, double[] yValues) {
    super(title);

    // Create a dataset using XYSeriesCollection
    XYSeries series = new XYSeries("Data");
    for (int i = 0; i < xValues.length && i < yValues.length; i++) {
    series.add(xValues[i], yValues[i]);
    }
    XYSeriesCollection dataset = new XYSeriesCollection(series);

    // Create the chart using ChartFactory
    JFreeChart chart = ChartFactory.createScatterPlot(
    "Scatter Plot", // Title
    "X Axis",       // X-axis label
    "Y Axis",       // Y-axis label
    dataset,        // Dataset
    PlotOrientation.VERTICAL,
    true,           // Show legend
    true,
    false
    );

    // Get the plot and adjust the range for both axes to include negative values
    XYPlot plot = chart.getXYPlot();
    plot.getDomainAxis().setRangeWithMargins(-10, 10); // X-axis range (-10 to 10)
    plot.getRangeAxis().setRangeWithMargins(-10, 10);  // Y-axis range (-10 to 10)

    // Draw X-axis and Y-axis lines using XYLineAnnotation
    XYLineAnnotation xAxis = new XYLineAnnotation(0, -10, 0, 10);
    XYLineAnnotation yAxis = new XYLineAnnotation(-10, 0, 10, 0);
    plot.addAnnotation(xAxis);
    plot.addAnnotation(yAxis);

    // Create renderer for lines connecting points and displaying points
    XYDifferenceRenderer renderer = new XYDifferenceRenderer(Color.GREEN, Color.RED, false);
    plot.setRenderer(renderer);

    // Set the chart background and size
    chart.setBackgroundPaint(Color.WHITE);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 600));
    setContentPane(chartPanel);
    }
     */

}

/*
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ScatterPlotExample extends JFrame {

public ScatterPlotExample(String title, double[] xValues, double[] yValues) {
super(title);

// Create a dataset using XYSeriesCollection
XYSeries series = new XYSeries("Data");
for (int i = 0; i < xValues.length && i < yValues.length; i++) {
series.add(xValues[i], yValues[i]);
}
XYSeriesCollection dataset = new XYSeriesCollection(series);

// Create the chart using ChartFactory
JFreeChart chart = ChartFactory.createScatterPlot(
"Scatter Plot", // Title
"X Axis",       // X-axis label
"Y Axis",       // Y-axis label
dataset,        // Dataset
PlotOrientation.VERTICAL,
true,           // Show legend
true,
false
);

// Get the plot and adjust the range for both axes to include negative values
XYPlot plot = chart.getXYPlot();
plot.getDomainAxis().setRangeWithMargins(-10, 10); // X-axis range (-10 to 10)
plot.getRangeAxis().setRangeWithMargins(-10, 10);  // Y-axis range (-10 to 10)

// Draw X-axis and Y-axis lines using XYLineAnnotation
XYLineAnnotation xAxis = new XYLineAnnotation(0, -10, 0, 10);
XYLineAnnotation yAxis = new XYLineAnnotation(-10, 0, 10, 0);
plot.addAnnotation(xAxis);
plot.addAnnotation(yAxis);

// Create renderer for lines connecting points and displaying points
XYDifferenceRenderer renderer = new XYDifferenceRenderer(Color.GREEN, Color.RED, false);
plot.setRenderer(renderer);

// Set the chart background and size
chart.setBackgroundPaint(Color.WHITE);
ChartPanel chartPanel = new ChartPanel(chart);
chartPanel.setPreferredSize(new Dimension(800, 600));
setContentPane(chartPanel);
}

public static void main(String[] args) {
// Example data for x and y values, including negative values
double[] xValues = {-3, -2, -1, 0, 1, 2, 3};
double[] yValues = {-4, -3, -2, -1, 0, 1, 4};

SwingUtilities.invokeLater(() -> {
ScatterPlotExample example = new ScatterPlotExample("Scatter Plot Example", xValues, yValues);
example.setSize(800, 600);
example.setLocationRelativeTo(null);
example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
example.setVisible(true);
});
}
}
 */