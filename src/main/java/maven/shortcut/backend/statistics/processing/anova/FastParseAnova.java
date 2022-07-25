package maven.shortcut.backend.statistics.processing.anova;

import java.util.ArrayList;
import java.util.List;

import maven.shortcut.backend.statistics.processing.criticals.FCriticalValue;
import maven.shortcut.backend.statistics.processing.criticals.PCriticalValue;
import maven.shortcut.backend.utils.Data;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.scrollpanepanels.AnovaToTukeyPanel;
import maven.shortcut.frontend.scrollpanepanels.StatisticsErrorsPanel;

public class FastParseAnova {
	
	Data data;
	int totalSampleSize;
	
	List<List<Double>> zippedData = new ArrayList<List<Double>>();
	
	List<Double> groupMeans = new ArrayList<Double>();
	List<String> variables = new ArrayList<String>();
	
	int numberOfGroups;
	double totalMean;
	int totalValues;
	double numerator;
	double denominator;
	double fStat;
	double pValue;
	
	public FastParseAnova() {
		ParseData parseData = new ParseData();
		parseData.parseData();
		data = parseData.getParsedData();
		
		if (data.getNumberOfGroups() <= 2) {
			System.out.println("Functionality Marker: Parsed Data - unable to Process DataZip as numberOfGroups < 3. T-Test recommended.");
			System.out.println("Launching relevant Error page, Code: 300");
			StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
			errorLauncher.enterStatsErrorPanel("Data does not support ANOVA! < 3 groups of Data detected!");
		} else {
			defineAnovaVariables();
			calculateNumerator();
			calculateMeanSquareError();
			
			fStat = numerator/denominator;
			
			FCriticalValue fCritGetter = new FCriticalValue();
			double fCrit = fCritGetter.returnFCrit(numberOfGroups-1, totalValues - numberOfGroups, "0.05");
			
			PCriticalValue getPCrit = new PCriticalValue();
			pValue = getPCrit.returnPCritFStat(fStat, numberOfGroups-1, totalValues - numberOfGroups);
			
			if (fStat > fCrit) {
				//great! null hypothesis rejected there is some difference between the means of the groups
				System.out.println("fStat Value of " + fStat + " is greater than fCrit of " + fCrit + "!");
				
				saveData();
				AnovaToTukeyPanel launchTukeyPrompt = new AnovaToTukeyPanel();
				launchTukeyPrompt.enterAnovaToTukeyPanel();
				
			} else {
				//insignifcant difference detected!
				System.out.println("Functionality Marker: Insignificant Difference Detected.");
				System.out.println("Launching relevant Error page, Code: 999");
				StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
				errorLauncher.enterStatsErrorPanel("fStat is not greater than fCrit. Therefore null hypothesis is accepted."
						+ " (Insignificant Difference)");
			}
		}
	}
	
	private void defineAnovaVariables() {
		numberOfGroups = data.getNumberOfGroups();
		
		double totalSum = 0;
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			totalValues = totalValues + data.getGroup(i).size();
			totalSum = totalSum+ data.getGroup(i).getSum();
		}
		totalMean = totalSum/totalValues;
	}
	
	private void calculateNumerator() {
		double partOne = 0;
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			partOne = partOne + (data.getGroup(i).size() * Math.pow(data.getGroup(i).getMean()-totalMean, 2));
		}
		double partTwo = numberOfGroups - 1;
		numerator = partOne/partTwo;
	}
	
	private void calculateMeanSquareError() {
		double sumOfXMinusXBarSquared = 0;
		double totalValuesMinusGroups = totalValues - numberOfGroups;
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			
			for (int x = 0; x < data.getGroup(i).size(); x++) {
				sumOfXMinusXBarSquared = sumOfXMinusXBarSquared + Math.pow(data.getGroup(i).get(x) - data.getGroup(i).getMean(), 2);
			}
		}
		denominator = sumOfXMinusXBarSquared/totalValuesMinusGroups;
	}
	
	private void saveData() {
		data.setTestType("anova");
		data.setPValue(pValue);
		data.setFStat(fStat);
		data.setANOVAColumnDOF(numberOfGroups-1);
		data.setANOVARowDOF(totalValues - numberOfGroups);
		data.setSignificanceLevel(0.05);
	}
	
	
}
