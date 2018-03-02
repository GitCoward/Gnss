package ensemble;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
//bpsk自相关曲线，结构同boc曲线
public class Bpsk_relative_chart_app extends Application {

	private static CurveFittedAreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public double fCarrier ;
    public double band;
    public double low_point;
    public double high_point ;
    public double offset;
    public double Tb;
    public static double a = 1000000.0;
//    @SuppressWarnings("unchecked")
	public Parent createContent() {
//		if(fCarrier <= 0) fCarrier = 1000;
		if(band <= 0 ) band = 1023000;
		if(low_point == 0)low_point = -15000000;
		if(high_point == 0) high_point = 15000000;
		if(offset == 0 || offset < 0) offset = 10000;
		Tb = 1/band;
        xAxis = new NumberAxis(-2/a, 2/a, 0.1/a);
		xAxis.setLabel("Bpsk Relative");
        yAxis = new NumberAxis(0, 1.2, 0.1);
        chart = new CurveFittedAreaChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        
        addData(series);
        chart.getData().add(series);
        chart.getStylesheets().add("CurveFittedAreaChart.css");
        return chart;
    }
    
    @SuppressWarnings("unchecked")
	public void addData(XYChart.Series<Number, Number> series) {
    	double max=0;
    	for(double f = low_point; f < high_point; f+=offset) {
    		max+=(calculate(f+0.1)*offset);
		}
    	max = Math.abs(max);
    	System.out.println(max);
    	for(double t=-2/a; t<2/a; t+=0.001/a) {
    		double Re=0;
    		double Im=0;
    		for(double f = -10*a/2; f < 10*a/2; f+=10000) {
        		Re+=(calculate(f+0.1)*Math.cos(2*Math.PI*(f)*t)*10000);
        		Im+=calculate(f+0.1)*Math.sin(2*Math.PI*(f)*t)*10000;
    		}
    		double result = Math.sqrt(Math.pow(Re, 2)+Math.pow(Im, 2));
    		series.getData().addAll(new XYChart.Data<Number, Number>(t, 
        				result/max));
    	}
    }
    
    private double calculate(double f) {
    	return Tb/4*(Math.pow(Math.sin(Math.PI*(f+fCarrier)*Tb)
    			/(Math.PI*(f+fCarrier)*Tb), 2)
    			+
    			Math.pow(Math.sin(Math.PI*(f-fCarrier)*Tb)
    			/(Math.PI*(f-fCarrier)*Tb), 2)
   				);
    }
    
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
//        getImage();
    }
    
    public void start_save(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
//        primaryStage.show();
        getImage();
    }
    
    

    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
    	launch(args);
    }
    
    
    public static void getImage() {
    	JFileChooser jFileChooser = new JFileChooser();
    	int result = jFileChooser.showSaveDialog(new JFrame("保存图片"));
    	File file = jFileChooser.getSelectedFile();
    	WritableImage image = chart.snapshot(new SnapshotParameters(), null);
//    	System.out.println(image);
    	try {
    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
//    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", preview);
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
}
