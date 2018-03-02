package ensemble;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;
//ʵ�ֵ�����excel�ļ�
public class ExcelExporter  {  
	public File file_last;
    public ExcelExporter() { }  
    public void exportTable(JTable table, File file) throws IOException {  
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file, true); 
        //�ж��ļ��Ƿ�Ϊ�գ�����Ϊ�գ���д���һ�б�����������д������
        if(file.length() == 0) {
	        for(int i=0; i < model.getColumnCount(); i++) {  
	            out.write(model.getColumnName(i) + "\t");  
	        }
	        out.write("\n"); 
        }
        //����д��
        for(int i=0; i< model.getRowCount(); i++) {  
            for(int j=0; j < model.getColumnCount(); j++) {  
                out.write(model.getValueAt(i,j).toString()+"\t"); 
                System.out.println(model.getValueAt(i,j)+" "+i+" "+j);
            } 
            
            out.write("\n");  
        }  
        out.close();  
        System.out.println("write out to: " + file);  
    }  

		
}
