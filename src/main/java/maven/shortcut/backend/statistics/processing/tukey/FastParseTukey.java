package maven.shortcut.backend.statistics.processing.tukey;

import maven.shortcut.backend.statistics.Analysis;
import maven.shortcut.backend.statistics.processing.criticals.QCriticalValue;
import maven.shortcut.backend.utils.Data;
import maven.shortcut.backend.utils.GroupData;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.scrollpanepanels.AnalysisPage;

public class FastParseTukey {
	
	Data data;
	
	int numberOfGroups;
	double significanceLevel;
	int totalSampleSize;
	double meanSquareError;
	int smallestSampleSize;
	
	
	
	public FastParseTukey(boolean fromAnova) {
		ParseData parseData = new ParseData();
		if (!fromAnova) {
			parseData.parseData();
		}
		data = parseData.getParsedData();
		defineTukeyVariables();
		
		QCriticalValue getQCrit = new QCriticalValue();
		double qCrit = getQCrit.returnQCrit(numberOfGroups, totalSampleSize, "0.05");
		
		double tukeyCrit = getTukeyCriterion(qCrit);
		runComparisons(tukeyCrit);
		
		System.out.println("Functionality Marker: TukeyLoop, runComparisons Post-Check:");
		
		for (int i = 0; i < numberOfGroups; i++) {
			System.out.println("Primary Group: " + data.getGroup(i).getName() + ": " + data.getGroup(i).tukeyTEMP());	
		}
		
		if (!fromAnova) {
			System.out.println("Generating Analysis Without Anova");
			analysisWithoutAnova();
		} else {
			System.out.println("Generating Analysis With Anova");
			analysisFromAnova();
		}
	}
	
	private void defineTukeyVariables() {
		numberOfGroups = data.getNumberOfGroups();
		significanceLevel = 0.05;
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			totalSampleSize = totalSampleSize + data.getGroup(i).size();
		}
		smallestSampleSize = data.getSmallestGroupDataSize();
		meanSquareError = calculateMeanSquareError();
	}
	
	private double calculateMeanSquareError() {
		double sumOfXMinusXBarSquared = 0;
		double totalValuesMinusGroups = totalSampleSize - numberOfGroups;
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			for (int x = 0; x < data.getGroup(i).size(); x++) {
				sumOfXMinusXBarSquared = sumOfXMinusXBarSquared + Math.pow(data.getGroup(i).get(x) - data.getGroup(i).getMean(), 2);
			}
		}
		return sumOfXMinusXBarSquared/totalValuesMinusGroups;
	}
	
	private double getTukeyCriterion(double qCrit) {
		return qCrit*Math.sqrt(meanSquareError/smallestSampleSize);
	}
	
	private void runComparisons(double tukeyCrit) {
		
		System.out.println("Running Tukey Comparisons - Tukey Criterion=" + tukeyCrit);
		
		for (int i = 0; i < numberOfGroups; i++) {
			for (int x = 0; x < numberOfGroups; x++) {
				
				if (i == x) {
					//Comparing Same Primary and Secondary group! No code!
				} else {
					GroupData primaryGroupData = data.getGroup(i);
					GroupData secondaryGroupData = data.getGroup(x);
					
					if (Math.abs(primaryGroupData.getMean() - secondaryGroupData.getMean()) > tukeyCrit) {
						System.out.println("Significant difference between primary group: " + primaryGroupData.getName() + "and" + secondaryGroupData.getName() + " detected.");
						primaryGroupData.tukeyAddSignificantGroupName(secondaryGroupData.getName());
						primaryGroupData.tukeyAddSignificantGroupNameWithPValue(secondaryGroupData.getName(), calculatePValue(Math.abs(primaryGroupData.getMean() - secondaryGroupData.getMean())));
					}	
				}	
			}
		}
	/*
	
	function of this code: to loop through every group (Primary Group) and compare its mean
	against the mean of every other group (Secondary Group) if significance is detected between the means 
	(Difference must be > tukeyCriterion) then the secondaryGroup's .getName is added to an ArrayList<String> saved within the primaryGroup's GroupData. 
	The pValue is then added to a Hashmap keyed with the secondaryGroup's .getname and saved within the primaryGroup's GroupData.
		
	*/
	}
		

	private double calculatePValue(double difference) {
		
		QCriticalValue getQCrit = new QCriticalValue();
		double upperTukeyCrit = getTukeyCriterion(getQCrit.returnQCrit(numberOfGroups, totalSampleSize, "0.01"));
		double lowerTukeyCrit = getTukeyCriterion(getQCrit.returnQCrit(numberOfGroups, totalSampleSize, "0.05"));
		double upperPValue = 0.01;
		double lowerPValue = 0.05;
		
		if (difference > upperTukeyCrit) {
			return 0.01;
		}
		
		//tukeyScale quantifies the difference between the lower and higher tCrit values
		double tukeyScale = upperTukeyCrit - lowerTukeyCrit;
		//pureTukey quantifies how far away from the lowerTCrit tStat is, therefore allowing us to see the percentage of where
		//the tukeyCrit lies between lower and higher tCrits
		double pureTukey = difference - lowerTukeyCrit;
		System.out.println("TRACE STACK: Difference= " + difference + " lowerQCrit=" + lowerTukeyCrit);
		//this is the percentage of where tukeyCrit lies between lower and higherQCrit with respect to the lowerQCrit
		double positionAsPercentage = pureTukey/tukeyScale;
		System.out.println("Position as percentage: " + positionAsPercentage);
		
		
		//now that we understand this proportion, we can apply this position along the continuum between lower/higherTCrit to 
		//the significance levels, giving us our exact position between the range of significance/p-values :D
		
		double pValueScale = lowerPValue - upperPValue;
		
		System.out.println("Sig diff found, pValue calculated. Returning pValue of: " + Double.toString(lowerPValue - (pValueScale * positionAsPercentage)));
		
		return lowerPValue - (pValueScale * positionAsPercentage);
	}
	
	private void analysisFromAnova() {
		data.setTestType("tukeyfromanova");
		new Analysis();
		
		AnalysisPage presentAnalysis = new AnalysisPage();
		presentAnalysis.enterPostAnalysis();
	}
	
	
	private void analysisWithoutAnova() {
		data.setTestType("tukey");
		new Analysis();
		
		AnalysisPage presentAnalysis = new AnalysisPage();
		presentAnalysis.enterPostAnalysis();
	}
	
	
	
}
