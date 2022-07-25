package maven.shortcut.backend.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CustomFileFilter extends FileFilter{

	String csv = "csv";
	String excel = "xlsx";
	String excelOld = "xls";
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		if (extension(f).equalsIgnoreCase(csv) || extension(f).equalsIgnoreCase(excel) || extension(f).equalsIgnoreCase(excelOld)) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "CSV and XLSX";
	}
	
	public String extension(File f){
		char dot = '.';
		
		String fileName = f.getName();
		int indexFile = fileName.lastIndexOf(dot);
		
		if (indexFile > 0 && indexFile < fileName.length()-1) {
			return fileName.substring(indexFile +1);
		} else {
			return " ";
		}
	}
}
