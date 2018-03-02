package ensemble.samplepage;

import ensemble.PlatformFeatures;
import ensemble.SampleInfo;
import ensemble.SampleInfo.URL;
import ensemble.Tracking_Error;
import ensemble.generated.Samples;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static ensemble.samplepage.SamplePage.INDENT;
import static ensemble.samplepage.SamplePageContent.title;

import java.awt.FileDialog;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import ensemble.ExcelExporter;
import ensemble.CreateTable;
import ensemble.Boc_evaluate;
import ensemble.Boc_frequence_chart_app;
import ensemble.Boc_relative_chart_app;
import ensemble.Boc_time_chart_app;
import ensemble.Bpsk_evaluate;
import ensemble.Bpsk_frequence_chart_app;
import ensemble.Bpsk_relative_chart_app;
import ensemble.Bpsk_time_chart_app;
import ensemble.CurveS;
import ensemble.MultiPath;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;


public class Description extends VBox {
	private static final Image ORANGE_ARROW = new Image("orange-arrrow.png");
    private SamplePage samplePage;
    private final VBox relatedSamples;
    
    private Text alpha;
    private TextField alphaValue;
    
    private Text beta;
    private TextField betaValue;
    
    private Text low;
    private TextField lowValue;
    
    private Text high;
    private TextField highValue;
    
    private Text offset;
    private TextField offsetValue;
    
    
    
    private Button paint;
    private Button save;
    private Button calculate;
    private Button tabs;
    
    private Button Fre_Offset;
    private Button Max_Power_Des;
    private Button Per_Band;
    private Button Out_Band_Loss;    
    private Button Beta_2;
    private Button Eff_Band;
    private Button Lamda;
    private Button Iso_Cof1;
    private Button Iso_Cof2;
    private Button Iso_Cof3;
    private Button TrackErr;
    private Text Fre_Offset_Value;
    private Text Max_Power_Des_Value;
    private Text Per_Band_Value;
    private Text Out_Band_Loss_Value;
    private Text Beta_2_Value;
    private Text Eff_Band_Value;
    private Text Lamda_Value;
    private Text Iso_Cof_Value1;
    private Text Iso_Cof_Value2;
    private Text Iso_Cof_Value3;
    private Text TrackErr_Value;
    
    private Boc_evaluate boc_evaluate;
    private Bpsk_evaluate bpsk_evaluate;
    private Boc_frequence_chart_app boc_frequence;
    private Boc_time_chart_app boc_time;
    private Boc_relative_chart_app boc_relative;
    private CurveS curveS;
    private Tracking_Error tracking_Error;
    private MultiPath multiPath;
    private Bpsk_time_chart_app bpsk_time;
    private Bpsk_frequence_chart_app bpsk_frequence;
    private Bpsk_relative_chart_app bpsk_relative;
    private ScrollPane scrollPane;
    
    public String [] boc_value = new String[11];
    public String [] bpsk_value = new String[10];

    public Description(final SamplePage samplePage) {
        this.samplePage = samplePage;
        getStyleClass().add("sample-page-box");

        boc_evaluate = new Boc_evaluate();
        bpsk_evaluate = new Bpsk_evaluate();
        boc_frequence = new Boc_frequence_chart_app();
        boc_time = new Boc_time_chart_app();
        boc_relative = new Boc_relative_chart_app();
        curveS = new CurveS();
        tracking_Error = new Tracking_Error();
        multiPath = new MultiPath();
        bpsk_time = new Bpsk_time_chart_app();
        bpsk_frequence = new Bpsk_frequence_chart_app();
        bpsk_relative = new Bpsk_relative_chart_app();
        //各个曲线实例化
        boc_evaluate.boc_frequence = this.boc_frequence;
        boc_evaluate.boc_time = this.boc_time;
        boc_evaluate.boc_relative = this.boc_relative;
        boc_evaluate.bpsk_frequence = this.bpsk_frequence;
        bpsk_evaluate.bpsk_frequence = this.bpsk_frequence;
        bpsk_evaluate.bpsk_time = this.bpsk_time;
        bpsk_evaluate.bpsk_relative = this.bpsk_relative;
        
        

        // Add View Source Hyperlink
        Hyperlink sourceBtn = new Hyperlink("VIEW SOURCE");
        sourceBtn.getStyleClass().add("sample-page-box-title");
        sourceBtn.setGraphic(new ImageView(ORANGE_ARROW));
        sourceBtn.setContentDisplay(ContentDisplay.RIGHT);
        sourceBtn.setOnAction((ActionEvent ev) -> {
            samplePage.pageBrowser.goToPage(samplePage.getUrl().replaceFirst("sample://", "sample-src://"));
        });
        
        
        
        scrollPane = new ScrollPane();
        
        GridPane temp1 = new GridPane();
        //需要评估的各项参数按钮及计算结果文本框实例化
        Fre_Offset = new Button("            距频带中心频偏");
        Max_Power_Des = new Button("    	 主瓣最大功率密度");
        Per_Band = new Button("        	90%功率带宽");
        Out_Band_Loss = new Button("      	 	      带外损失");
        Beta_2 = new Button("          	   均方根带宽");
        Eff_Band = new Button("          	有效矩形带宽");
        Lamda = new Button("     	               剩余功率");
        Iso_Cof1 = new Button("      与自身频谱隔离系数");
        Iso_Cof2 = new Button("   与1.023MHz隔离系数");
        Iso_Cof3 = new Button("与BOC(10,5)的隔离系数");
        TrackErr = new Button("         	    码跟踪误差");
        Fre_Offset_Value = new Text("");
        Max_Power_Des_Value = new Text("");
        Per_Band_Value = new Text("");
        Out_Band_Loss_Value = new Text("");
        Beta_2_Value = new Text("");
        Eff_Band_Value = new Text("");
        Lamda_Value = new Text("");
        Iso_Cof_Value1 = new Text("");
        Iso_Cof_Value2 = new Text("");
        Iso_Cof_Value3 = new Text("");
        TrackErr_Value = new Text("");
        ColumnConstraints left1 = new ColumnConstraints();
        left1.setPercentWidth(40);
        ColumnConstraints right1 = new ColumnConstraints();
        right1.setPercentWidth(30);
        //各项参数添加到显示界面
        temp1.getColumnConstraints().addAll(left1, right1);
        temp1.add(Fre_Offset, 0, 0);
        temp1.add(Max_Power_Des,0, 1);
//        temp1.add(Per_Band, 0, 2);
//        temp1.add(Out_Band_Loss, 0, 3);
        temp1.add(Beta_2,0, 2);
        temp1.add(Eff_Band, 0, 3);
        temp1.add(Lamda, 0, 4);
        temp1.add(Iso_Cof1, 0, 5);
        temp1.add(Iso_Cof2, 0, 6);
        temp1.add(Iso_Cof3, 0, 7);
        temp1.add(TrackErr, 0, 8);
        temp1.add(Fre_Offset_Value, 1, 0);
        temp1.add(Max_Power_Des_Value, 1, 1);
//        temp1.add(Per_Band_Value, 1, 2);
//        temp1.add(Out_Band_Loss_Value, 1, 3);
        temp1.add(Beta_2_Value, 1, 2);
        temp1.add(Eff_Band_Value, 1, 3);
        temp1.add(Lamda_Value, 1, 4);
        temp1.add(Iso_Cof_Value1, 1, 5);
        temp1.add(Iso_Cof_Value2, 1, 6);
        temp1.add(Iso_Cof_Value3, 1, 7);
        temp1.add(TrackErr_Value, 1, 8);
        
        scrollPane.setContent(temp1);
        
        alpha = title("Alpha");
        beta = title("Beta");
        alphaValue = new TextField();
        alphaValue.setPromptText("default:10");
        betaValue = new TextField();
        betaValue.setPromptText("default:5");
        
        
        
        low = title("LowPoint");
        high = title("HighPoint");
        offset = title("Offset");
        lowValue = new TextField();
        highValue = new TextField();
        offsetValue = new TextField();
        lowValue.setPromptText("default:-15M");
        highValue.setPromptText("default:15M");
        offsetValue.setPromptText("default:10K");
        //点击以绘制曲线
        paint = new Button("绘制");
        paint.setOnAction((ActionEvent e)->{
        	String name = samplePage.sampleInfoProperty.get().name;
        	switch(name) {
        		case("BOC-时域"):
	        		setValue_Boc_time();
	            	try{
	            		low.setText("Sequence");
	            		lowValue.setPromptText("01序列");
	            		offset.setText("Ts");
	            		offsetValue.setPromptText("defalut:30");
	            		boc_time.start(new Stage());
	            	}catch(Exception a) {a.printStackTrace();}
	            	break;
        		case("BOC-频域"):
	        		setValue();
	        		try{
	        			alpha.setText("Alpha");
	                    beta.setText("Beta");
	                    alphaValue.setPromptText("default:10");
	                    betaValue.setPromptText("default:5");
	                    low.setText("lowPoint");
	                    high.setText("HighPoint");
	                    offset.setText("offset");
	                    lowValue.setPromptText("default:-15M");
	                    highValue.setPromptText("default:15M");
	                    offsetValue.setPromptText("default:10K");
	            		boc_frequence.start(new Stage());
	            	}catch(Exception a) {a.printStackTrace();}
	        		break;
        		case("BOC-自相关"):
        			setValue_Boc_time();
	        		try{
	            		low.setText("Sequence");
	            		lowValue.setPromptText("01序列");
	            		offset.setText("Ts");
	            		offsetValue.setPromptText("defalut:30");
	            		boc_relative.start(new Stage());
	            	}catch(Exception a) {a.printStackTrace();}
	        		break;
        		case("Curve-S"):
        			try {
    					curveS.start(new Stage());
    				} catch (Exception e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        			break;
        		case("Tracking_Error"):
        			try {
    					tracking_Error.start(new Stage());
    				} catch (Exception e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        			break;
        		case("MultiPath"):
        			try {
    					multiPath.start(new Stage());
    				} catch (Exception e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        			break;
        		case("BPSK-时域"):
	        		setValue_Bpsk_time();
	        		try{
	        			alpha.setText("Nf");
	        			alphaValue.setPromptText("defalut:2");
	        			beta.setText("possibility");
	        			betaValue.setPromptText("defalut:0.5");
	            		low.setText("NTb");
	            		lowValue.setPromptText("default:5");
	            		offsetValue.setPromptText("defalut:200");
	            		highValue.setPromptText("defalut:1000");
	            		bpsk_time.start(new Stage());
	            	}catch(Exception a) {a.printStackTrace();}
	        		break;
        		case("BPSK-频域"):
	        		setValue_Bpsk_frequence();
	        		try{
	        			alpha.setText("fCarrier");
	        			alphaValue.setPromptText("defalut:10.23M");
	        			beta.setText("band");
	        			betaValue.setPromptText("defalut:1000");
	        			low.setText("lowPoint");
	            		lowValue.setPromptText("default:-30000000");
	            		offsetValue.setPromptText("defalut:10000");
	            		highValue.setPromptText("defalut:30000000");
	            		bpsk_frequence.start(new Stage());
	            	}catch(Exception a) {a.printStackTrace();}
	        		break;
        		case("BPSK-自相关"):
        			setValue_Bpsk_frequence();
	        		try{
	        			alpha.setText("fCarrier");
	        			alphaValue.setPromptText("defalut:1000");
	        			beta.setText("band");
	        			betaValue.setPromptText("defalut:10230000");
	        			low.setText("lowPoint");
	            		lowValue.setPromptText("default:-15000000");
	            		offsetValue.setPromptText("defalut:10000");
	            		highValue.setPromptText("defalut:15000000");
	            		bpsk_relative.start(new Stage());
	            	}catch(Exception a) {a.printStackTrace();}
	        		break;
        	}
        });
        //点击以保存曲线图片
        save = new Button("保存");
        save.setOnAction((ActionEvent e)->{
        	String name = samplePage.sampleInfoProperty.get().name;
        	switch(name) {
        	case("BOC-时域"):
        		setValue();
        		System.out.println(boc_time.alpha +" "+boc_time.beta);
            	try{
            		alpha.setText("Alpha");
                    beta.setText("Beta");
                    alphaValue.setPromptText("default:10");
                    betaValue.setPromptText("default:5");
                    low.setText("lowPoint");
                    high.setText("HighPoint");
                    offset.setText("offset");
                    lowValue.setPromptText("default:-15M");
                    highValue.setPromptText("default:15M");
                    offsetValue.setPromptText("default:10K");
            		boc_time.start_save(new Stage());
            	}catch(Exception a) {a.printStackTrace();}
            	break;
    		case("BOC-频域"):
        		setValue_Boc_time();
        		try{
            		low.setText("Sequence");
            		lowValue.setPromptText("01序列");
            		offset.setText("Ts");
            		offsetValue.setPromptText("defalut:30");
            		boc_frequence.start_save(new Stage());
            	}catch(Exception a) {a.printStackTrace();}
        		break;
    		case("BOC-自相关"):
    			setValue_Boc_time();
	    		try{
	        		low.setText("Sequence");
	        		lowValue.setPromptText("01序列");
	        		offset.setText("Ts");
	        		offsetValue.setPromptText("defalut:30");
	        		boc_relative.start_save(new Stage());
	        	}catch(Exception a) {a.printStackTrace();}
	    		break;
    		case("Curve-S"):
    			try {
					curveS.start_save(new Stage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			break;
    		case("Tracking_Error"):
    			try {
					tracking_Error.start_save(new Stage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			break;
    		case("MultiPath"):
    			try {
					multiPath.start_save(new Stage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			break;
    		case("BPSK-时域"):
        		setValue_Bpsk_time();
        		try{
        			alpha.setText("Nf");
        			alphaValue.setPromptText("defalut:2");
        			beta.setText("possibility");
        			betaValue.setPromptText("defalut:0.5");
            		low.setText("NTb");
            		lowValue.setPromptText("default:5");
            		offsetValue.setPromptText("defalut:200");
            		highValue.setPromptText("defalut:1000");
            		bpsk_time.start_save(new Stage());
            	}catch(Exception a) {a.printStackTrace();}
        		break;
    		case("BPSK-频域"):
        		setValue_Bpsk_frequence();
        		try{
        			alpha.setText("fCarrier");
        			alphaValue.setPromptText("defalut:1000");
        			beta.setText("band");
        			betaValue.setPromptText("defalut:200");
        			low.setText("lowPoint");
            		lowValue.setPromptText("default:-2000");
            		offsetValue.setPromptText("defalut:200");
            		highValue.setPromptText("defalut:2000");
            		bpsk_frequence.start_save(new Stage());
            	}catch(Exception a) {a.printStackTrace();}
        		break;
    		case("BPSK-自相关"):
    			setValue_Bpsk_frequence();
	    		try{
	    			alpha.setText("fCarrier");
	    			alphaValue.setPromptText("defalut:1000");
	    			beta.setText("band");
	    			betaValue.setPromptText("defalut:200");
	    			low.setText("lowPoint");
	        		lowValue.setPromptText("default:-2000");
	        		offsetValue.setPromptText("defalut:200");
	        		highValue.setPromptText("defalut:2000");
	        		bpsk_relative.start_save(new Stage());
	        	}catch(Exception a) {a.printStackTrace();}
	    		break;
        	}
        });
        //各个按钮的点击时间，也就是各个单项参数的评估
		Lamda.setOnAction((ActionEvent e)->{
			Lamda_Value.setText(""+10*Math.log10(boc_evaluate.getLamda()));
		});
		Fre_Offset.setOnAction((ActionEvent e)->{
			Fre_Offset_Value.setText("±"+Math.abs(boc_evaluate.getFre_Off_Value()));
		});
		Max_Power_Des.setOnAction((ActionEvent e)->{
			Max_Power_Des_Value.setText(""+boc_evaluate.getMax_Fre_Des());
		});
		Per_Band.setOnAction((ActionEvent e)->{
			Per_Band_Value.setText("");
		});
		Out_Band_Loss.setOnAction((ActionEvent e)->{
			Out_Band_Loss_Value.setText("");
		});
		Beta_2.setOnAction((ActionEvent e)->{
			Beta_2_Value.setText(""+boc_evaluate.getBeta_2());
		});
		Eff_Band.setOnAction((ActionEvent e)->{
			Eff_Band_Value.setText(""+boc_evaluate.getEff_Band());
		});
		Iso_Cof1.setOnAction((ActionEvent e)->{
			Iso_Cof_Value1.setText(""+boc_evaluate.getIso_Cof1());
		});
		Iso_Cof2.setOnAction((ActionEvent e)->{
			Iso_Cof_Value2.setText(""+boc_evaluate.getIso_Cof2());
		});
		Iso_Cof3.setOnAction((ActionEvent e)->{
			Iso_Cof_Value3.setText(""+boc_evaluate.getIso_Cof3());
		});
		TrackErr.setOnAction((ActionEvent e)->{
			TrackErr_Value.setText(""+boc_evaluate.getTrackErr());
		});
        //点击以进行参数评估
        calculate = new Button("评估");
        calculate.setOnAction((ActionEvent e)->{
        	String name = samplePage.sampleInfoProperty.get().name;
        	if(name.contains("BOC")) {
        		setValue();
        		boc_value[0] = ""+boc_evaluate.boc_frequence.alpha;
        		boc_value[1] = ""+boc_evaluate.boc_frequence.beta;
        		boc_value[6] = ""+10*Math.log10(boc_evaluate.getLamda());
        		boc_value[2] = "±"+Math.abs(boc_evaluate.getFre_Off_Value());
        		boc_value[3] = ""+boc_evaluate.getMax_Fre_Des();
        		boc_value[4] = ""+boc_evaluate.getBeta_2();
        		boc_value[5] = ""+boc_evaluate.getEff_Band();
        		boc_value[7] = ""+boc_evaluate.getIso_Cof1();
        		boc_value[8] = ""+boc_evaluate.getIso_Cof2();
        		boc_value[9] = ""+boc_evaluate.getIso_Cof3();
        		boc_value[10] = ""+boc_evaluate.getTrackErr();
        		Lamda_Value.setText(boc_value[6]);
        		Fre_Offset_Value.setText(boc_value[2]);
        		Max_Power_Des_Value.setText(boc_value[3]);
//        		Per_Band_Value.setText(boc_value[5]);
//        		Out_Band_Loss_Value.setText(boc_value[6]);
        		Beta_2_Value.setText(boc_value[4]);
        		Eff_Band_Value.setText(boc_value[5]);        		
        		Iso_Cof_Value1.setText(boc_value[7]);
        		Iso_Cof_Value2.setText(boc_value[8]);
        		Iso_Cof_Value3.setText(boc_value[9]);
        		TrackErr_Value.setText(boc_value[10]);
        	}else if(name.contains("BPSK")){
        		setValue_Bpsk_frequence();
        		bpsk_value[0] = ""+bpsk_evaluate.bpsk_frequence.fCarrier;
        		bpsk_value[5] = ""+10*Math.log10(bpsk_evaluate.getLamda());
        		bpsk_value[1] = "±"+Math.abs(bpsk_evaluate.getFre_Off_Value());
        		bpsk_value[2] = ""+bpsk_evaluate.getMax_Fre_Des();
        		bpsk_value[3] = ""+bpsk_evaluate.getBeta_2();
        		bpsk_value[4] = ""+bpsk_evaluate.getEff_Band();
        		bpsk_value[6] = ""+bpsk_evaluate.getIso_Cof1();
        		bpsk_value[7] = ""+bpsk_evaluate.getIso_Cof2();
        		bpsk_value[8] = ""+bpsk_evaluate.getIso_Cof3();
        		bpsk_value[9] = ""+bpsk_evaluate.getTrackErr();
        		Lamda_Value.setText(bpsk_value[5]);
        		Fre_Offset_Value.setText(bpsk_value[1]);
        		Max_Power_Des_Value.setText(bpsk_value[2]);
//        		Per_Band_Value.setText("");
//        		Out_Band_Loss_Value.setText("");
        		Beta_2_Value.setText(bpsk_value[3]);
        		Eff_Band_Value.setText(bpsk_value[4]);        		
        		Iso_Cof_Value1.setText(bpsk_value[6]);
        		Iso_Cof_Value2.setText(bpsk_value[7]);
        		Iso_Cof_Value3.setText(bpsk_value[8]);
        		TrackErr_Value.setText(bpsk_value[9]);
        	}
        });
        //点击以获取表格
        tabs = new Button("表格");
        tabs.setOnAction((ActionEvent e)->{
        	String name = samplePage.sampleInfoProperty.get().name;
        	if(name.contains("BOC")) {
        		JFrame frame = new JFrame("JTable to Excel Hack");
        		JButton export_BOC = new JButton("Export_BOC");
        		JButton export_bpsk=new JButton("Export_bpsk");
        		final JTable table_BOC=new CreateTable(boc_value, bpsk_value).getTable_BOC();
        		final JTable table_bpsk=new CreateTable(boc_value, bpsk_value).getTable_bpsk();
        		JScrollPane scroll1 = new JScrollPane(table_BOC);
        		JScrollPane scroll2 = new JScrollPane(table_bpsk);    
        		FileDialog fd_BOC = new FileDialog(new CreateTable(boc_value, bpsk_value), "BOC特性", FileDialog.SAVE);  
        		fd_BOC.setLocation(400, 250);  
        		fd_BOC.setVisible(true);    
        		String stringfile_BOC = fd_BOC.getDirectory()+fd_BOC.getFile()+".xls";
        		export_BOC.addActionListener(new ActionListener() { 
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {  
        			    	ExcelExporter exp = new ExcelExporter();  
        			    	exp.exportTable(table_BOC, new File(stringfile_BOC));  
        			    	} catch (IOException ex) {  
        			    		System.out.println(ex.getMessage());  
        			    		ex.printStackTrace();
        			    		}
        			    	}
					});
        		frame.getContentPane().add("North",scroll1);
    		    frame.getContentPane().add("Center",scroll2);
    		    frame.getContentPane().add("East",export_BOC);
    		    frame.getContentPane().add("West",export_bpsk);
    		    frame.pack();
    		    frame.setVisible(true);
        	}else if(name.contains("BPSK")){
        		JFrame frame = new JFrame("JTable to Excel Hack");
        		JButton export_BOC = new JButton("Export_BOC");
        		JButton export_bpsk=new JButton("Export_bpsk");
        		final JTable table_BOC=new CreateTable(boc_value, bpsk_value).getTable_BOC();
        		final JTable table_bpsk=new CreateTable(boc_value, bpsk_value).getTable_bpsk();
        		JScrollPane scroll1 = new JScrollPane(table_BOC);
        		JScrollPane scroll2 = new JScrollPane(table_bpsk);  
        		FileDialog fd_bpsk = new FileDialog(new CreateTable(boc_value, bpsk_value), "BPSK特性", FileDialog.SAVE);
        		fd_bpsk.setLocation(400, 250);  
        		fd_bpsk.setVisible(true);    
        		String stringfile_bpsk = fd_bpsk.getDirectory()+fd_bpsk.getFile()+".xls";
        		export_bpsk.addActionListener(new ActionListener() { 
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try { 
        			    	ExcelExporter exp = new ExcelExporter();  
        			    	exp.exportTable(table_bpsk, new File(stringfile_bpsk));  
        			    	} catch (IOException ex) {  
        			    		System.out.println(ex.getMessage());  
        			    		ex.printStackTrace();
        			    		}
        			    	}
					});
        		frame.getContentPane().add("North",scroll1);
    		    frame.getContentPane().add("Center",scroll2);
    		    frame.getContentPane().add("East",export_BOC);
    		    frame.getContentPane().add("West",export_bpsk);
    		    frame.pack();
    		    frame.setVisible(true);
        	}
        });
        
        GridPane temp = new GridPane();
        ColumnConstraints left = new ColumnConstraints();
        left.setPercentWidth(70);
        ColumnConstraints right = new ColumnConstraints();
        right.setPercentWidth(30);
        temp.getColumnConstraints().addAll(left, right);
        temp.add(calculate, 0, 0);
        temp.add(tabs, 1, 0);

        //各个界面控制器件位置及占用空间大小
        GridPane.setConstraints(alpha, 0, 0);
        GridPane.setConstraints(alphaValue, 0, 1);
        
        GridPane.setConstraints(beta, 1, 0);
        GridPane.setConstraints(betaValue, 1, 1);
        
        GridPane.setConstraints(low, 0, 2);
        GridPane.setConstraints(lowValue, 0, 3);
        
        GridPane.setConstraints(high, 1, 2);
        GridPane.setConstraints(highValue, 1, 3);
        
        GridPane.setConstraints(offset, 2, 2);
        GridPane.setConstraints(offsetValue, 2, 3);
        
        GridPane.setConstraints(paint, 0, 4);
        GridPane.setConstraints(save, 1, 4);
        GridPane.setConstraints(temp, 2, 4);
        
        GridPane.setConstraints(scrollPane, 0, 5, 2, 1);
        
        
        if (PlatformFeatures.LINK_TO_SOURCE) getChildren().add(sourceBtn);
        if (Platform.isSupported(ConditionalFeature.WEB)) {
            // Setup Columns
            GridPane gridPane = new GridPane();
            getChildren().add(gridPane);
            gridPane.setVgap(INDENT);
            gridPane.setHgap(INDENT);
            ColumnConstraints leftColumn = new ColumnConstraints();
            leftColumn.setPercentWidth(30);
            ColumnConstraints rightColumn = new ColumnConstraints();
            rightColumn.setPercentWidth(30);
            gridPane.getColumnConstraints().addAll(leftColumn, rightColumn);

            // Add Related Samples
            Text relatedSamplesTitle = title("RELATED SAMPLES");
            GridPane.setConstraints(relatedSamplesTitle, 2, 0);
            relatedSamples = new VBox();
            GridPane.setConstraints(relatedSamples, 2, 1);
            //各个界面控制器件的添加
            gridPane.getChildren().addAll(
            		relatedSamplesTitle,
                    relatedSamples,
                    alpha,
                    alphaValue, 
                    beta, 
                    betaValue, 
                    low, 
                    lowValue,
                    high,
                    highValue,
                    offset,
                    offsetValue, 
                    paint,
                    save, 
                    temp,
                    scrollPane
                    );
            
        } else {
            Text relatedSamplesTitle = title("RELATED SAMPLES");
            relatedSamples = new VBox();
            getChildren().addAll(relatedSamplesTitle, relatedSamples);
        }

        // listen for when sample changes
        samplePage.registerSampleInfoUpdater((SampleInfo sampleInfo) -> {
            update(sampleInfo);
            return null;
        });
    }

    private void update(SampleInfo sampleInfo) {
        if (PlatformFeatures.WEB_SUPPORTED) {
//            relatedDocumentsList.getChildren().clear();
            for (final URL docUrl : sampleInfo.getDocURLs()) {
                Hyperlink link = new Hyperlink(docUrl.getName());
                link.setOnAction((ActionEvent ev) -> {
                    samplePage.pageBrowser.goToPage(docUrl.getURL());
                });
                link.setTooltip(new Tooltip(docUrl.getName()));
//                relatedDocumentsList.getChildren().add(link);
            }
            for (final String classpath : sampleInfo.apiClasspaths) {
                Hyperlink link = new Hyperlink(classpath);
                link.setOnAction((ActionEvent ev) -> {
                    samplePage.pageBrowser.goToPage(samplePage.apiClassToUrl(classpath));
                });
//                relatedDocumentsList.getChildren().add(link);
            }
        }
        relatedSamples.getChildren().clear();
        for (final SampleInfo.URL sampleURL : sampleInfo.getRelatedSampleURLs()) {
            if (Samples.ROOT.sampleForPath(sampleURL.getURL()) != null) { //Check if sample exists
                Hyperlink sampleLink = new Hyperlink(sampleURL.getName());
                sampleLink.setOnAction((ActionEvent t) -> {
                    samplePage.pageBrowser.goToPage("sample://" + sampleURL.getURL());
                });
                sampleLink.setPrefWidth(1000);
                relatedSamples.getChildren().add(sampleLink);
            }
        }
    }
    
    
    private int getAlphaValue() {
    	if(alphaValue.getText().length() == 0) {
    		return 10;
    	}
    	double result = Double.parseDouble(alphaValue.getText().trim());
    	if (result < 0) {
    		alphaValue.setPromptText("值需要大于0");
    		return 10;
    	}else {
			return ((int)result)==0?1:(int)result;
		}
    }
    
    private double getBpskFCarrierValue() {
    	if(alphaValue.getText().length() == 0) {
    		return 1000;
    	}
    	double result = Double.parseDouble(alphaValue.getText().trim());
    	if (result < 0) {
    		alphaValue.setPromptText("值需要大于0");
    		return 1000;
    	}else {
			return result;
		}
    }
    
    private int getBpskNfValue() {
    	if(alphaValue.getText().length() == 0) {
    		return 2;
    	}
    	double result = Double.parseDouble(alphaValue.getText().trim());
    	if (result <= 0) {
    		alphaValue.setPromptText("值需要大于0");
    		return 2;
    	}else {
			return (int)result;
		}
    }

    private int getBetaValue() {
    	if(betaValue.getText().length() == 0) {
    		return 5;
    	}
    	double result = Double.parseDouble(betaValue.getText().trim());
    	if (result <= 0) {
    		alphaValue.setPromptText("值需要大于0");
    		return 5;
    	}else {
			return (int)result;
		}
    }
    
    private double getBpskBandValue() {
    	if(betaValue.getText().length() == 0) {
    		return 10230000;
    	}
    	double result = Double.parseDouble(betaValue.getText().trim());
    	if (result <= 0) {
    		alphaValue.setPromptText("值需要大于0");
    		return 10230000;
    	}else {
			return result;
		}
    }
    
    private double getPossibilityValue() {
		if(betaValue.getText().length() == 0) {
			return 0.5;
		}
		double result = Double.parseDouble(highValue.getText().trim());
		return result;
	}
    
    private double getLowValue() {
		if(lowValue.getText().length() == 0) {
			return -15000000;
		}
		double result = Double.parseDouble(lowValue.getText().trim());
		return result==0?1:result;
	}
    
    private double getBpskFrequenceLowValue() {
    	if(lowValue.getText().length() == 0) {
			return -30000000;
		}
		double result = Double.parseDouble(lowValue.getText().trim());
		return result==0?1:result;
    }
    
    private double getNTbValue() {
		if(lowValue.getText().length() == 0) {
			return 5;
		}
		double result = Double.parseDouble(lowValue.getText().trim());
		return result==0?1:result;
	}
    
    private String getSequenceValue() {
		if(lowValue.getText() == null) {
			return ""+10100;
		}
		String  result = lowValue.getText().trim();
		return result;
	}
    
    private double getHighValue() {
		if(highValue.getText().length() == 0) {
			return 15000000;
		}
		double result = Double.parseDouble(highValue.getText().trim());
		return result;
	}
    
    private double getBpsk_Tiem_HighValue() {
		if(highValue.getText().length() == 0) {
			return 1000;
		}
		double result = Double.parseDouble(highValue.getText().trim());
		return result;
	}
    
    private double getBpskFrequenceHighValue() {
    	if(highValue.getText().length() == 0) {
			return 30000000;
		}
		double result = Double.parseDouble(highValue.getText().trim());
		return result==0?1:0;
    }
    
    private int getOffsetValue() {
		if(offsetValue.getText().length() == 0) {
			return 10000;
		}
		double result = Double.parseDouble(offsetValue.getText().trim());
		if(result <= 0) {
			offsetValue.setPromptText("值需要大于0");
			return 10000;
		}else {
			return (int)result;
		}
	}
    
    private int getBpsk_Time_OffsetValue() {
		if(offsetValue.getText().length() == 0) {
			return 1000;
		}
		double result = Double.parseDouble(offsetValue.getText().trim());
		if(result <= 0) {
			offsetValue.setPromptText("值需要大于0");
			return 1000;
		}else {
			return (int)result;
		}
	}
    
    private int getBpskOffsetValue() {
    	if(offsetValue.getText().length() == 0) {
			return 10000;
		}
		double result = Double.parseDouble(offsetValue.getText().trim());
		if(result <= 0) {
			offsetValue.setPromptText("值需要大于0");
			return 10000;
		}else {
			return (int)result;
		}
    }
    
    private int getTsValue() {
		if(offsetValue.getText().length() == 0) {
			return 30;
		}
		double result = Double.parseDouble(offsetValue.getText().trim());
		if(result <= 0) {
			offsetValue.setPromptText("值需要大于0");
			return 30;
		}else {
			return (int)result;
		}
	}
    
    private void setValue() {
		boc_frequence.alpha = getAlphaValue();
		boc_frequence.beta = getBetaValue();
		boc_frequence.low_point = getLowValue();
		boc_frequence.high_point = getHighValue();
		boc_frequence.offset = getOffsetValue();
	}
    
    private void setValue_Boc_time() {
		boc_time.alpha = getAlphaValue();
		boc_time.beta = getBetaValue();
		boc_time.sequence = getSequenceValue();
		boc_time.high_point = getHighValue();
		boc_time.Ts = getTsValue();
	}
    
    private void setValue_Bpsk_time() {
    	bpsk_time.Nf = getBpskNfValue();
    	bpsk_time.possibility = getPossibilityValue();
    	bpsk_time.NTb = getNTbValue();
    	bpsk_time.high_point = getBpsk_Tiem_HighValue();
    	bpsk_time.offset = getBpsk_Time_OffsetValue();
    }
    
    private void setValue_Bpsk_frequence() {
    	bpsk_frequence.fCarrier = getBpskFCarrierValue();
    	bpsk_frequence.band = getBpskBandValue();
    	bpsk_frequence.low_point = getBpskFrequenceLowValue();
    	bpsk_frequence.high_point = getBpskFrequenceHighValue();
    	bpsk_frequence.offset = getBpskOffsetValue();
    }
    
    
    
    
    
}
