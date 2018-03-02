package ensemble;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.sun.org.apache.regexp.internal.recompile;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.AreaChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import sun.tools.jar.resources.jar;
//控制产生多径曲线
public class MultiPath extends Application {

    private static AreaChart<Number, Number> chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int alpha ;
    public int beta ;
    public double low_point ;
    public double high_point ;
    public double offset ;
    public double band = 10230000;
    public double a = 1000000000.0;
    public double r;
    public double Gs;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Parent createContent() {
		if(alpha <= 0) alpha = 10;
		if(beta <= 0) beta = 5;
		if(low_point == 0) low_point = -1/a;
		if(high_point == 0) high_point = 252/a;
		if(offset <= 0) offset = 1/a;
		xAxis = new NumberAxis(0, 250/a, 50/a);
        xAxis.setLabel("X Values");
        yAxis = new NumberAxis();
        yAxis.setLabel("Y Values");
        chart = new AreaChart(xAxis, yAxis, addData());
        return chart;
    }
    //数据添加
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ObservableList<AreaChart.Series> addData() {
    	final AreaChart.Series<Number, Number> series1 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series2 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series3 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series4 = new AreaChart.Series<>();
    	
    	for(double t=low_point; t<high_point;t+=offset) {
    		series1.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 1)));
    		series2.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 2)));
    		series3.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 3)));
    		series4.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 4)));
    		
    	}
    	ObservableList<AreaChart.Series> chartData = FXCollections.observableArrayList(
    			new AreaChart.Series("odd_BOC(10,5)", FXCollections.observableArrayList(series1.getData())),
    			new AreaChart.Series("even_BOC(10,5)", FXCollections.observableArrayList(series2.getData())),
    			new AreaChart.Series("odd_BPSK", FXCollections.observableArrayList(series3.getData())),
    			new AreaChart.Series("even_BPSK", FXCollections.observableArrayList(series4.getData()))
    			
    			);
    	return chartData;
    }
    
    private double getResult(double t, int flag) {
//    	return Math.pow(getRs_average(t-delta/2), 2)-Math.pow(getRs_average(t+delta/2), 2);
    	if(flag ==1) {
    		double temp1 = 0; 
    		double temp2 = 0;
    		for(double f = -12000000; f<12000000; f+=10000) {
    			temp1 += calculate_boc(f+0.1, 10, 5)*Math.sin(Math.PI*f*30/a)*Math.sin(2*Math.PI*f*t)*10000;
    			temp2 += f*calculate_boc(f+0.1, 10, 5)*Math.sin(Math.PI*f*30/a)*(1-0.25*Math.cos(2*Math.PI*f*t))*10000;
    		}
    		return (-0.25*temp1)/(2*Math.PI*temp2);
    	}
    	if(flag == 2) {
    		double temp1 = 0; 
    		double temp2 = 0;
    		for(double f = -12000000; f<12000000; f+=10000) {
    			temp1 += calculate_boc(f+0.1, 10, 5)*Math.sin(Math.PI*f*30/a)*Math.sin(2*Math.PI*f*t)*10000;
    			temp2 += f*calculate_boc(f+0.1, 10, 5)*Math.sin(Math.PI*f*30/a)*(1+0.25*Math.cos(2*Math.PI*f*t))*10000;
    		}
    		return (0.25*temp1)/(2*Math.PI*temp2);
    	}
    	if(flag == 3) {
    		double temp1 = 0; 
    		double temp2 = 0;
    		for(double f = -12000000; f<12000000; f+=10000) {
    			temp1 += calculate_bpsk(f+0.1)*Math.sin(Math.PI*f*30/a)*Math.sin(2*Math.PI*f*t)*10000;
    			temp2 += f*calculate_bpsk(f+0.1)*Math.sin(Math.PI*f*30/a)*(1-0.25*Math.cos(2*Math.PI*f*t))*10000;
    		}
    		return (-0.25*temp1)/(2*Math.PI*temp2);
    	}
    	if(flag == 4) {
    		double temp1 = 0; 
    		double temp2 = 0;
    		for(double f = -12000000; f<12000000; f+=10000) {
    			temp1 += calculate_bpsk(f+0.1)*Math.sin(Math.PI*f*30/a)*Math.sin(2*Math.PI*f*t)*10000;
    			temp2 += f*calculate_bpsk(f+0.1)*Math.sin(Math.PI*f*30/a)*(1+0.25*Math.cos(2*Math.PI*f*t))*10000;
    		}
    		return (0.25*temp1)/(2*Math.PI*temp2);
    	}else {
    		return 0;
    	}
    }
    
    private double calculate_boc(double f, int a, int b) {
    	double result = 0;
    	if((a*2/b) % 2==0) {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.sin(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	else {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.cos(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	return result;
    }
    
    private double calculate_bpsk(double f) {
    	return 1/(4*band)*Math.pow((Math.sin(Math.PI*f*(1/band))/(Math.PI*f*(1/band))), 2);
    }
    //曲线绘制及显示
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    //曲线绘制及保存
    public void start_save(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        getImage();
    }
    
    

  
    public static void main(String[] args) {
    	launch(args);
    }
    
    
    public static void getImage() {
    	JFileChooser jFileChooser = new JFileChooser();
    	int result = jFileChooser.showSaveDialog(new JFrame("保存图片"));
    	File file = jFileChooser.getSelectedFile();
//    	File file = new File("src/MultiPath.png");
    	WritableImage image = chart.snapshot(new SnapshotParameters(), null);
    	try {
    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
//    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", preview);
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
}
