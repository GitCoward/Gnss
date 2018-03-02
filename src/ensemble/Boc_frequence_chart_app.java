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
//bocƵ������
public class Boc_frequence_chart_app extends Application {

    private static CurveFittedAreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int alpha ;
    public int beta ;
    public double low_point ;
    public double high_point ;
    public int offset ;
    public double Max_Fre_Des;

//    @SuppressWarnings("unchecked")
    //����chart����Ⱦ��ʽ
	public Parent createContent() {
		//ֵ��ʼ��
		//alphaĬ��ֵ8
		if(alpha == 0 || alpha < 0) alpha = 8;
		//betaĬ��ֵ4
		if(beta == 0 || beta < 0) beta = 4;
		//������Ĭ����Сֵ
		if(low_point == 0) low_point = -15000000;
		//������Ĭ�����ֵ
		if(high_point == 0) high_point = 15000000;
		//Ĭ�ϻ�����ͼ��
		if(offset == 0 || offset < 0) offset = 10000;
		//ͼ������
        xAxis = new NumberAxis(low_point, high_point, offset);
		xAxis.setLabel("Frequence offset from Center Frequence(Hz)");
        yAxis = new NumberAxis(-100, -60, 5);
        yAxis.setLabel("Power Spectrum Density(dbW/Hz)");
        chart = new CurveFittedAreaChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        //�������
        addData(series);
        chart.getData().add(series);
        //ͼ��������Ⱦ
        chart.getStylesheets().add("CurveFittedAreaChart.css");
        return chart;
    }
    
    @SuppressWarnings("unchecked")
	public void addData(XYChart.Series<Number, Number> series) {
    	Max_Fre_Des = -100;
    	//�������
    	for(double f = low_point+2; f < high_point+2; f+=offset) {
    		double temp = calculate(f+0.1, alpha, beta);
    		series.getData().addAll(new XYChart.Data<Number, Number>(f, 
    				temp));
    		if(Max_Fre_Des < temp) Max_Fre_Des = temp;
    	}
    	
    }
    
    private double calculate(double f, int a, int b) {
    	double result = 0;
    	if((a*2/b) % 2==0) {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.sin(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	else {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.cos(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	return 10 * Math.log10(result);
    }
    
    //�������߲���ʾ
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    //���»������߲�����ͼƬ
    public void start_save(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        getImage();
    }
    
    public static void main(String[] args) {
    	launch(args);
    }
    //ͼƬ����ķ���
    public static void getImage() {
    	JFileChooser jFileChooser = new JFileChooser();
    	int result = jFileChooser.showSaveDialog(new JFrame("����ͼƬ"));
    	File file = jFileChooser.getSelectedFile();
    	WritableImage image = chart.snapshot(new SnapshotParameters(), null);
    	try {
    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    	}catch (Exception e) {}
    }
}
