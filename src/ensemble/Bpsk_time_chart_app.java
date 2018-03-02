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
//bpsk时域曲线，结构同boc时域曲线
public class Bpsk_time_chart_app extends Application {

    private static CurveFittedAreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int Nf ;
    public double possibility ;
    public double NTb ;
    public double high_point ;
    public double offset ;
    private Random random;

//    @SuppressWarnings("unchecked")
	public Parent createContent() {
		if(Nf == 0 || Nf < 0) Nf = 2;
		if(possibility == 0 || possibility < 0) possibility = 0.5;
		if(NTb == 0) NTb = 5;
		if(high_point == 0) high_point = 1000;
		if(offset == 0 || offset < 0) offset = 200;
		random = new Random();
        xAxis = new NumberAxis(0, Nf*NTb*offset/2, offset);
		xAxis.setLabel("2PSK时间波形（f/Hz)");
        yAxis = new NumberAxis(-1.2, 1.2, 0.2);
        yAxis.setLabel("Value(V)");
        chart = new CurveFittedAreaChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        System.out.println(Nf);
        
        addData(series);
        chart.getData().add(series);
//        String curveFittedChartCss = CurveChartApp.class.getResource("reso//CurveFittedAreaChart.css").toExternalForm();
        chart.getStylesheets().add("CurveFittedAreaChart.css");
        return chart;
    }
    
    @SuppressWarnings("unchecked")
	public void addData(XYChart.Series<Number, Number> series) {
    	int x = 0;
    	int a = 1;
    	for(int i=0; i<NTb; i++) {
    		for(int j=0; j<Nf; j++) {
    			for(double f=0; f<offset; f++) {
    				series.getData().addAll(new XYChart.Data<Number, Number>(
    						(x++)/2, a*Math.sin((f/offset) * 2 * Math.PI)));
    			}
    		}
    		a *= generator();
    	}
    	
    }
    
    private int generator() {
    	double value = random.nextDouble();
    	double gate = possibility;
    	if(value<=gate) {
    		return 1;
    	}else {
    		return -1;
    	}
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
