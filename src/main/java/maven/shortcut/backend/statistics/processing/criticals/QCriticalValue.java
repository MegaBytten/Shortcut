package maven.shortcut.backend.statistics.processing.criticals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QCriticalValue {
	
	
	//can become more softcode/interchangeable with adding "String tail" parameter instead of directly finding the two-tailed sheet
	public double returnQCrit(int numberOfGroups, int sampleSize, String significanceLevel) {
		
		File tTableFile = new File("/Users/" + System.getProperty("user.name") + "/Shortcut/QTables.xlsx");
		
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(tTableFile));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		//parse file from excel into wb object
		
		
		Sheet sheet = null;
		
		//one or two tailed test can be malleable by changing "0.05"!!!
		for (int i = 0; i <= wb.getNumberOfSheets(); i++) {
			if(wb.getSheetAt(i).getSheetName().toLowerCase().contains(significanceLevel)){
				sheet = wb.getSheetAt(i);
				break;
			}
		}
		
		Row row = null;
		
		
		//this code selects the row according to our degrees of freedom
		if (sampleSize > 120) {
			row = sheet.getRow(sheet.getLastRowNum());
		} else {
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
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
		
		
		//now we need to get our column number
		int column = 0;
		
		for (int i = 1; i < sheet.getRow(0).getLastCellNum(); i++) {
			
			if (numberOfGroups == sheet.getRow(0).getCell(i).getNumericCellValue()) {
				column = i;
			}
		}
		return row.getCell(column).getNumericCellValue();
	}
			

}
