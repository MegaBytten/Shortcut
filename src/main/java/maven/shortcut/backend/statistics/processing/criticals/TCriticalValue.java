package maven.shortcut.backend.statistics.processing.criticals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TCriticalValue {

	public double returnTCrit(int sampleSize, String tail, double alpha) {
		
		//string tail must either be "one" or "two"
		File tTableFile = new File("/Users/" + System.getProperty("user.name") + "/Shortcut/TTables.xlsx");
		
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(tTableFile));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		//parse file from excel into wb object
		
		
		Sheet sheet = null;
		
		for (int i = 0; i <= wb.getNumberOfSheets(); i++) {
			if(wb.getSheetAt(i).getSheetName().toLowerCase().contains(tail.toLowerCase())){
				sheet = wb.getSheetAt(i);
				break;
			} else {
				System.out.println("Functionality Marker: T-Table Xlsx file scanning sheets for [contains , two]");
			}
		}
		//gets the active sheet according to our Alpha Value of "0.05"
		
		Row row = null;
		
		
		if (sampleSize >= 1000) {
			row = sheet.getRow(sheet.getLastRowNum());
		} else {
			
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				if (sampleSize < sheet.getRow(i).getCell(0).getNumericCellValue()) {
					
					int currentDoFValue = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
					int previousDoFValue = (int) sheet.getRow(i-1).getCell(0).getNumericCellValue();
					
					if (currentDoFValue - sampleSize > sampleSize - previousDoFValue) {
						row = sheet.getRow(i - 1);
					} else {
						row = sheet.getRow(i);
					}
					break;
					
				} else if (sampleSize == sheet.getRow(i).getCell(0).getNumericCellValue()) {
					row = sheet.getRow(i);
					break;
				}
				
			}
		}
		
		
		int column = 0;
		
		for (int i = 1; i < sheet.getRow(0).getLastCellNum(); i++) {
			
			if (sheet.getRow(0).getCell(i).getNumericCellValue() == alpha) {
				column = i;
				break;
			} 
		}
		return row.getCell(column).getNumericCellValue();
	}
	
	
}
