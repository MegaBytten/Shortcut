package maven.shortcut.backend.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupData {
	
	List<Double> data = new ArrayList<Double>();
	List<String> tukeySignificantGroupNames = new ArrayList<String>();
	HashMap<String, Double> tukeySignificantGroupNamesWithPValues = new HashMap<String, Double>();
	String name;
	
	public GroupData() {
	}
	
	public double get(int index) {
		return data.get(index);
	}
	
	public GroupData(String name) {
		setName(name);
	}
	
	public void setName(String variableName) {
		name = variableName;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Double> getData(){
		return data;
	}
	
	public void addData(double d) {
		data.add(d);
	}
	
	public int size() {
		return data.size();
	}
	
	public double getSum() {
		double total = 0;
		for (double d: data) {
			total = total + d;
		}
		return total;
	}
	
	public double getMean() {
		return getSum()/size();
	}
	
	public double getVariance() {
		double group1TotalVar = 0;
		double mean = getMean();
		for (double d: data) {
			group1TotalVar = group1TotalVar + (Math.pow((d - mean), 2));
		}
		return group1TotalVar / (size() - 1);
	}
	
	public void tukeyAddSignificantGroupName(String groupName) {
		tukeySignificantGroupNames.add(groupName.toLowerCase());
	}
	
	public boolean tukeySignificantGroupNameContains (String groupName) {
		if (tukeySignificantGroupNames.contains(groupName.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	public String tukeyGetSignificantGroupName(int index) {
		return tukeySignificantGroupNames.get(index);
	}
	
	public void tukeyAddSignificantGroupNameWithPValue(String groupName, double pValue) {
		tukeySignificantGroupNamesWithPValues.put(groupName, pValue);
	}
	
	public double tukeyGetPValue(String groupNameKey) {
		return tukeySignificantGroupNamesWithPValues.get(groupNameKey);
	}
	
	public int tukeyGetSignificantGroupSize() {
		return tukeySignificantGroupNames.size();
	}
	
	public List<String> tukeyTEMP(){
		return tukeySignificantGroupNames;
	}

}
