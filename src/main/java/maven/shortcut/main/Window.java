package maven.shortcut.main;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import maven.shortcut.backend.password.PasswordCheck;



public class Window {
	
	public static JLayeredPane jlp = new JLayeredPane();
	public static final JFrame window = new JFrame();
	
	public void windowInit() {
		
		
		
		window.setPreferredSize(window.getMaximumSize());
		window.setTitle("Shortcut");
		window.setResizable(true);
		consoleKeyReset(window);
		
		window.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	
		    	PasswordCheck cleaner = new PasswordCheck();
		    	cleaner.fileClean();
		    	System.exit(0);
		    }
		});
		
		window.addComponentListener(new ComponentListener() {

			public void componentResized(ComponentEvent e) {
				
				updatePanelOnResize();
	            
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
				
			}
			
		});
		
		
		
		window.setLayeredPane(jlp);
		
		window.pack();
		window.setVisible(true);
		
	}
	
	
	public void consoleKeyReset(JFrame window) {
		window.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_BACK_QUOTE) {
					
		        	System.out.println("Back Quote Key pressed.");
		        	
		        	//instantiate consoleLauncher
		        	
		        	/*
		        	
		        	//checks if console is currently open 
		        	
		        	if (launcher.returnConsoleOpen() == false) {
		        		System.out.println("Console Initialising.");
		        		launcher.consoleInit();
		        	} else {
		        		System.out.println("Console already open.");
		        		e.consume();
		        	}
		        	*/
		        } 
			}

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_BACK_QUOTE) {
					//do stuff
		        } 
			}
			
			public void keyReleased(KeyEvent e) {
			}
		});
	}
	
	public JLayeredPane returnWindowJLP() {
		return jlp;
	}
	
	public void updatePanelOnResize() {
		double width = window.getWidth();
	    double height = window.getHeight();
	    int layers = jlp.getComponentCount();
	   
	    for (int i = 0; i < layers; i++) {
	    	
	    	jlp.getComponent(i).setBounds(0, 0, (int) width, (int) height - 28);
	    }
	}
	

}
