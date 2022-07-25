package maven.shortcut.backend.statistics.processing.criticals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FCriticalValue {
	

	public double returnFCrit(int columnDoF, int rowDoF, String significanceLevel) {
		File fTableFile = new File("/Users/" + System.getProperty("user.name") + "/Shortcut/FTables.xlsx");

		System.out.println("Functionality Marker: Parsing F-Table Xlsx file to return FCrit");
		
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(fTableFile));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		//parse file from excel into wb object
		
		
		Sheet sheet = null;
		
		for (int i = 0; i <= wb.getNumberOfSheets(); i++) {
			if(wb.getSheetAt(i).getSheetName().contains(significanceLevel)) {
				sheet = wb.getSheetAt(i);
				break;
			} 
		}
		//gets the active sheet according to our Alpha Value of "0.05"
		
		Row row = null;
		
		if (rowDoF > 120) {
			row = sheet.getRow(sheet.getLastRowNum());
			//if DF > 120, no need to scan, last row is only option.
		} else {
			for (int i = 1; i <= sheet.getLastRowNum() + 1; i++ ) { 
				//loops to check every row starting from the 2nd one (actual numbers) to one before the last row
				
				if (sheet.getRow(i).getCell(0).getNumericCellValue() > rowDoF) {
					//if the value in the cell (which corresponding row of DoF) is greater than our degrees of freedom
					//we have either found our required row, or we have gone 1 section past it
					
					int currentDoFValue = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
					int previousDoFValue = (int) sheet.getRow(i-1).getCell(0).getNumericCellValue();
					
					if (currentDoFValue - rowDoF > rowDoF - previousDoFValue) {
						row = sheet.getRow(i - 1);
					} else {
						row = sheet.getRow(i);
					}
					//this if statement selects which row we are looking at depending on the differences between
					//the previous/current degrees of freedom and our own
					break;
					
					
				} else if (sheet.getRow(i).getCell(0).getNumericCellValue() == rowDoF){
					row = sheet.getRow(i);
					break;
					//if the i'th row is equal to the degrees of freedom its that exact row
				} 
			}
		}
		//gets the active row according to our DoF and the F-table online
		
		int column = 0;
		
		if (columnDoF > 120) {
			column = sheet.getRow(1).getLastCellNum();
			//if DF > 120, no need to scan, last column is only option
		} else {
			
			Row df1Row = sheet.getRow(0);
			for (int i = 1; i <= df1Row.getLastCellNum()-1; i++) {
				//loops through all cells of the first row aka the degrees of freedom labels
				
				if (df1Row.getCell(i).getNumericCellValue() > columnDoF) {
					//if the i'th cell's value is greater than our degrees of freedom, we've gone too far
					//then need to calculate whether the current or the previous degrees of freedom is closer.
					
					int currentDoFValue = (int) df1Row.getCell(i).getNumericCellValue();
					int previousDoFValue = (int) df1Row.getCell(i-1).getNumericCellValue();
					
					if (currentDoFValue - columnDoF > columnDoF - previousDoFValue) {
						column = i - 1;
					} else {
						column = i;
					}
					//this if statement assigns the column int which corresponds to the position of the DoF1 column
					//now we have our column .getCell(column), and our row!
					break;
					
				} else if (df1Row.getCell(i).getNumericCellValue() == columnDoF){
					column = i;
					break;
				}
			}	
		}
		return row.getCell(column).getNumericCellValue();
	}

}
