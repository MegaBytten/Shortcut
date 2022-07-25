package maven.shortcut.backend.statistics.processing.ttestpaired;

import maven.shortcut.backend.statistics.Analysis;
import maven.shortcut.backend.statistics.processing.criticals.PCriticalValue;
import maven.shortcut.backend.statistics.processing.criticals.TCriticalValue;
import maven.shortcut.backend.utils.Data;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.scrollpanepanels.AnalysisPage;
import maven.shortcut.frontend.scrollpanepanels.StatisticsErrorsPanel;

public class FastParseTTestPaired {
	
	Data data;
	
	int sampleSize;
	double group1Mean;
	double group2Mean;
	double group1Var;
	double group2Var;
	int DoF;
	
	public FastParseTTestPaired(){
		System.out.println("Fast Equal T-Test Processing Initiated.");
		ParseData parseData = new ParseData();
		parseData.parseData();
		data = parseData.getParsedData();
		
		if (data.getGroup(0).size() != data.getGroup(1).size()){
			
			System.out.println("Functionality Marker: DataSizes vary!.");
			System.out.println("Launching relevant Error page, Code: 998");
			StatisticsErrorsPanel errorLauncher = new StatisticsErrorsPanel();
			errorLauncher.enterStatsErrorPanel("Data does not support Paired T-Test Format! Groups are Different Sizes!");
			
		} else {
			defineTTestVariables();
			double tStat = getTStat();
			TCriticalValue getTCrit = new TCriticalValue();
			double tCrit = getTCrit.returnTCrit(DoF, "two", 0.05);
			
			if (tStat > tCrit) {
				//significant difference detected!
				
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
			
		}
		
		
	}
	
	private void defineTTestVariables() {
		sampleSize = data.getGroup(1).size();
		DoF = sampleSize - 1;
		group1Mean = data.getGroup(0).getMean();
		group2Mean = data.getGroup(1).getMean();
		group1Var = data.getGroup(0).getVariance();
		group2Var = data.getGroup(1).getVariance();
	}
	
	
	
	private double getTStat() {
		
		//												Summation of (x - y)
		//   tStat  =   -------------------------------------------------------------------------------------------------
		//			Math.sqrt( ((sampleSize * Σ(x-y)^2) - (Σ(x-y))^2 )/ sampleSize - 1 )
		
		
		double sumD = 0;
		for (int i = 0; i < sampleSize; i++) {
			sumD = sumD + (data.getGroup(0).get(i) - data.getGroup(1).get(i));
		}
		
		double sumDSqrd = 0;
		for (int i = 0; i < sampleSize; i++) {
			sumDSqrd = sumDSqrd + Math.pow((data.getGroup(0).get(i) - data.getGroup(1).get(i)), 2);
		}
		
		double numerator = 0;
		if (sumD < 0) {
			numerator = sumD*-1;
		} else {
			numerator = sumD;
		}
		double denominatorRootTopHalf = (sampleSize*sumDSqrd) - Math.pow(sumD, 2);
		double denominatorRootBottomHalf = sampleSize - 1;
		double denominator = Math.sqrt(denominatorRootTopHalf/denominatorRootBottomHalf);
		
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
