package ensemble;
import javax.swing.JFrame;
import javax.swing.table.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.io.*;
//参数评估的表格显示
public class CreateTable extends JFrame {

		private JTable table=null;
		private JPanel jp=new JPanel();
		//存储boc评估参数
		private String [] boc_value;
		//存储bpsk评估参数
		private String [] bpsk_value;
		public CreateTable(String[] boc, String[] bpsk) {
			this.boc_value = boc;
			this.bpsk_value = bpsk;
		}
		public JTable getTable_bpsk(){
			if(table==null){
			table=new JTable();
			String[] columns={"fb","距频带中心频偏","主瓣最大功率密度","均方根带宽","有效矩形带宽","剩余功率","与自身频谱隔离系数","与1.023MHz隔离系数","与BOC(10,5)的隔离系数","码跟踪误差"};
			
			int[] columnWidth={100,100,100,100,100,100,100,200,200,200};
			DefaultTableModel model=new DefaultTableModel(columns,1);//8是设置行数
			table.setModel(model);
			TableColumnModel columnModel=table.getColumnModel();
			int count=columnModel.getColumnCount();
			for(int i=0;i<count;i++){
				javax.swing.table.TableColumn column=columnModel.getColumn(i);
				column.setPreferredWidth(columnWidth[i]);
			}
			//输入数据，i是行，j是列
			for(int i=0; i< model.getRowCount(); i++) {
			    for(int j=0; j < model.getColumnCount(); j++) {
			       model.setValueAt(bpsk_value[j], i, j);
			       }
			    }
			}
			return table;
			}
			
		public JTable getTable_BOC(){
			  if(table==null){
			  table=new JTable();
			  String[] columns={"alpha","beta","距频带中心频偏","主瓣最大功率密度","均方根带宽","有效矩形带宽","剩余功率","与自身频谱隔离系数","与1.023MHz隔离系数","与BOC(10,5)的隔离系数","码跟踪误差"};
			  int[] columnWidth={30,30,100,100,100,100,100,100,200,200,200};
			  DefaultTableModel model=new DefaultTableModel(columns,1);//8是设置行数
			  table.setModel(model);
			  TableColumnModel columnModel=table.getColumnModel();
			  int count=columnModel.getColumnCount();
			  for(int i=0;i<count;i++){
				  javax.swing.table.TableColumn column=columnModel.getColumn(i);
			      column.setPreferredWidth(columnWidth[i]);
			      }
			  //输入数据
			  for(int i=0; i< model.getRowCount(); i++) {
				  for(int j=0; j < model.getColumnCount(); j++) {
				     model.setValueAt(boc_value[j], i, j);
				      }
				   }
			    }
			   return table;
			}
		
}
