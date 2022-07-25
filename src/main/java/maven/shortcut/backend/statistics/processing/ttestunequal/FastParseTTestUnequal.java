package maven.shortcut.backend.statistics.processing.ttestunequal;

import maven.shortcut.backend.statistics.Analysis;
import maven.shortcut.backend.statistics.processing.criticals.PCriticalValue;
import maven.shortcut.backend.statistics.processing.criticals.TCriticalValue;
import maven.shortcut.backend.utils.Data;
import maven.shortcut.backend.utils.FTest;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.scrollpanepanels.AnalysisPage;
import maven.shortcut.frontend.scrollpanepanels.StatisticsErrorsPanel;

public class FastParseTTestUnequal {
	
	Data data;
	
	int group1SampleSize;
	int group2SampleSize;
	double group1Mean;
	double group2Mean;
	double group1Var;
	double group2Var;
	int DoF;
	
	public FastParseTTestUnequal() {
		System.out.println("Fast Unequal T-Test Processing Initiated.");
		ParseData parseData = new ParseData();
		parseData.parseData();
		data = parseData.getParsedData();
		
		defineTTestVariables();
		
		
		if (!fTest()) {
			
			double tStat = getTStat();
			TCriticalValue getTCrit = new TCriticalValue();
			double tCrit = getTCrit.returnTCrit(DoF, "two", 0.05);
			
			if (tStat > tCrit) {
				PCriticalValue getPCrit = new PCriticalValue();
				double pValue = getPCrit.returnPCritTStat(tStat, DoF);
				defineAnalytics(DoF, pValue, 0.05, tStat, tCrit);
				
				new Analysis();
				AnalysisPage presentData = new AnalysisPage();
				presentData.enterPostAnalysis();
				
				
			} else {
				//insignifcant difference detected!
				System.out.println("Functionality Marker: Insignificant Difference Detected.");
				System.out.println("Launching relevant Error page, Code: 999");
				StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
				errorLauncher.enterStatsErrorPanel("tStat is not greater than tCrit. Therefore null hypothesis is accepted."
						+ " (Insignificant Difference)");
			}
			
		} else {
			//equal variance
			System.out.println("Functionality Marker: T-Test of Equal Var detected on attempted Unequal Var test!");
			System.out.println("Launching relevant Error page, Code: 002");
			StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
			errorLauncher.enterStatsErrorPanel("Equal Variance detected on attempted Unequal Variance T-Test! Change Test Type!");
		}
	}
	
	public void defineTTestVariables() {
		group1SampleSize = data.getGroup(0).size();
		group2SampleSize = data.getGroup(1).size();
		group1Mean = data.getGroup(0).getMean();
		group2Mean = data.getGroup(1).getMean();
		group1Var = data.getGroup(0).getVariance();
		group2Var = data.getGroup(1).getVariance();
		DoF = (int) Math.round(getDoFDouble());
	}
	
	public double getDoFDouble() {
		//								Math.pow( (xVar/xSampleSize) +yVar/ySampleSize), 2 )
		// DoF = -------------------------------------------------------------------------------------------------------------------
		//		   ( Math.pow((xVar/xSampleSize), 2)/xSampleSize-1 ) + ( Math.pow((yVar/ySampleSize), 2)/ySampleSize-1 )
		
		double sqrtNumerator = (group1Var/group1SampleSize) + (group2Var/group2SampleSize);
		double numerator = Math.pow(sqrtNumerator, 2);
		
		double denominatorFirstHalf = Math.pow((group1Var/group1SampleSize), 2) / (group1SampleSize-1);
		double denominatorSecondHalf = Math.pow((group2Var/group2SampleSize), 2) / (group2SampleSize-1);
		double denominator = denominatorFirstHalf + denominatorSecondHalf;
		return numerator/denominator;
	}
	
	private boolean fTest() {
		FTest fTest = new FTest();
		if (fTest.isEqualVar(group1Var, group2Var, group1SampleSize, group2SampleSize, "0.05")) {
			return true;
		} else {
			return false;
		}
	}
	
	public double getTStat() {
		//							xMean - yMean
		// tStat = -------------------------------------------------
		//		   SqRoot( (xVar/xSampleSize) + (yVar/ySampleSize) )
		
		double numerator = 0;
		if (group1Mean > group2Mean) {
			numerator = group1Mean - group2Mean;
		} else {
			numerator = group2Mean - group1Mean;
		}
		double denominator = Math.sqrt(  (group1Var/group1SampleSize) + (group2Var/group2SampleSize));
		return numerator/denominator;
	}
	
	
	public void defineAnalytics(int DOF, double pValue, double significanceLevel, double tStat, double tCrit) {
		data.setTestType("ttest");
		data.setDegreesOfFreedom(DOF);
		data.setPValue(pValue);
		data.setSignificanceLevel(significanceLevel);
		data.setTStat(tStat);
		data.setTCrit(tCrit);
	}
	
}
