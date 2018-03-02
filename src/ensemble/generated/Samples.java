package ensemble.generated;
import ensemble.*;

//各个曲线的静态实例
import ensemble.playground.PlaygroundProperty;
import javafx.application.ConditionalFeature;
import java.util.HashMap;
public class Samples{
	
	private static final SampleInfo SAMPLE_BOC_TIME = new SampleInfo(
			"BOC-时域",
			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
			"sample://BOC/BOC-时域",
			"/src/ensemble",//baseUri
			"ensemble.Boc_time_chart_app",//appClass
			"Boc_time.png",
			new String[]{
			"ensemble/Boc_time_chart_app.java",
			"CurveFittedAreaChart.css",},
			new String[]{"javafx.scene.chart.AreaChart",
			"javafx.scene.chart.NumberAxis",},
			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
			"Using JavaFX Charts Tutorial",},
			new String[]{"/BOC/BOC-频域",
						"/BOC/BOC-自相关",
						"/BOC/BOC-S曲线"},
			"/src/ensemble/Boc_time_chart_app.java",
			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
			new PlaygroundProperty(null,"-","name","xAxis"),
			new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
			new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
			new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
			
			new PlaygroundProperty("xAxis","tickLabelFill"),
			new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
			
			new PlaygroundProperty(null,"-","name","yAxis"),
			new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
			new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
			new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
			
			new PlaygroundProperty("yAxis","tickLabelFill"),
			new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
			
//			new PlaygroundProperty("chart","title"),
//			new PlaygroundProperty("chart","titleSide"),
			},
			new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_BOC_FREQUENCE = new SampleInfo(
			"BOC-频域",
			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
			"sample://BOC/BOC-频域",
			"src/ensemble",//baseUri
			"ensemble.Boc_frequence_chart_app",//appClass
			"Boc_frequence.png",
			new String[]{"src/ensemble/CurveFittedAreaChart.java",
			"src/ensemble/Boc_frequence_chart_app.java",
			"src/CurveFittedAreaChart.css",},
			new String[]{"javafx.scene.chart.AreaChart",
			"javafx.scene.chart.NumberAxis",},
			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
			"Using JavaFX Charts Tutorial",},
			new String[]{"BOC/BOC-时域",
					"BOC/BOC-自相关",
					"BOC/BOC-S曲线"},
			"/src/ensemble/Boc_frequence_chart_app.java",
			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
					new PlaygroundProperty(null,"-","name","xAxis"),
					new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
					
					new PlaygroundProperty("xAxis","tickLabelFill"),
					new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
					
					new PlaygroundProperty(null,"-","name","yAxis"),
					new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
					new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
					new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
					
					new PlaygroundProperty("yAxis","tickLabelFill"),
					new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
//					new PlaygroundProperty("chart","title"),
//					new PlaygroundProperty("chart","titleSide"),
					},
					new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_BOC_RELATIVE = new SampleInfo(
			"BOC-自相关",
			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
			"sample://BOC/BOC-自相关",
			"/src/ensemble",//baseUri
			"ensemble.Boc_relative_chart_app",//appClass
			"Boc_relative.png",
			new String[]{"/src/ensemble/CurveFittedAreaChart.java",
			"/src/ensemble/Boc_relative_chart_app.java",
			"CurveFittedAreaChart.css",},
			new String[]{"javafx.scene.chart.AreaChart",
			"javafx.scene.chart.NumberAxis",},
			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
			"Using JavaFX Charts Tutorial",},
			new String[]{"/BOC/BOC-时域",
					"/BOC/BOC-频域",
					"/BOC/BOC-S曲线"},
			"/src/ensemble/Boc_relative_chart_app.java",
			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
					new PlaygroundProperty(null,"-","name","xAxis"),
					new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
					
					new PlaygroundProperty("xAxis","tickLabelFill"),
					new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
					
					new PlaygroundProperty(null,"-","name","yAxis"),
					new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
					new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
					new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
					
					new PlaygroundProperty("yAxis","tickLabelFill"),
					new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
//					new PlaygroundProperty("chart","title"),
//					new PlaygroundProperty("chart","titleSide"),
					},
					new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_BOC_CurveS = new SampleInfo(
    			"Curve-S",
    			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
    			"sample://BOC/Curve-S曲线",
    			"/src/ensemble",//baseUri
    			"ensemble.CurveS",//appClass
    			"Boc_Curve_S.png",
    			new String[]{
    			"ensemble/ensemble.CurveS.java"},
    			new String[]{"javafx.scene.chart.AreaChart",
    			"javafx.scene.chart.NumberAxis",},
    			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
    			"Using JavaFX Charts Tutorial",},
    			new String[]{"/BOC/BOC-频域",
    						"/BOC/BOC-自相关",
    						"/BOC/BOC-时域"},
    			"/src/ensemble/CurveS.java",
    			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
    			new PlaygroundProperty(null,"-","name","xAxis"),
    			new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
    			new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
    			new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
    			
    			new PlaygroundProperty("xAxis","tickLabelFill"),
    			new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
    			
    			new PlaygroundProperty(null,"-","name","yAxis"),
    			new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
    			new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
    			new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
    			
    			new PlaygroundProperty("yAxis","tickLabelFill"),
    			new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
    			
//    			new PlaygroundProperty("chart","title"),
//    			new PlaygroundProperty("chart","titleSide"),
    			},
    			new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_Tracking_Error = new SampleInfo(
    			"Tracking_Error",
    			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
    			"sample://BOC/Tracking_Error",
    			"/src/ensemble",//baseUri
    			"ensemble.Tracking_Error",//appClass
    			"Tracking_Error.png",
    			new String[]{
    			"ensemble/ensemble.Tracking_Error.java"},
    			new String[]{"javafx.scene.chart.AreaChart",
    			"javafx.scene.chart.NumberAxis",},
    			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
    			"Using JavaFX Charts Tutorial",},
    			new String[]{"/BOC/BOC-频域",
    						"/BOC/BOC-自相关",
    						"/BOC/BOC-S曲线"},
    			"/src/ensemble/Tracking_Error.java",
    			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
    			new PlaygroundProperty(null,"-","name","xAxis"),
    			new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
    			new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
    			new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
    			
    			new PlaygroundProperty("xAxis","tickLabelFill"),
    			new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
    			
    			new PlaygroundProperty(null,"-","name","yAxis"),
    			new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
    			new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
    			new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
    			
    			new PlaygroundProperty("yAxis","tickLabelFill"),
    			new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
    			
//    			new PlaygroundProperty("chart","title"),
//    			new PlaygroundProperty("chart","titleSide"),
    			},
    			new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_MultiPath = new SampleInfo(
    			"MultiPath",
    			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
    			"sample://BOC/MultiPath",
    			"/src/ensemble",//baseUri
    			"ensemble.MultiPath",//appClass
    			"MultiPath.png",
    			new String[]{
    			"ensemble/ensemble.MultiPath.java"},
    			new String[]{"javafx.scene.chart.AreaChart",
    			"javafx.scene.chart.NumberAxis",},
    			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
    			"Using JavaFX Charts Tutorial",},
    			new String[]{"/BOC/BOC-频域",
    						"/BOC/BOC-自相关",
    						"/BOC/BOC-S曲线"},
    			"/src/ensemble/MultiPath.java",
    			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
    			new PlaygroundProperty(null,"-","name","xAxis"),
    			new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
    			new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
    			new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
    			
    			new PlaygroundProperty("xAxis","tickLabelFill"),
    			new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
    			
    			new PlaygroundProperty(null,"-","name","yAxis"),
    			new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
    			new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
    			new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
    			
    			new PlaygroundProperty("yAxis","tickLabelFill"),
    			new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),

    			},
    			new ConditionalFeature[]{},false);
        
        
        
        private static final SampleInfo SAMPLE_BPSK_TIME = new SampleInfo(
			"BPSK-时域",
			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
			"sample://BPSK/BPSK-时域",
			"/src/ensemble",//baseUri
			"ensemble.Bpsk_time_chart_app",//appClass
			"Bpsk_time.png",
			new String[]{"/src/ensemble/CurveFittedAreaChart.java",
			"/src/ensemble/Bpsk_time_chart_app.java",
			"CurveFittedAreaChart.css",},
			new String[]{"javafx.scene.chart.AreaChart",
			"javafx.scene.chart.NumberAxis",},
			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
			"Using JavaFX Charts Tutorial",},
			new String[]{"/BPSK/BPSK-频域",
					"/BPSK/BPSK-自相关"},
			"/src/ensemble/Bpsk_time_chart_app.java",
			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
					new PlaygroundProperty(null,"-","name","xAxis"),
					new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
					
					new PlaygroundProperty("xAxis","tickLabelFill"),
					new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
					
					new PlaygroundProperty(null,"-","name","yAxis"),
					new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
					new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
					new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
					
					new PlaygroundProperty("yAxis","tickLabelFill"),
					new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
					},
					new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_BPSK_FREQUENCE = new SampleInfo(
			"BPSK-频域",
			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
			"sample://BPSK/BPSK-频域",
			"/src/ensemble",//baseUri
			"ensemble.Bpsk_frequence_chart_app",//appClass
			"Bpsk_frequence.png",
			new String[]{"/src/ensemble/CurveFittedAreaChart.java",
			"/src/ensemble/Bpsk_frequence_chart_app.java",
			"CurveFittedAreaChart.css",},
			new String[]{"javafx.scene.chart.AreaChart",
			"javafx.scene.chart.NumberAxis",},
			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
			"Using JavaFX Charts Tutorial",},
			new String[]{"/BPSK/BPSK-时域",
					"/BPSK/BPSK-自相关"},
			"/src/ensemble/Bpsk_frequence_chart_app.java",
			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
					new PlaygroundProperty(null,"-","name","xAxis"),
					new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
					
					new PlaygroundProperty("xAxis","tickLabelFill"),
					new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
					
					new PlaygroundProperty(null,"-","name","yAxis"),
					new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
					new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
					new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
					
					new PlaygroundProperty("yAxis","tickLabelFill"),
					new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
//					new PlaygroundProperty("chart","title"),
//					new PlaygroundProperty("chart","titleSide"),
					},
					new ConditionalFeature[]{},false);
        
        private static final SampleInfo SAMPLE_BPSK_RELATIVE = new SampleInfo(
			"BPSK-自相关",
			"An area chart that demonstrates curve fitting. Styling is done through CSS. ",
			"sample://BPSK/BPSK-自相关",
			"/src/ensemble",//baseUri
			"ensemble.Bpsk_relative_chart_app",//appClass
			"Bpsk_relative.png",
			new String[]{"/ensemble/CurveFittedAreaChart.java",
			"src/ensemble/Bpsk_relative_chart_app.java",
			"CurveFittedAreaChart.css",},
			new String[]{"javafx.scene.chart.AreaChart",
			"javafx.scene.chart.NumberAxis",},
			new String[]{"https://docs.oracle.com/javafx/2/charts/jfxpub-charts.htm",
			"Using JavaFX Charts Tutorial",},
			new String[]{"/BPSK/BPSK-时域",
					"/BPSK/BPSK-自相关"},
			"/src/ensemble/Bpsk_relative_chart_app.java",
			new PlaygroundProperty[]{new PlaygroundProperty("chart","data"),
					new PlaygroundProperty(null,"-","name","xAxis"),
					new PlaygroundProperty("xAxis","lowerBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","upperBound","min","-10000","max","10000","step","1"),
					new PlaygroundProperty("xAxis","tickUnit","max","10000","step","1"),
					
					new PlaygroundProperty("xAxis","tickLabelFill"),
					new PlaygroundProperty("xAxis","tickLabelRotation","min","-180","max","180","step","1"),
					
					new PlaygroundProperty(null,"-","name","yAxis"),
					new PlaygroundProperty("yAxis","lowerBound","min","-1000","max","1000","step","1"),
					new PlaygroundProperty("yAxis","upperBound","min","-1000","max","2000","step","1"),
					new PlaygroundProperty("yAxis","tickUnit","max","1000","step","1"),
					
					new PlaygroundProperty("yAxis","tickLabelFill"),
					new PlaygroundProperty("yAxis","tickLabelRotation","min","-180","max","180","step","1"),
//					new PlaygroundProperty("chart","title"),
//					new PlaygroundProperty("chart","titleSide"),
					},
					new ConditionalFeature[]{},false);
        
        
        
        
	public static final SampleCategory ROOT = new SampleCategory("ROOT", 
                                                                        null, 
                                                                        null, 
                                                                        new SampleCategory[] {
                                                                            new SampleCategory("BOC", 
                                                                                        new SampleInfo [] {SAMPLE_BOC_TIME,
                                                                                                            SAMPLE_BOC_FREQUENCE,
                                                                                                            SAMPLE_BOC_RELATIVE,
                                                                                                            SAMPLE_BOC_CurveS,
                                                                                                            SAMPLE_Tracking_Error,
                                                                                                            SAMPLE_MultiPath}, 
                                                                                        new SampleInfo [] {SAMPLE_BOC_TIME,
                                                                                                            SAMPLE_BOC_FREQUENCE,
                                                                                                            SAMPLE_BOC_RELATIVE,
                                                                                                            SAMPLE_BOC_CurveS,
                                                                                                            SAMPLE_Tracking_Error,
                                                                                                            SAMPLE_MultiPath
                                                                                        },null),
                                                                                new SampleCategory("BPSK", 
                                                                                new SampleInfo [] {SAMPLE_BPSK_TIME,
                                                                                                    SAMPLE_BPSK_FREQUENCE,
                                                                                                    SAMPLE_BPSK_RELATIVE}, 
                                                                                new SampleInfo [] {SAMPLE_BPSK_TIME,
                                                                                                    SAMPLE_BPSK_FREQUENCE,
                                                                                                    SAMPLE_BPSK_RELATIVE
                                                                                },
                                                                                null
                                                                                ),
                                                                        }) ;
        
        
        
	public static final SampleInfo[] HIGHLIGHTS = new SampleInfo[]{SAMPLE_BOC_CurveS,SAMPLE_BPSK_TIME};
	private static final HashMap<String,SampleInfo[]> DOCS_URL_TO_SAMPLE = new HashMap<String,SampleInfo[]>(4);
	static {
		DOCS_URL_TO_SAMPLE.put("javafx.scene.chart.AreaChart",new SampleInfo[]{SAMPLE_BOC_TIME,SAMPLE_BOC_FREQUENCE,SAMPLE_BOC_RELATIVE});
	}
	public static SampleInfo[] getSamplesForDoc(String docUrl) {
        return DOCS_URL_TO_SAMPLE.get(docUrl);
    }
}
