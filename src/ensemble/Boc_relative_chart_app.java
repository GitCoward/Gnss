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
//boc自相关曲线
public class Boc_relative_chart_app extends Application {

    private static CurveFittedAreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int alpha ;
    public int beta ;
    public double low_point ;
    public double high_point ;
    public double offset ;
    public double band = 24000000;
    public int a = 1000000;
    public double r;
    public double Gs;
//    @SuppressWarnings("unchecked")
	public Parent createContent() {
		//数值初始化
		if(alpha <= 0) alpha = 10;
		if(beta <= 0) beta = 5;
		if(low_point == 0) low_point = -0.5/a;
		if(high_point == 0) high_point = 0.5/a;
		if(offset <= 0) offset = 0.1/a;
        xAxis = new NumberAxis(low_point, high_point, offset);
		xAxis.setLabel("Boc Relative");
        yAxis = new NumberAxis(0, 1.2, 0.1);
        chart = new CurveFittedAreaChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        //添加数据
        addData(series);
        chart.getData().add(series);
        //图标样式渲染
        chart.getStylesheets().add("CurveFittedAreaChart.css");
        return chart;
    }
    
    @SuppressWarnings("unchecked")
	public void addData(XYChart.Series<Number, Number> series) {
    	int count=0;
    	double max=0;
    	//计算t=0时的自相关数值，用于归一化
    	for(double f = -band/2; f < band/2; f+=1000) {
    		max+=(calculate(f+0.1, alpha, beta)*1000);
		}
    	//最大值
    	max = Math.abs(max);
    	for(double t=low_point; t<high_point; t+=0.0005/a) {
    		double Re=0;
    		double Im=0;
    		for(double f = -band/2+0.2; f < band/2+0.2; f+=10000) {
        		Re+=(calculate(f+0.1, alpha, beta)*Math.cos(2*Math.PI*f*t)*10000);
        		Im+=calculate(f+0.1, alpha, beta)*Math.sin(2*Math.PI*f*t)*10000;
    		}
    		double result = Math.sqrt(Math.pow(Re, 2)+Math.pow(Im, 2));
    		series.getData().addAll(new XYChart.Data<Number, Number>(t, 
        				result/max));
    	}
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
    
    //图片保存
    public static void getImage() {
    	JFileChooser jFileChooser = new JFileChooser();
    	int result = jFileChooser.showSaveDialog(new JFrame("保存图片"));
    	File file = jFileChooser.getSelectedFile();
    	WritableImage image = chart.snapshot(new SnapshotParameters(), null);
    	try {
    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
}
