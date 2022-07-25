package maven.shortcut.backend.password;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import maven.shortcut.frontend.app.Homepage;
import maven.shortcut.frontend.startup.Launcher;
import maven.shortcut.frontend.startup.PasswordPanel;
import maven.shortcut.main.Window;

public class PasswordCheck {
	
	public static Boolean loadedMaster = false;
	static String masterPasswordJsonFilePath;
	static String masterPasswordJsonFileName;
	
	Window getter = new Window();

	
	public void checkPassword(String password) {
		
		System.out.println("password check called successfully.");
		
		masterJSONGetter();
		
		if (password.equals(returnMasterPassword())) {
			File f = new File(masterPasswordJsonFilePath);
			f.delete();
			
			PasswordPanel removePP = new PasswordPanel();
			removePP.departPasswordPanel();
			
			Launcher removeBackground = new Launcher();
			removeBackground.departLauncher();
			
			Homepage fullLauncher = new Homepage();
			fullLauncher.enterHomepageLauncher(false);
			
		} else {
			
			
			PasswordPanel wrongPassword = new PasswordPanel();
			wrongPassword.wrongPassword();
			File f = new File(masterPasswordJsonFilePath);
			f.delete();
			
		}
	}

	
	
	public void masterJSONGetter() {
		URL url;
        URLConnection con;
        DataInputStream dis; 
        FileOutputStream fos; 
        byte[] fileData; 
        
        masterPasswordJsonFileName = generateRandomFileName(8);
        masterPasswordJsonFilePath = "/Users/" + System.getProperty("user.name") + "/" + masterPasswordJsonFileName;
        
        try {
	             url = new URL("https://drive.google.com/uc?export=download&id=12EPmcAK0_u9K1ObCCnu5Rcol_puWrBoZ"); //File Location goes here
	             con = url.openConnection(); // open the url connection.
	             dis = new DataInputStream(con.getInputStream());
	             fileData = new byte[con.getContentLength()];
	             
	             for (int q = 0; q < fileData.length; q++) { 
	                 fileData[q] = dis.readByte();
	             }
            
	         dis.close(); // close the data input stream
	         fos = new FileOutputStream(new File(masterPasswordJsonFilePath)); //FILE Save Location goes here
	         fos.write(fileData);  // write out the file we want to save.
	         fos.close(); // close the output stream writer
	         
        }
        catch(Exception m) {
            System.out.println(m);
        }
        
        loadedMaster = true;
        
	}
	
	
	public String generateRandomFileName(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
          +"lmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}
	
	
	
	public String returnMasterPassword() {
		
		String masterPassword = null;
		JSONParser parser = new JSONParser();
		
		
		
			Object obj;
			try {
				obj = parser.parse(new FileReader(masterPasswordJsonFilePath));
				JSONObject jsonObj = (JSONObject) obj;
				masterPassword = (String) jsonObj.get("toulouse");
				
				
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
		
		return masterPassword;
	}
	
	
	
	public void fileClean() {
		
		if (loadedMaster == true) {
			Path p = Paths.get(masterPasswordJsonFilePath);
			if (Files.exists(p) == true) {
				File f = new File (masterPasswordJsonFilePath);
				f.delete();
			}
		}
	}
	
}
