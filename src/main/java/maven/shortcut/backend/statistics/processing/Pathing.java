package maven.shortcut.backend.statistics.processing;


import maven.shortcut.backend.statistics.Redirectory;
import maven.shortcut.backend.statistics.processing.anova.FastParseAnova;
import maven.shortcut.backend.statistics.processing.ttestequal.FastParseTTestEqual;
import maven.shortcut.backend.statistics.processing.ttestpaired.FastParseTTestPaired;
import maven.shortcut.backend.statistics.processing.ttestunequal.FastParseTTestUnequal;
import maven.shortcut.backend.statistics.processing.tukey.FastParseTukey;

public class Pathing {
	
	public void compass() {
		
		int testType = Redirectory.testType;
		
		switch (testType) {
	    case 1:  //T equal
	    	if (Redirectory.isFast) {
	    		new FastParseTTestEqual();
	    	} else {
	    		//launch custom parsing shit
	    	}
	             break;
	    case 2:  //T un
	    	if (Redirectory.isFast) {
	    		new FastParseTTestUnequal();
	    	} else {
	    		//launch custom parsing shit
	    	}
	             break;
	    case 3:  //Paired T
	    	if (Redirectory.isFast) {
	    		new FastParseTTestPaired();
	    	}
	    	
	             break;
	    case 4:  //Anova
	    	if (Redirectory.isFast) {
	    		new FastParseAnova();
	    	}
	             break;
	    case 5:  //Tukey
	    	if (Redirectory.isFast) {
	    		new FastParseTukey(false);
	    	}
	             break;
	    case 6:  //Mann
	             break;
	    case 7:  //Chi
	             break;
	    case 8:  //Fisher
	             break;
	    case 9:  //Pearson
	             break;
	    case 10: //Spearman
	             break;
	    case 0:  //none
	    		 break;
			}
	
	}
	
	

}
