package p21_file_xlsx;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class excelUtils {

    private  static String FILE_PATH = "D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\poi.xlsx";
    private  static String FILE_PATH2 = "D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\poi2.xlsx";

    	
    public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {

        
	        InputStream inp = new FileInputStream(FILE_PATH);//读取xlsx文件
	        Workbook wb = WorkbookFactory.create(inp);
	        
	        Sheet sheet = wb.getSheetAt(0);//获取第一个工作表信息
	        Row row = sheet.getRow(3); //获取第三行信息，从0行还是，第3行实际为第4行
	       
	        Cell cell = row.getCell(0); //获取第一格信息并输出
	        System.out.println(cell);
	
	        
	        if (cell == null)
	            cell = row.createCell(0);
	        cell.setCellValue("java");//将单元格内信息改成java

	        wb.createSheet("sheet2");
	        wb.setSheetName(0, "iuy");
	        
	        OutputStream fileOut = new FileOutputStream(FILE_PATH2);//保存修改后的文件
	        wb.write(fileOut);
        }
    }




