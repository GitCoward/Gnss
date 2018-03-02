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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
//Âë¸ú×ÙÎó²î¼ÆËã£¬½á¹¹Í¬CurveS
public class Tracking_Error extends Application {

    private static AreaChart<Number, Number> chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public int alpha ;
    public int beta ;
    public double low_point = -12000000;
    public double high_point = 12000000;
    public int offset = 10000;
    private double Bl = 0.1;

    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Parent createContent() {
		xAxis = new NumberAxis(20, 40, 5);
        xAxis.setLabel("X Values");
        yAxis = new NumberAxis(0, 1, 0.1);
        yAxis.setLabel("Y Values");
        chart = new AreaChart(xAxis, yAxis, addData());
        return chart;
    }
    

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ObservableList<AreaChart.Series> addData() {
    	final AreaChart.Series<Number, Number> series1 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series2 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series3 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series4 = new AreaChart.Series<>();
    	final AreaChart.Series<Number, Number> series5 = new AreaChart.Series<>();
    	for(double t=10; t<60;t+=1) {
    		series1.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 2, 0, 0, 1023000.0, Math.PI*40/1000000000.0)));
    		series2.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 2, 0, 0, 10230000.0, Math.PI*40/1000000000.0)));
    		series3.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 1, 10, 5, 0, Math.PI*40/1000000000.0)));
    		series4.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 1, 8, 4, 0, Math.PI*50/1000000000.0)));
    		series5.getData().addAll(new AreaChart.Data<Number, Number>(t, 
    				getResult(t, 1, 5, 2, 0, Math.PI*80/1000000000.0)));
    	}
    	ObservableList<AreaChart.Series> chartData = FXCollections.observableArrayList(
    			new AreaChart.Series("1.023M BPSk", FXCollections.observableArrayList(series1.getData())),
    			new AreaChart.Series("10.23M BPSK", FXCollections.observableArrayList(series2.getData())),
    			new AreaChart.Series("BOC(10,5)", FXCollections.observableArrayList(series3.getData())),
    			new AreaChart.Series("BOC(8,4)", FXCollections.observableArrayList(series4.getData())),
    			new AreaChart.Series("BOC(5,2)", FXCollections.observableArrayList(series5.getData()))
    			);
    	return chartData;
    }
    
    private double getResult(double t, int flag, int alpha, int beta, double band, double delta) {
    	double temp1 = 0;
    	double temp2 = 0;
    	double temp3 = 0;
    	double temp4 = 0;
    	double s1 = 0;
    	double s2 = 0;
    	if(flag == 1) {
	    	for (double f=low_point; f<high_point; f+=offset) {
	    		temp1+=calculate_boc(f+0.1, alpha, beta)*(Math.pow(Math.sin(delta*f), 2))*offset;
				temp2+=f*calculate_boc(f+0.1, alpha, beta)*Math.sin(delta*f)*offset;
				temp3+=calculate_boc(f+0.1, alpha, beta)*(Math.pow(Math.cos(delta*f), 2))*offset;
				temp4+=calculate_boc(f+0.1, alpha, beta)*Math.cos(delta*(f))*offset;
			}
			s1 = (Bl*(1-0.25*Bl*0.02)*temp1)/(t*Math.pow(2*Math.PI*temp2, 2));
			s2 = (1+((temp3)/(0.02*t*(Math.pow(temp4, 2)))));
			return Math.sqrt(s1*s2)*100000000;
    	}else if(flag == 2) {
    		for (double f=low_point; f<high_point; f+=offset) {
	    		temp1+=calculate_bpsk(f+0.1, band)*(Math.pow(Math.sin(delta*f), 2))*offset;
				temp2+=f*calculate_bpsk(f+0.1, band)*Math.sin(delta*f)*offset;
				temp3+=calculate_bpsk(f+0.1, band)*(Math.pow(Math.cos(delta*f), 2))*offset;
				temp4+=calculate_bpsk(f+0.1, band)*Math.cos(delta*(f))*offset;
			}
			s1 = (Bl*(1-0.25*Bl*0.02)*temp1)/(t*Math.pow(2*Math.PI*temp2, 2));
			s2 = (1+((temp3)/(0.02*t*(Math.pow(temp4, 2)))));
			return Math.sqrt(s1*s2)*10000000;
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
    	return (result);
    }
    private double calculate_bpsk(double f, double band) {
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
    	int result = jFileChooser.showSaveDialog(new JFrame("±£´æÍ¼Æ¬"));
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
