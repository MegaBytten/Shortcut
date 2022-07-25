package maven.shortcut.backend.statistics;

import maven.shortcut.backend.statistics.processing.criticals.PCriticalValue;
import maven.shortcut.backend.utils.Data;
import maven.shortcut.backend.utils.GroupData;
import maven.shortcut.backend.utils.ParseData;

public class Analysis {
	
	Data data;
	
	public static StringBuilder analysisText = new StringBuilder();
	
	
	public Analysis() {
		ParseData parseData = new ParseData();
		data = parseData.getParsedData();
		
		if (checkAcceptNullHypothesis()) {
			generateNullHypothesis();
		} else {
			if (data.getTestType().equalsIgnoreCase("ttest")) {
				generateTTestAnalysis();
			} else if (data.getTestType().equalsIgnoreCase("anova")) {
				 generateAnovaAnalysis();
			} else if (data.getTestType().equalsIgnoreCase("tukeyfromanova")) {
				generateTukeyFromAnovaAnalysis();
			} else if (data.getTestType().equalsIgnoreCase("tukey")) {
				generateTukeyWithoutAnova();
			} else {
				System.out.println("Error: TestType not specified, Analysis generation interrupted.");
			}
		}
	}
		
		
	
	private void generateTTestAnalysis() {
		appendAnalysisText("There is ");
		
		if (data.getPValue() < data.getSignificanceLevel()*0.8) {
			appendAnalysisText("strong");
		} else if (data.getPValue() <= data.getSignificanceLevel()) {
			appendAnalysisText("weak");
		} else {
			//loop out!
			appendAnalysisText("insufficient");
		}
		
		appendAnalysisText(" evidence (p");
		
		if (PCriticalValue.isTiny) {
			appendAnalysisText("<<");
		} else {
			appendAnalysisText("=");
		}
		
		appendAnalysisText(round(data.getPValue(), 4) + ") that ");
		
		if (data.getGroup(0).getMean() > data.getGroup(1).getMean()) {
			appendAnalysisText(data.getGroup(0).getName());
		} else if (data.getGroup(0).getMean() < data.getGroup(1).getMean()) {
			appendAnalysisText(data.getGroup(1).getName());
		} else {
			//not fully sure can figure out later
			//equal means - accept null hypothesis based on tStat
		}
		
		appendAnalysisText(" was significantly greater (tStat=" + round(data.getTStat(), 2) + ", DoF=" + data.getDegreesOfFreedom() + ") than ");
		if (data.getGroup(0).getMean() > data.getGroup(1).getMean()) {
			appendAnalysisText(data.getGroup(0).getName());
		} else if (data.getGroup(0).getMean() < data.getGroup(1).getMean()) {
			appendAnalysisText(data.getGroup(1).getName());
		} else {
			//not fully sure can figure out later
			//equal means - accept null hypothesis based on tStat
		}
		
		appendAnalysisText(" with the average difference being ");
		if (data.getGroup(0).getMean() > data.getGroup(1).getMean()) {
			appendAnalysisText(String.valueOf(round(data.getGroup(0).getMean()-data.getGroup(1).getMean(), 3)));
		} else if (data.getGroup(0).getMean() < data.getGroup(1).getMean()) {
			appendAnalysisText(String.valueOf(round(data.getGroup(1).getMean()-data.getGroup(0).getMean(), 3)));
		} else {
			//not fully sure can figure out later
			//equal means - accept null hypothesis based on tStat
		}
		
		appendAnalysisText(" units.");
		
	}
	
	private void generateAnovaAnalysis() {
		appendAnalysisText("There is ");
		
		if (data.getPValue() < data.getSignificanceLevel()*0.8) {
			appendAnalysisText("strong");
		} else if (data.getPValue() <= data.getSignificanceLevel()) {
			appendAnalysisText("weak");
		}
		
		appendAnalysisText(" evidence (p");
		
		if (PCriticalValue.isTiny) {
			appendAnalysisText("<<");
		} else {
			appendAnalysisText("=");
		}
		appendAnalysisText(round(data.getPValue(), 4) + ") to reject the null hypothesis and accept the alternate hypothesis "
				+ "suggesting that there is a significant difference (fStat=" + round(data.getFStat(), 2) + ", DoF=[" + data.getANOVAColumnDOF() + ", " + data.getANOVARowDOF() + "]) between:");
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			if (i == data.getNumberOfGroups()-1) {
				appendAnalysisText(" and " + data.getGroup(i).getName());
			} else {
				appendAnalysisText(" " + data.getGroup(i).getName() + ",");
			}
		}
		
		appendAnalysisText(" with their average means being: ");
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			if (i == data.getNumberOfGroups()-1) {
				appendAnalysisText(" and " + round(data.getGroup(i).getMean(), 2));
			} else {
				appendAnalysisText(" " + round(data.getGroup(i).getMean(),2) + ",");
			}
		}
		appendAnalysisText(" respectively.");
	}
	
	
	/*
	 * This data is saved from ANOVA:
	 * private void saveData() {
		data.setTestType("anova");
		data.setPValue(pValue);
		data.setFStat(fStat);
		data.setANOVAColumnDOF(numberOfGroups-1);
		data.setANOVARowDOF(totalValues - numberOfGroups);
		data.setSignificanceLevel(0.05);
		}
	 */
	
	private void generateTukeyFromAnovaAnalysis() {
		appendAnalysisText("By initially conducting an Anova test, it was shown that there is ");
		
		if (data.getPValue() < data.getSignificanceLevel()*0.8) {
			appendAnalysisText("strong");
		} else if (data.getPValue() <= data.getSignificanceLevel()) {
			appendAnalysisText("weak");
		} else {
			//loop out!
			appendAnalysisText("insufficient");
		}
		appendAnalysisText(" evidence (p");
		if (PCriticalValue.isTiny) {
			appendAnalysisText("<<");
		} else {
			appendAnalysisText("=");
		}
		
		appendAnalysisText(round(data.getPValue(), 2) + ") of a significant difference present in the groups of");
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			if (i == data.getNumberOfGroups() - 1) {
				appendAnalysisText(" and " + data.getGroup(i).getName());
			} else {
				appendAnalysisText(" " + data.getGroup(i).getName()+ ",");
			}
		}
		appendAnalysisText(". Further investigation of the data through a Tukey Test shows that specifically:");
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			appendAnalysisText(" With respect to " + data.getGroup(i).getName() + " (mean=" + round(data.getGroup(i).getMean(), 2) + "), group(s): ");
			
			int secondaryGroupSize = data.getGroup(i).tukeyGetSignificantGroupSize();
			for (int x = 0; x < secondaryGroupSize; x++) {
				
				GroupData primaryGroup = data.getGroup(i);
				GroupData secondaryGroup = data.findGroup(data.getGroup(i).tukeyGetSignificantGroupName(x));
				
				if (secondaryGroupSize == 0) {
					appendAnalysisText(" (No Other Groups)");
					break;
				}else if (secondaryGroupSize == 1) {
					appendAnalysisText(secondaryGroup.getName() + " (mean=" + round(secondaryGroup.getMean(), 2) + ", pValue=" + round(primaryGroup.tukeyGetPValue(secondaryGroup.getName()), 3) + ")");
					break;
				}else if (x == secondaryGroupSize-1) {
					appendAnalysisText(" and " + secondaryGroup.getName() + " (mean=" + round(secondaryGroup.getMean(), 2) + ", pValue=" + round(primaryGroup.tukeyGetPValue(secondaryGroup.getName()), 3) + ")");
				} else {
					appendAnalysisText(" " + secondaryGroup.getName() + " (mean=" + round(secondaryGroup.getMean(), 2) + ", pValue=" + round(primaryGroup.tukeyGetPValue(secondaryGroup.getName()), 3) + "),");
				}
			}
			appendAnalysisText(" were found to be significantly different.");
			
		}
	}
	
	
	private void generateTukeyWithoutAnova() {
		appendAnalysisText("By using a Tukey Test, it was found: ");
		
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			appendAnalysisText(" With respect to " + data.getGroup(i).getName() + " (mean=" + round(data.getGroup(i).getMean(), 2) + "), group(s): ");
			
			int secondaryGroupSize = data.getGroup(i).tukeyGetSignificantGroupSize();
			for (int x = 0; x < secondaryGroupSize; x++) {
				
				GroupData primaryGroup = data.getGroup(i);
				GroupData secondaryGroup = data.findGroup(data.getGroup(i).tukeyGetSignificantGroupName(x));

				if (secondaryGroupSize == 0) {
					appendAnalysisText(" (No Other Groups)");
					break;
				}else if (secondaryGroupSize == 1) {
					appendAnalysisText(secondaryGroup.getName() + " (mean=" + round(secondaryGroup.getMean(), 2) + ", pValue=" + round(primaryGroup.tukeyGetPValue(secondaryGroup.getName()), 3) + ")");
					break;
				}else if (x == secondaryGroupSize-1) {
					appendAnalysisText(" and " + secondaryGroup.getName() + " (mean=" + round(secondaryGroup.getMean(), 2) + ", pValue=" + round(primaryGroup.tukeyGetPValue(secondaryGroup.getName()), 3) + ")");
				} else {
					appendAnalysisText(" " + secondaryGroup.getName() + " (mean=" + round(secondaryGroup.getMean(), 2) + ", pValue=" + round(primaryGroup.tukeyGetPValue(secondaryGroup.getName()), 3) + "),");
				}
			}
			appendAnalysisText(" were found to be significantly different.");
			
		}
	}

	
	public StringBuilder getAnalysisText() {
		return analysisText;
	}
	
	private void appendAnalysisText(String newPhrase) {
			analysisText.append(newPhrase);
	}
	
	public boolean checkAcceptNullHypothesis() {
		if (data.getPValue() > data.getSignificanceLevel()) {
			return true;
		} else {
			return false;
		}
	}
	
	private void generateNullHypothesis() {
		analysisText.append("There is insufficient evidence (p=" + data.getPValue() + ") supporting the significant difference between groups ");
		for (int i = 0; i < data.getNumberOfGroups(); i++) {
			if (i == data.getNumberOfGroups() - 1) {
				appendAnalysisText(" and " + data.getGroup(i).getName() + ".");
			} else {
				appendAnalysisText(" " + data.getGroup(i).getName()+ ",");
			}
		}
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
