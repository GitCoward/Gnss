package ensemble;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

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
//S曲线
public class CurveS extends Application {

    private static AreaChart<Number, Number> chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int alpha ;
    public int beta ;
    public double low_point ;
    public double high_point ;
    public double offset ;
    public double band = 30000000;
    public double a = 1000000000.0;
    public double r;
    public double Gs;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Parent createContent() {
		//数值初始化
		if(alpha <= 0) alpha = 10;
		if(beta <= 0) beta = 5;
		if(low_point == 0) low_point = -60/a;
		if(high_point == 0) high_point = 60/a;
		if(offset <= 0) offset = 1/a;
		xAxis = new NumberAxis(-60/a, 60/a, 10/a);
        xAxis.setLabel("X Values");
        yAxis = new NumberAxis();
        yAxis.setLabel("Y Values");
        chart = new AreaChart(xAxis, yAxis, addData());
        return chart;
    }
    //数据添加，共五组数据，五个曲线
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ObservableList<AreaChart.Series> addData() {
    	final AreaChart.Series<Number, Number> series1 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series2 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series3 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series4 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series5 = new AreaChart.Series<>();
    	for(double t=low_point; t<high_point;t+=offset) {
    		series1.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 20/a)));
    		series2.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 30/a)));
    		series3.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 40/a)));
    		series4.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 50/a)));
    		series5.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 60/a)));
    	}
    	ObservableList<AreaChart.Series> chartData = FXCollections.observableArrayList(
    			new AreaChart.Series("1", FXCollections.observableArrayList(series1.getData())),
    			new AreaChart.Series("2", FXCollections.observableArrayList(series2.getData())),
    			new AreaChart.Series("3", FXCollections.observableArrayList(series3.getData())),
    			new AreaChart.Series("4", FXCollections.observableArrayList(series4.getData())),
    			new AreaChart.Series("5", FXCollections.observableArrayList(series5.getData()))
    			);
    	return chartData;
    }
    
    private double getResult(double t, double delta) {
    	return Math.pow(getRs_average(t-delta/2), 2)-Math.pow(getRs_average(t+delta/2), 2);
    }
    //计算带限后自相关
    private double getRs_average(double t) {
    	double max=0;
    	//计算t=0时的自相关值，用于归一化
    	for(double f = -band/2; f < band/2; f+=10000) {
    		max+=(calculate(f+0.1, alpha, beta)*10000);
		}
    	max = Math.abs(max);
    	double Re=0;
    	double Im=0;
    	for(double f = -band/2; f < band/2; f+=10000) {
        	Re+=(calculate(f+0.1, alpha, beta)*Math.cos(2*Math.PI*f*t)*10000);
        	Im+=calculate(f+0.1, alpha, beta)*Math.sin(2*Math.PI*f*t)*10000;
    	}
    	double result = Math.sqrt(Math.pow(Re, 2)+Math.pow(Im, 2));
    	return result/max;
    }
    
    
    public double calculate(double f, int a, int b) {
    	double result = 0;
    	if((a*2/b) % 2==0) {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.sin(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	else {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.cos(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	return result;
    }
    //绘制曲线并显示
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    //绘制曲线并保存
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
    	WritableImage image = chart.snapshot(new SnapshotParameters(), null);
    	try {
    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
//    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", preview);
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
}
