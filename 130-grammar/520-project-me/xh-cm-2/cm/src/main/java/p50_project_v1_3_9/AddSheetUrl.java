package p50_project_v1_3_9;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AddSheetUrl {

    static String PROJECT_ID = 
//    		"26"	//26 ex
    		"27"	//27 gs
//    		"06"	//06 local
    		;  
	static String TABLE_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.TABLE_PATH).toString();
    public static void main(String[] args) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
//    		Workbook dbBook = WorkbookFactory.create(new FileInputStream(TABLE_PATH));
            FileOutputStream out = new FileOutputStream("D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\gs2.xlsx");
            XSSFSheet sheet = workbook.createSheet("1");
            XSSFSheet sheetRef = workbook.createSheet("aa");
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setUnderline(XSSFFont.U_DOUBLE);
            font.setColor(IndexedColors.BLUE.getIndex());
            style.setFont(font);
            Row row = sheet.createRow(2);
            Cell cell = row.createCell(2);
            cell.setCellValue("aa");
            cell.setCellStyle(style);

            CreationHelper createHelper = workbook.getCreationHelper();
            XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
            link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("aa!A1");//路径不对，可能会出问题
            cell.setHyperlink(link);
            
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}