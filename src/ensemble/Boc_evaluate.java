package ensemble;

//boc������
public class Boc_evaluate {
	//boc�����ʵ��
	public Boc_relative_chart_app boc_relative;
	//bocʱ��ʵ��
	public Boc_time_chart_app boc_time;
	//bocƵ��ʵ��
	public Boc_frequence_chart_app boc_frequence;
	//bpskƵ��ʵ��
	public Bpsk_frequence_chart_app bpsk_frequence;
	//bpsk����
	public double band;
	//Ƶ�����ֵ����Ƶ��
	public double f_max;
	//�������
	public double TBand = 30000000;
	//���մ���
	public double RBand = 24000000;
	//��ֵ��ʼ��
	public void init() {
		if(boc_frequence.alpha <= 0) boc_frequence.alpha = 10;
		if(boc_frequence.beta <= 0) boc_frequence.beta = 5;
		if(boc_frequence.low_point == 0) boc_frequence.low_point = -0.5/1000000;
		if(boc_frequence.high_point == 0) boc_frequence.high_point = 0.5/1000000;
	}
	//ʣ�๦��
	public double getLamda() {
		//��ֵ��ʼ������ֹ����ʱ��ֵΪ0
		init();
		double r=0;
		band = 0;
		//���մ���ȡΪ24M��ȡ���Ϊ10000Hz����Gs����֣���ͣ�
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
	//����������
	public double getBeta_2(){
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand/2;f+=10000) {
			result += (Math.pow(f, 2) * (getGs_average(f)) * 10000);
		}
		return Math.sqrt(result);
	}
	//��һ���������ܶ�
	public double getGs_average(double f) {
		return 1/getLamda()*getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta);
	}
	//�������Ƶ�׸���ϵ��
	public double getIso_Cof1() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta)
					*getGs_average(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	//��1.023MBPSK��Ƶ�׸���ϵ��
	public double getIso_Cof2() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, boc_frequence.alpha, boc_frequence.beta)
					*getBpskGs(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	//BPSKƵ���ܶ�
	private double getBpskGs(double f) {
    	return 1/(4*1023000.0)*Math.pow((Math.sin(Math.PI*f*(1/1023000.0))/(Math.PI*f*(1/1023000.0))), 2);
    }
	//��BOC��10��5����Ƶ�׸���ϵ��
	public double getIso_Cof3() {
		init();
		double result = 0;
		for(double f=-RBand/2; f<RBand;f+=10000) {
			result += (getGs(f+0.1, 10, 5)
					*getGs_average(f+0.1)*10000);
		}
		return 10*Math.log10(result);
	}
	//��Ч���δ���
	public double getEff_Band() {
		return getLamda()/band;
	}
	//��������
	public double getTrackErr() {
		return 1/(2*Math.PI*getBeta_2())*Math.sqrt((getEff_Band()/(getLamda()*30)));
	}
	//Ƶ���ܶ�
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
	//Ƶ�������Ƶ�����ĵ�Ƶƫ
	public double getFre_Off_Value() {
		return f_max;
	}
	//������������ܶ�
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
