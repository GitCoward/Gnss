package ensemble;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;
//实现导出到excel文件
public class ExcelExporter  {  
	public File file_last;
    public ExcelExporter() { }  
    public void exportTable(JTable table, File file) throws IOException {  
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file, true); 
        //判断文件是否为空，若是为空，则写入第一行标题栏，否则写入数据
        if(file.length() == 0) {
	        for(int i=0; i < model.getColumnCount(); i++) {  
	            out.write(model.getColumnName(i) + "\t");  
	        }
	        out.write("\n"); 
        }
        //数据写入
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
