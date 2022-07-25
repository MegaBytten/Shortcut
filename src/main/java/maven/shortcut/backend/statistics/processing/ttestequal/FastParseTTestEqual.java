package maven.shortcut.backend.statistics.processing.ttestequal;

import maven.shortcut.backend.statistics.Analysis;
import maven.shortcut.backend.statistics.processing.criticals.PCriticalValue;
import maven.shortcut.backend.statistics.processing.criticals.TCriticalValue;
import maven.shortcut.backend.utils.Data;
import maven.shortcut.backend.utils.FTest;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.scrollpanepanels.AnalysisPage;
import maven.shortcut.frontend.scrollpanepanels.StatisticsErrorsPanel;

public class FastParseTTestEqual {
	
	Data data;
	int group1SampleSize;
	int group2SampleSize;
	double group1Mean;
	double group2Mean;
	double group1Var;
	double group2Var;
	
	public FastParseTTestEqual() {
		System.out.println("Fast Equal T-Test Processing Initiated.");
		ParseData parseData = new ParseData();
		parseData.parseData();
		data = parseData.getParsedData();
		
		defineTTestVariables();
		
		if (fTest()) {
			//check to see if the variance is equal, if so proceed with T-Test!
			double tStat = getTStat();
			TCriticalValue getTCrit = new TCriticalValue();
			double tCrit = getTCrit.returnTCrit((group1SampleSize + group2SampleSize - 2), "two", 0.05);
			
			if (tStat > tCrit) {
				PCriticalValue getPCrit = new PCriticalValue();
				double pValue = getPCrit.returnPCritTStat(tStat, group1SampleSize+group2SampleSize-2);
				defineAnalytics(group1SampleSize+group2SampleSize-2, pValue, 0.05, tStat, tCrit);
				
				
				new Analysis();
				AnalysisPage presentData = new AnalysisPage();
				presentData.enterPostAnalysis();
				
			} else {
				System.out.println("Functionality Marker: Insignificant Difference Detected.");
				System.out.println("Launching relevant Error page, Code: 999");
				StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
				errorLauncher.enterStatsErrorPanel("tStat is not greater than tCrit. Therefore null hypothesis is accepted."
						+ " (Insignificant Difference)");
			}
			
			
			
			//if check after get tCrit
			
		} else {
			//non equal variance! Procedurally end processing of Equal Var T-Test and warn user.
			System.out.println("Functionality Marker: T-Test of Unequal Var detected on attempted Equal Var test!");
			System.out.println("Launching relevant Error page, Code: 001");
			StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
			errorLauncher.enterStatsErrorPanel("Unequal Variance detected on attempted Equal Variance T-Test! Change Test Type!");
		}
		
	}
	
	private void defineTTestVariables() {
		group1SampleSize = data.getGroup(0).size();
		group2SampleSize = data.getGroup(1).size();
		group1Mean = data.getGroup(0).getMean();
		group2Mean = data.getGroup(1).getMean();
		group1Var = data.getGroup(0).getVariance();
		group2Var = data.getGroup(1).getVariance();
	}
	
	
	private boolean fTest() {
		FTest fTest = new FTest();
		if (fTest.isEqualVar(group1Var, group2Var, group1SampleSize, group2SampleSize, "0.05")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
	private double getTStat() {
		
		// 							xMean - yMean
		// tStat = -------------------------------------------------
		//		SqRoot( (pVar/sampleSizeX) + (pVar/sampleSizeY) )
		
		
		
		//			(sSX - 1)* xVar + (sSY - 1)* yVar
		// pVar =  --------------------------------------
		//					sSX + sSY - 2
		
		
		double numerator;
		
		if (group1Mean > group2Mean) {
			numerator = group1Mean - group2Mean;
		} else {
			numerator = group2Mean - group1Mean;
		}
		//just to make sure numerator is POSITIVE
		
		double pVar = ( ((group1SampleSize - 1)* group1Var + (group2SampleSize - 1)* group2Var) /*numerator*/ 
						/ (group1SampleSize + group2SampleSize - 2)/*denominator*/);
		
		double denominator = (Math.sqrt( (pVar/group1SampleSize) + (pVar/group2SampleSize) ));
		return numerator/denominator;
	}
	
	private void defineAnalytics(int DOF, double pValue, double significanceLevel, double tStat, double tCrit) {
		data.setTestType("ttest");
		data.setDegreesOfFreedom(DOF);
		data.setPValue(pValue);
		data.setSignificanceLevel(significanceLevel);
		data.setTStat(tStat);
		data.setTCrit(tCrit);
	}
	
}
