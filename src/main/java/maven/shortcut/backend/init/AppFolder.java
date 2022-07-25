package maven.shortcut.backend.init;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AppFolder {
	
	Path appFolder = Paths.get("/Users/" + System.getProperty("user.name") + "/Shortcut");
	
	public void appFolderInit() {
		
		if (!checkAppFolder()) {
			createDirectory();
		} 
		
		checkDownloads();
	}
	
	public boolean checkAppFolder() {
        if (java.nio.file.Files.exists(appFolder, LinkOption.NOFOLLOW_LINKS)) {
        	return true;
        } else {
        	return false;
        }
	}
	
	public void createDirectory() {
		try {        		
			java.nio.file.Files.createDirectories(appFolder);
		} catch (IOException e) {
			System.out.println("Error creating App Folder.");
			e.printStackTrace();
		}
	}
	
	public boolean checkDownloads() {
		File appDirectory = new File(appFolder.toString());
		String[] fileNames = appDirectory.list();
		
		//List of required files - hardcoded that user's list of files gets compared against
		List<String> requiredFiles = new ArrayList<String>();
		requiredFiles.add("ftables.xlsx");
		requiredFiles.add("ttables.xlsx");
		requiredFiles.add("qtables.xlsx");
		
		//Cycles through the requiredFiles, if it can find a match in the user's files (comparing file names), it removes it from the
		//hardcoded requiredFiles list
		if (fileNames.length != 0) {
			for (int i=0; i<fileNames.length; i++) {
				int requiredFilesSize = requiredFiles.size();
				
				if(requiredFilesSize == 0) {
					break;
				}
				
				for (int t=0; t<requiredFilesSize; t++) {
					
					if (requiredFiles.get(t)!=null) {
						if (fileNames[i].equalsIgnoreCase(requiredFiles.get(t))) {
							requiredFiles.remove(t);
						}
					}		
				}
			}
		}
		
		
		//All requiredFiles are crosschecked, all of the files found are copied over into a checkedFiles list
		//if checkedFIles != requiredFiles some are missing! Gotta go download the missing ones
		if (requiredFiles.size() != 0) {
			downloadRequiredFiles(requiredFiles);
		} else {
			System.out.println("All required files present.");
		}

		return true;
	}
	
	
	public void downloadRequiredFiles(List<String> requiredFiles) {
		System.out.println("Some files missing. Downloading required files: " + requiredFiles);
		
		for (String s: requiredFiles) {
			
			switch (s) {
			case "ftables.xlsx":
				downloadFile("TTables.xlsx", "https://drive.google.com/uc?export=download&id=1AUpwC8qvt5RWom6-hM0VB9FZDVmZNnPn");
				break;
			case "ttables.xlsx":
				downloadFile("FTables.xlsx", "https://drive.google.com/uc?export=download&id=1vtcKlOir7ms1n4SaJknlXtcVMyYn6PWQ");
				break;
			case "qtables.xlsx":
				downloadFile("QTables.xlsx", "https://drive.google.com/uc?export=download&id=170LypOw1G6kkd74BquMXlQ73odZ2brAD");
			}
				
		}
		
		
	}
	
	//link for all alpha values of the Q table:
	// https://www.real-statistics.com/statistics-tables/studentized-range-q-table/
	
	
	public void downloadFile(String filename, String URL) {
		URL url;
        URLConnection con;
        DataInputStream dis; 
        FileOutputStream fos; 
        byte[] fileData; 
        
        String fTableFilePath = "/Users/" + System.getProperty("user.name") + "/Shortcut/" + filename;
        
        try {
	             url = new URL(URL); //File Location goes here
	             con = url.openConnection(); // open the url connection.
	             dis = new DataInputStream(con.getInputStream());
	             fileData = new byte[con.getContentLength()];
	             
	             for (int q = 0; q < fileData.length; q++) { 
	                 fileData[q] = dis.readByte();
	             }
            
	         dis.close(); // close the data input stream
	         fos = new FileOutputStream(new File(fTableFilePath)); //FILE Save Location goes here
	         fos.write(fileData);  // write out the file we want to save.
	         fos.close(); // close the output stream writer
	         
        }
        catch(Exception m) {
            System.out.println(m);
        }
		
	}
	

}
