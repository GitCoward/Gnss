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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
//bocʱ������
public class Boc_time_chart_app extends Application {

    private static AreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int alpha ;
    public int beta ;
    public String sequence;
    public double high_point ;
    public int Ts ;
    //����ͼ��
//    @SuppressWarnings("unchecked")
	public Parent createContent() {
		//��ֵ��ʼ��
		if(alpha == 0 || alpha < 0) alpha = 10;
		if(this.sequence == null || this.sequence.length() == 0) this.sequence = ""+10100;
		if(beta == 0|| beta < 0) beta = 5;
		if(high_point == 0) high_point = 15000000;
		if(Ts == 0 || Ts < 0) Ts = 30;
		int temp = (int)2*alpha/beta;
        int key = temp>0?temp:1;
        String arr[] = this.sequence.split("");
        int [] array = new int[arr.length];
        //���еĻ��
        for(int i = 0; i < arr.length; i++) {
        	array[i] = (Integer.parseInt(arr[i])==0)?-1:1;
        }
        
        xAxis = new NumberAxis(0, key*Ts*array.length, Ts);
		xAxis.setLabel("Time field curve(Ts)");
        yAxis = new NumberAxis(-2, 2, 1);
        yAxis.setLabel("Value(V)");
        chart = new AreaChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        //���ݵ����
        addData(series, key, array);
        chart.getData().add(series);
        //ͼ�����Ⱦ
        chart.getStylesheets().add("Boc_time.css");
        return chart;
    }
    
    @SuppressWarnings("unchecked")
	public void addData(XYChart.Series<Number, Number> series, int key, int[] array) {
    	
    	int j = 1;
    	int k = array[0];
    	int i = 0;
    	for(int T = 1; T<= key*Ts*array.length; T+=1) {
    		series.getData().addAll(new XYChart.Data<Number, Number>(T, 
    				j*k));
    		if(T%Ts==0) j *= -1;
    		if(T%(key*30)==0) {
    			k = array[i++];
    		}
    	}
    	
    }
    //���߻��Ƽ���ʾ
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    //���߻��Ƽ�����
    public void start_save(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        getImage();
    }
    
    public static void main(String[] args) {
    	launch(args);
    }
    //ͼƬ����ľ���ʵ��
    public static void getImage() {
    	JFileChooser jFileChooser = new JFileChooser();
    	int result = jFileChooser.showSaveDialog(new JFrame("����ͼƬ"));
    	File file = jFileChooser.getSelectedFile();
    	WritableImage image = chart.snapshot(new SnapshotParameters(), null);
    	try {
    		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    	}catch (Exception e) {
		}
    }
}