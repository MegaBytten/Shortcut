package maven.shortcut.backend.statistics.processing.criticals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PCriticalValue {
	
	public static boolean isTiny = false;
	
	//can become more softcode/interchangeable with adding "String tail" parameter instead of directly finding the two-tailed sheet
	public double returnPCritTStat(double tStat, int DoF) {
		
		File tTableFile = new File("/Users/" + System.getProperty("user.name") + "/Shortcut/TTables.xlsx");
		
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(tTableFile));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		//parse file from excel into wb object
		
		
		Sheet sheet = null;
		
		//one or two tailed test can be malleable by changing "two"!!!
		for (int i = 0; i <= wb.getNumberOfSheets(); i++) {
			if(wb.getSheetAt(i).getSheetName().toLowerCase().contains("two")){
				sheet = wb.getSheetAt(i);
				break;
			} else {
				System.out.println("Functionality Marker: PCrit Return - T-Table Xlsx file scanning sheets for [contains , two]");
			}
		}
		
		Row row = null;
		
		
		//this code selects the row according to our degrees of freedom
		if (DoF >= 1000) {
			row = sheet.getRow(sheet.getLastRowNum());
		} else {
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				if (DoF < sheet.getRow(i).getCell(0).getNumericCellValue()) {
					int currentDoFValue = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
					int previousDoFValue = (int) sheet.getRow(i-1).getCell(0).getNumericCellValue();
					
					if (currentDoFValue - DoF > DoF - previousDoFValue) {
						row = sheet.getRow(i - 1);
					} else {
						row = sheet.getRow(i);
					}
					break;
				} else if (DoF == sheet.getRow(i).getCell(0).getNumericCellValue()) {
					row = sheet.getRow(i);
					break;
				}
				
			}
		}
		
		
		//now we need to find the range for our tStat
		for (int i = 1; i<=sheet.getLastRowNum(); i++) {
			
			if (i >= Integer.valueOf(row.getLastCellNum())) {
				//if i continues to increase until there are no more columns, tStat has surpassed any comparible data
				//in its respective degrees of freedom, and the pValue is therefore uncalculable, so we say
				//pValue <<<< Our last possible value!
				
				isTiny = true;
				return sheet.getRow(0).getCell(sheet.getRow(0).getLastCellNum()-1).getNumericCellValue();
				
			} else if (row.getCell(i).getNumericCellValue() > tStat) {
				
				//these two are the higher P-Value/significance level and lower tCrit value, but called lower bc paired.
				double lowerAlpha = sheet.getRow(0).getCell(i-1).getNumericCellValue();
				double lowerTCrit = row.getCell(i-1).getNumericCellValue();
				
				//These two are the lower P-Value/significance level and higher TCrit value, but called higher bc paired.
				double higherAlpha = sheet.getRow(0).getCell(i).getNumericCellValue();
				double higherTCrit = row.getCell(i).getNumericCellValue();
				
				//our tStat lies somewhere between these two tCrit values, and therefore our P-Value lies proprortionally along the
				//two significance (alpha) values
				
				//tStatScale quantifies the difference between the lower and higher tCrit values
				double tStatScale = higherTCrit - lowerTCrit;
				//pureTStat quantifies how far away from the lowerTCrit tStat is, therefore allowing us to see the percentage of where
				//the tStat lies between lower and higher tCrits
				double pureTStat = tStat - lowerTCrit;
				
				//this is the percentage of where tStat lies between lower and higherTCrit with respect to the lowerTCrit
				double positionAsPercentage = pureTStat/tStatScale;
				
				//now that we understand this proportion, we can apply this position along the continuum between lower/higherTCrit to 
				//the significance levels, giving us our exact position between the range of significance/p-values :D
				
				double pValueScale = lowerAlpha - higherAlpha;
				
				return lowerAlpha - (pValueScale * positionAsPercentage);
				
			} else if (row.getCell(i).getNumericCellValue() == tStat) {
				return sheet.getRow(0).getCell(i).getNumericCellValue();
			}
			
			
		}
		
		return 0;
	}
		
	
	
	
	
	
	public double returnPCritFStat(double fStat, int columnDoF, int rowDoF) {
		FCriticalValue fCritGetter = new FCriticalValue();
		
		List<String> significanceLevelsList = new ArrayList<String>();
		Collections.addAll(significanceLevelsList, "0.1", "0.05", "0.025", "0.01");
		
		for (int i = 0; i <= significanceLevelsList.size(); i++) {if (i == significanceLevelsList.size()) {
				isTiny = true;
				return Double.parseDouble(significanceLevelsList.get(i-1));
				
			} else if (fCritGetter.returnFCrit(columnDoF, rowDoF, significanceLevelsList.get(i)) > fStat) {
				
				//these two are the higher P-Value/significance level and lower tCrit value, but called lower bc they come before the fStat in the FTable
				double lowerAlpha = Double.parseDouble(significanceLevelsList.get(i-1));
				double lowerFCrit = fCritGetter.returnFCrit(columnDoF, rowDoF, significanceLevelsList.get(i-1));
				
				//These two are the lower P-Value/significance level and higher TCrit value, but called higher bc they come after the fStat!
				double higherAlpha = Double.parseDouble(significanceLevelsList.get(i));
				double higherFCrit = fCritGetter.returnFCrit(columnDoF, rowDoF, significanceLevelsList.get(i));
				
				//tStatScale quantifies the difference between the lower and higher tCrit values
				double fStatScale = higherFCrit - lowerFCrit;
				//pureTStat quantifies how far away from the lowerTCrit tStat is, therefore allowing us to see the percentage of where
				//the tStat lies between lower and higher tCrits
				double pureFStat = fStat - lowerFCrit;
				
				//this is the percentage of where tStat lies between lower and higherTCrit with respect to the lowerTCrit
				double positionAsPercentage = pureFStat/fStatScale;
				
				//now that we understand this proportion, we can apply this position along the continuum between lower/higherTCrit to 
				//the significance levels, giving us our exact position between the range of significance/p-values :D
				double pValueScale = lowerAlpha - higherAlpha;
				
				return lowerAlpha - (pValueScale * positionAsPercentage);
			} else if (fCritGetter.returnFCrit(columnDoF, rowDoF, significanceLevelsList.get(i)) == fStat) {
				return Double.parseDouble(significanceLevelsList.get(i));
			}
			
		}
	
		return 0;
	}
	
	
}
