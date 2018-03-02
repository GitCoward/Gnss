package ensemble;

//bpsk评估类，结构与boc评估类相同
public class Bpsk_evaluate {
	public Bpsk_relative_chart_app bpsk_relative;
	public Bpsk_time_chart_app bpsk_time;
	public Bpsk_frequence_chart_app bpsk_frequence;
	public Boc_frequence_chart_app boc_frequence;
	public double band;
	public double f_max;
	public double TBand = 30000000;
	public double RBand = 24000000;
	
	public void init() {}
	
	public double getLamda() {
		init();
		double r=0;
		band = 0;
		for(double f=-RBand/2; f<RBand/2;f+=10000) {
			double temp = getBpskGs(f+0.1);
			r+=temp*10000;
			if(band < temp) {
				band = temp;
				f_max = f;
			}
		}
		return r;
	}
	
	public double getBeta_2(){
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand/2;f+=10000) {
			result += (Math.pow(f, 2) * (getGs_average(f)) * 10000);
		}
		return Math.sqrt(result);
	}
	
	public double getGs_average(double f) {
		return 1/getLamda()*getBpskGs(f+0.1);
	}
	
	public double getIso_Cof1() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getBpskGs(f+0.1)
					*getGs_average(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	public double getIso_Cof2() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getBpskGs(f+0.1)
					*getBpskGs(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	
	private double getBpskGs(double f) {
    	return 1/(4*1023000.0)*Math.pow((Math.sin(Math.PI*f*(1/1023000.0))/(Math.PI*f*(1/1023000.0))), 2);
    }
	
	public double getIso_Cof3() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, 10, 5)
					*getGs_average(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	
	public double getEff_Band() {
		return getLamda()/band;
	}
	
	public double getTrackErr() {
		return 1/(2*Math.PI*getBeta_2())*Math.sqrt((getEff_Band()/(getLamda()*30)));
	}
	
	public double getGs(double f, int a, int b) {    	
    	double result = 0;
    	if((a*2/b) % 2==0) {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.sin(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	else {
    		result = Math.pow((Math.tan(Math.PI*f/(2*a*1023000)) * Math.cos(Math.PI*f/(b*1023000))) / (Math.PI * f), 2) * (b*1023000);
    	}
    	return result;
	}
	
	public double getFre_Off_Value() {
		return f_max;
	}
	
	public double getMax_Fre_Des() {
		init();
		double Max_Fre_Des = -100;
		for(double f = -TBand/2; f < TBand/2; f+=10000) {
    		double temp = 10*Math.log10(calculate(f+0.1));
    		if(Max_Fre_Des < temp) Max_Fre_Des = temp;
    	}
		return Max_Fre_Des;
	}
	
	private double calculate(double f) {
    	return 1/(4*1023000.0)*Math.pow((Math.sin(Math.PI*f*(1/1023000.0))/(Math.PI*f*(1/1023000.0))), 2);
    }
	
}
