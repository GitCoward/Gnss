package ensemble;

//boc评估类
public class Boc_evaluate {
	//boc自相关实例
	public Boc_relative_chart_app boc_relative;
	//boc时域实例
	public Boc_time_chart_app boc_time;
	//boc频域实例
	public Boc_frequence_chart_app boc_frequence;
	//bpsk频域实例
	public Bpsk_frequence_chart_app bpsk_frequence;
	//bpsk带宽
	public double band;
	//频谱最大值处的频率
	public double f_max;
	//发射带宽
	public double TBand = 30000000;
	//接收带宽
	public double RBand = 24000000;
	//数值初始化
	public void init() {
		if(boc_frequence.alpha <= 0) boc_frequence.alpha = 10;
		if(boc_frequence.beta <= 0) boc_frequence.beta = 5;
		if(boc_frequence.low_point == 0) boc_frequence.low_point = -0.5/1000000;
		if(boc_frequence.high_point == 0) boc_frequence.high_point = 0.5/1000000;
	}
	//剩余功率
	public double getLamda() {
		//数值初始化，防止计算时数值为0
		init();
		double r=0;
		band = 0;
		//接收带宽取为24M，取间隔为10000Hz，对Gs求积分（求和）
		for(double f=-RBand/2; f<RBand/2;f+=10000) {
			double temp = getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta);
			r+=temp*10000;
			if(band < temp) {
				band = temp;
				f_max = f;
			}
		}
		System.out.println(r);
		return r;
	}
	//均方根带宽
	public double getBeta_2(){
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand/2;f+=10000) {
			result += (Math.pow(f, 2) * (getGs_average(f)) * 10000);
		}
		return Math.sqrt(result);
	}
	//归一化功率谱密度
	public double getGs_average(double f) {
		return 1/getLamda()*getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta);
	}
	//与自身的频谱隔离系数
	public double getIso_Cof1() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta)
					*getGs_average(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	//与1.023MBPSK的频谱隔离系数
	public double getIso_Cof2() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta)
					*getBpskGs(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	//BPSK频谱密度
	private double getBpskGs(double f) {
    	return 1/(4*1023000.0)*Math.pow((Math.sin(Math.PI*f*(1/1023000.0))/(Math.PI*f*(1/1023000.0))), 2);
    }
	//与BOC（10，5）的频谱隔离系数
	public double getIso_Cof3() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, 10, 5)
					*getGs_average(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	//等效矩形带宽
	public double getEff_Band() {
		return getLamda()/band;
	}
	//码跟踪误差
	public double getTrackErr() {
		return 1/(2*Math.PI*getBeta_2())*Math.sqrt((getEff_Band()/(getLamda()*30)));
	}
	//频谱密度
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
	//频谱主瓣距频带中心的频偏
	public double getFre_Off_Value() {
		return f_max;
	}
	//主瓣最大功率谱密度
	public double getMax_Fre_Des() {
		init();
		double Max_Fre_Des = -100;
		for(double f = -TBand/2; f < TBand/2; f+=10000) {
    		double temp = calculate(f+0.1, boc_frequence.alpha, boc_frequence.beta);;
    		if(Max_Fre_Des < temp) Max_Fre_Des = temp;
    	}
		return Max_Fre_Des;
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
	
}
