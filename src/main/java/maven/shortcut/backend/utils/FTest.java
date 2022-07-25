package maven.shortcut.backend.utils;

import maven.shortcut.backend.statistics.processing.criticals.FCriticalValue;

public class FTest {
	
	
	public boolean isEqualVar(double group1Var, double group2Var, int group1SampleSize, int group2SampleSize, String significanceLevel) {
		
		FCriticalValue fCritGetter = new FCriticalValue();
		
		double fStat = 0;
		double fCrit = 0;
		if (group1Var > group2Var) {
			fStat = group1Var / group2Var;
			fCrit = fCritGetter.returnFCrit(group1SampleSize-1, group2SampleSize-1, significanceLevel);
			
		} else if (group2Var > group1Var) {
			fStat = group2Var / group1Var;
			fCrit = fCritGetter.returnFCrit(group2SampleSize-1, group1SampleSize-1, significanceLevel);
			
		} else {
			fStat = 1;
			fCrit = fCritGetter.returnFCrit(group1SampleSize-1, group2SampleSize-1, significanceLevel);
		}
		//run an F-test to test if variances are equal
		//and return the fCrit value according to the respective formula/differences in Variance
		
		if (fCrit > fStat) {
			//equal variance! continue in processing Equal Var T-Test
			return true;
			
		} else {
			//non equal variance! Procedurally end processing of Equal Var T-Test and warn user.
			return false;
		} 
		
	}

}
