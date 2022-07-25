package maven.shortcut.backend.statistics;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import maven.shortcut.backend.utils.CustomFileFilter;

public class ImportData {
	
	
	public File chooseFile() {
		
		JDialog dialog = new JDialog(); 
		
    	System.out.println("Choose file called");
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Import Data");
		jfc.setFileFilter(new CustomFileFilter());

		//command below opens the window
        int returnValue = jfc.showOpenDialog(dialog);
        System.out.println("Window opened");
        
       if (returnValue == JFileChooser.APPROVE_OPTION) {
        	System.out.println("File approved.");
           return jfc.getSelectedFile();
        }  else {
        	System.out.println("return value != approve option.");
        	return null;
        }
	}

}
