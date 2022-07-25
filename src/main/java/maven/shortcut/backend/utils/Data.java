package maven.shortcut.backend.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {
	
	List<GroupData> data = new ArrayList<GroupData>();
	
	String testType;
	double pValue;
	double significance;
	double tStat;
	double tCrit;
	double fStat;
	int smallestGroupDataSize;
	int anovaColumnDOF;
	int anovaRowDOF;
	int DOF;
	
	public Data() {
	}
	
	public void addGroups(int i) {
		for (int x = 0; x < i; x++) {
			data.add(new GroupData());
		}
	}
	
	public void addGroup() {
		data.add(new GroupData());
	}
	
	public void addGroup(String name) {
		data.add(new GroupData(name));
	}
	
	public int getNumberOfGroups() {
		return data.size();
	}
	
	public GroupData getGroup(int index) {
		return data.get(index);
	}
	
	public GroupData findGroup(String groupName) {
		for (GroupData gd : data) {
			if (gd.getName().equalsIgnoreCase(groupName)) {
				return gd;
			}
		}
		return null;
	}
	
	public void setTestType(String testType) {
		this.testType = testType;
	}
	
	public String getTestType() {
		return testType;
	}
	
	public void setPValue(double pValue) {
		this.pValue = pValue;
	}
	
	public double getPValue() {
		return pValue;
	}
	
	public void setSignificanceLevel(double significance) {
		this.significance = significance;
	}
	
	public double getSignificanceLevel() {
		return significance;
	}
	
	public void setTStat(double tStat) {
		this.tStat = tStat;
	}
	
	public double getTStat() {
		return tStat;
	}
	
	public void setTCrit(double tCrit) {
		this.tCrit = tCrit;
	}
	
	public double getTCrit() {
		return tCrit;
	}
	
	public void setFStat(double fStat) {
		this.fStat = fStat;
	}
	
	public double getFStat() {
		return fStat;
	}
	
	public void setANOVAColumnDOF(int columnDOF) {
		if (getTestType().equalsIgnoreCase("anova")) {
			anovaColumnDOF = columnDOF;
		} else {
			System.out.println("Error. Attempting to set ANOVA ColumnDOF although Test Type is not ANOVA.");
		}
	}
	
	public int getANOVAColumnDOF() {
		if (getTestType().equalsIgnoreCase("anova")) {
			return anovaColumnDOF; 
		} else {
			System.out.println("Error. Attempting to get ANOVA ColumnDOF although Test Type is not ANOVA.");
			return 0;
		}
	}
	
	public void setANOVARowDOF(int rowDOF) {
		if (getTestType().equalsIgnoreCase("anova")) {
			this.anovaRowDOF = rowDOF;
		} else {
			System.out.println("Error. Attempting to set ANOVA RowDOF although Test Type is not ANOVA.");
		}
	}
	
	public int getANOVARowDOF() {
		if (getTestType().equalsIgnoreCase("anova")) {
			return anovaRowDOF;
		} else {
			System.out.println("Error. Attempting to get ANOVA RowDOF although Test Type is not ANOVA.");
			return 0;
		}
	}
	
	public void setDegreesOfFreedom(int DOF) {
		this.DOF = DOF;
	}
	
	public int getDegreesOfFreedom() {
		return DOF;
	}
	
	public void clearData() {
		data.clear();
	}
	
	public int getSmallestGroupDataSize() {
		List<Integer> groupSizes = new ArrayList<Integer>();
		for (GroupData gd : data) {
			groupSizes.add(gd.size());
		}
		return groupSizes.get(groupSizes.indexOf(Collections.min(groupSizes)));
	}
	
}
