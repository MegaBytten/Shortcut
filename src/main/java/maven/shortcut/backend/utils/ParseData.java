package maven.shortcut.backend.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import maven.shortcut.backend.statistics.Redirectory;

public class ParseData {
	
	private static Data data = new Data();
	List<GroupData> parsedData = new ArrayList<GroupData>();

	public void parseData() {
		Workbook wb = null;
		try {
			if (Redirectory.isXlsx) {
				wb = new XSSFWorkbook(new FileInputStream(Redirectory.dataFile)); //need to parse the user based file from Redirectory!!
			} else {
				FileInputStream fiStream;
				fiStream = new FileInputStream(Redirectory.dataFile); //need to parse the user based file from Redirectory!!
	            wb = new HSSFWorkbook(fiStream);
			}
		} catch (IOException e1) {
			System.out.println("Error: Unable to Parse File for ParseData.");
			e1.printStackTrace();
		} 
		
		Sheet sheet = wb.getSheetAt(0);
		
		int firstRowIndex = sheet.getFirstRowNum();   // The first row contains column names / variables names, returns in Array format (1=0)
        int lastRowIndex = sheet.getLastRowNum();
        
        
        Row variableRow = sheet.getRow(firstRowIndex);
        if (variableRow != null) {
        	for (int cellIndex = 0; cellIndex < variableRow.getLastCellNum(); cellIndex++) {
        		Cell cell = variableRow.getCell(cellIndex);
        		if (cell != null) {
        			if (cell.getStringCellValue() != null) {
        				data.addGroup(cell.getStringCellValue());
        				System.out.println();
            		}
        		}
        	}
        }
        //takes First row of sheet, and cycles through all (both) cells 
        //adding their Strings to the String List of "variables" (variable names)
        
        
        for (int i = 0; i < data.getNumberOfGroups(); i++) {
        	for (int rowIndex = firstRowIndex+1; rowIndex <= lastRowIndex; rowIndex++) {
        		Cell cell = sheet.getRow(rowIndex).getCell(i);
        		if (cell != null) {
        			data.getGroup(i).addData(cell.getNumericCellValue());
        		}
            }
        }
		/*
		 * These above 5 lines of pure godliness do the following:
		 * 1. cycles through every column according to the size of data, which was determined by the Strings in variableRow
		 * This corresponds to how many variableNames and therefore variables/groups there are!
		 * 
		 * 2. Before cycling to the next column:
		 * cycles through every row according to the sheet's first/last row, and gets the cell according to the current group mid cycle
		 * 
		 * 3. Checks if the cell does not have any data (is Null), and if it != null, it will add the cell's data to the corresponding group
		 * 
		 */
        
	}
	
	public Data getParsedData() {
		return data;
	}
	
}
