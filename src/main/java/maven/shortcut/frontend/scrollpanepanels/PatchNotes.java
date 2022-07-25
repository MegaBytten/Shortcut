package maven.shortcut.frontend.scrollpanepanels;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;

public class PatchNotes {
	
	public static JLabel patchNotesText;
	static String patchNotesFilePath;
	
	public void patchNotesInit() {
		
		patchNotesText = new JLabel();
		patchNotesText.setOpaque(true);
		patchNotesText.setBackground(new Color(20,20,20));		
		patchNotesText.setForeground(Color.red);
		
		List<String> patchNotes = new ArrayList<String>();
		
		URL url;
        URLConnection con;
        DataInputStream dis; 
        FileOutputStream fos; 
        byte[] fileData; 
        
        
        patchNotesFilePath = "/Users/" + System.getProperty("user.name") + "/ShortcutPatchNotes-0.0.1.txt";
        
        try {
	             url = new URL("https://drive.google.com/uc?export=download&id=1VX_gevW1HRJPGUf6HGw4us8c_c1wJjvf"); //File Location goes here
	             con = url.openConnection(); // open the url connection.
	             dis = new DataInputStream(con.getInputStream());
	             fileData = new byte[con.getContentLength()];
	             
	             for (int q = 0; q < fileData.length; q++) { 
	                 fileData[q] = dis.readByte();
	             }
            
	         dis.close(); // close the data input stream
	         fos = new FileOutputStream(new File(patchNotesFilePath)); //FILE Save Location goes here
	         fos.write(fileData);  // write out the file we want to save.
	         fos.close(); // close the output stream writer
	         
        }
        catch(Exception m) {
            System.out.println(m);
        }
		
        
        File f = new File(patchNotesFilePath);
        
    	try {
 			@SuppressWarnings("resource")
 			Scanner scanner = new Scanner(f);
 			while (scanner.hasNextLine()) {
 				patchNotes.add(scanner.nextLine() + "<br/>");
 			}
 			
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
         
         
 		f.delete();
    
        
       
		
		
		
		StringBuffer fullTextString = new StringBuffer();
		
		fullTextString.append("<html>");
		
		for (String s: patchNotes) {
			fullTextString.append(s);
		
		}
		fullTextString.append("</html>");
		
		
		patchNotesText.setText(fullTextString.toString());
	}
	

}
