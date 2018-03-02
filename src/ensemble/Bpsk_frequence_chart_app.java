package ensemble;

import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

//bpsk频谱曲线，结构同boc频谱曲线
public class Bpsk_frequence_chart_app extends Application {

    private static CurveFittedAreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public double fCarrier ;
    public double band;
    public double low_point;
    public double high_point ;
    public double offset;
    public double Tb;
    public double Max_Fre_Des;

//    @SuppressWarnings("unchecked")
	public Parent createContent() {
		if(fCarrier <= 0) fCarrier = 1000;
		if(band <= 0 ) band = 10230000;
		if(low_point == 0)low_point = -30000000;
		if(high_point == 0) high_point = 30000000;
		if(offset == 0 || offset < 0) offset = 10000;
		Tb = 1/band;
        xAxis = new NumberAxis(low_point, high_point, offset);
		xAxis.setLabel("2PSK时间波形（f/Hz)");
        yAxis = new NumberAxis(-100, -60, 5);
        yAxis.setLabel("Value(V)");
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
    	for(double f=low_point; f<high_point; f+=offset) {
    		double temp = calculate(f+0.1);
    		series.getData().addAll(new XYChart.Data<Number, Number>(f, 10*Math.log10(temp)));
    		if(Max_Fre_Des < temp) Max_Fre_Des = temp;
    	}
    }
    
    private double calculate(double f) {
//    	return Tb/4*(Math.pow(Math.sin(Math.PI*(f+fCarrier)*Tb)
//    			/(Math.PI*(f+fCarrier)*Tb), 2)
//    			+
//    			Math.pow(Math.sin(Math.PI*(f-fCarrier)*Tb)
//    			/(Math.PI*(f-fCarrier)*Tb), 2)
//   				);
    	return 1/(4*band)*Math.pow((Math.sin(Math.PI*f*(1/band))/(Math.PI*f*(1/band))), 2);
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
		//getImage();
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
