package maven.shortcut.backend.statistics;

import java.io.File;

import javax.swing.JButton;

import maven.shortcut.backend.statistics.processing.Pathing;
import maven.shortcut.frontend.scrollpanepanels.ParsingTypeSelection;

public class Redirectory {
	
	public static File dataFile;
	public static boolean isFast;
	public static int testType;
	public static boolean isXlsx;
	static JButton selectedB;
	
	
	public void hub(JButton b) {
		
		//need to get the selected file, get the type of parsing, get the test, get file type and save them locally
		//further processes then can always come back and get details of the file
		selectedB = b;
		
		//Launches JFileChooser for user to select file and sets it to dataFile
		getData();
		
		//double checks if user cancelled, entered wrong file, or any other errors to stop processing
		if (dataFile == null) {

		} else {
			//if all checks cleared, type of parsing is selected via ParsingChoice Panel
			ParsingTypeSelection parsingChoicePanelLauncher = new ParsingTypeSelection();
			parsingChoicePanelLauncher.enterParsingChoice();
		}
	}
	
	
	public void getData() {
		ImportData getDataFile = new ImportData();
		dataFile = getDataFile.chooseFile();
	}
	
	
	public void setPathway() {
		//entered from the ParsingChoice Panel. Hence got the type of parsing
		
		String fileType = checkFileType();
		if (fileType.equalsIgnoreCase("csv") || fileType.equalsIgnoreCase("xls")) {
			isXlsx = false;
		} else if (fileType.equalsIgnoreCase("xlsx")) {
			isXlsx = true;
		}
		//set the file type
		
		
		testType = checkTest(selectedB);
		//get test type
		
		Pathing decideExactTest = new Pathing();
		decideExactTest.compass();
		//finally have all the info saved into the redirectory class! now to process it.
	}
	
	public String checkFileType() {
		String extension = null;
		char dot = '.';
		String fileName = dataFile.getName();
		int indexFile = fileName.lastIndexOf(dot);
		if (indexFile > 0 && indexFile < fileName.length()-1) {
			extension = fileName.substring(indexFile +1);
		} else {
			System.out.println("Selected File has no extension!");
		}
		//above functions gets and sets the file extension to a local var
		
		
		if (extension.equalsIgnoreCase("csv") || (extension.equalsIgnoreCase("xls"))) {
			//process file as a CSV or XLS
			return "csv";
		} else if (extension.equalsIgnoreCase("xlsx")) {
			//process file as an XLSX
			return "xlsx";
		} else {
			System.out.println("Error: Processed file is not CSV nor XLSX and has slipped through selection filters.");
			return null;
		}
	}
	
	public int checkTest(JButton b) {
		
		String testType = b.getText();
		
		CharSequence tTestEqual = " equal";
		CharSequence tTestUnequal = "unequal";
		CharSequence pairedT = "paired";
		CharSequence anova = "anova";
		CharSequence tukey = "tukey";
		CharSequence mannWhit = "mann";
		CharSequence chi = "chi";
		CharSequence fisher = "fisher";
		CharSequence pearson = "pearson";
		CharSequence spearman = "spearman";
		
		if (testType.toLowerCase().contains(tTestEqual)) {
			System.out.println("T-Test of Equal Variance");
			return 1;
		} else if (testType.toLowerCase().contains(tTestUnequal)) {
			System.out.println("T-Test of Unequal Variance");
			return 2;
		} else if (testType.toLowerCase().contains(pairedT)) {
			System.out.println("Paired");
			return 3;
		} else if (testType.toLowerCase().contains(anova)) {
			System.out.println("Anova");
			return 4;
		} else if (testType.toLowerCase().contains(tukey)) {
			System.out.println("Tukey");
			return 5;
		} else if (testType.toLowerCase().contains(mannWhit)) {
			System.out.println("Mann");
			return 6;
		} else if (testType.toLowerCase().contains(chi)) {
			System.out.println("Chi");
			return 7;
		} else if (testType.toLowerCase().contains(fisher)) {
			System.out.println("Fisher");
			return 8;
		} else if (testType.toLowerCase().contains(pearson)) {
			System.out.println("Pearson");
			return 9;
		} else if (testType.toLowerCase().contains(spearman)) {
			System.out.println("Spearman");
			return 10;
		} else {
			return 0;
		}
		
		
	}
	
	
	

}
