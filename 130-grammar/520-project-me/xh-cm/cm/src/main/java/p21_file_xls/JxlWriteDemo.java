package p21_file_xls;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * jxl写Excel
 * 
 * @author jianggujin
 * 
 */
public class JxlWriteDemo
{
   public static void main(String[] args) throws IOException, WriteException
   {
      File xlsFile = new File("D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\jxl.xls");
      
      WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);// 创建一个工作簿, xls
      
      WritableSheet sheet = workbook.createSheet("sheet1", 0);// 创建一个工作表, sheet
      for (int row = 0; row < 10; row++)
      {
         for (int col = 0; col < 10; col++)
         {
            
            sheet.addCell(new Label(col, row, "data" + row + col));// 向工作表中添加数据
         }
      }
      workbook.write();
      workbook.close();
   }
}