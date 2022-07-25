package maven.shortcut.frontend.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import maven.shortcut.frontend.app.Homepage;
import maven.shortcut.main.Window;


public class Launcher {
	
	public static JPanel backGroundPanel;
	public static JPanel signIn = new JPanel();
	
	Dimension macScreenDx = new Dimension (1440, 900);
	
	
	

	Window getter = new Window();
	
	public void initialisation() {
		backGroundPanel  = new JPanel();
		
		backGroundPanelInit();
		signInInit();
	}
	
	
	public void backGroundPanelInit() {
		backGroundPanel = new JPanel();
		backGroundPanel.setBackground(Color.black);
		backGroundPanel.setPreferredSize(macScreenDx);

		
		JLabel backgroundImage = new JLabel();
		ImageIcon img = new ImageIcon();
		
		try {
			img = new ImageIcon(ImageIO.read(getClass().getResource("/credits/Shortcut.png")));
		} catch (IOException e) {
			System.out.println("Error loading launcher image.");
			e.printStackTrace();
		}
		
		backgroundImage.setIcon(img);
		backgroundImage.setPreferredSize(macScreenDx);
					
		backGroundPanel.add(backgroundImage);
	}
	
	
	public void signInInit() {

		signIn.setOpaque(false);
		signIn.setLayout(new BorderLayout());
		signIn.setPreferredSize(macScreenDx);
		
		Dimension minGlue = new Dimension(0,0);
		
		JLabel glue = new JLabel();
		glue.setOpaque(false);
		glue.setMinimumSize(minGlue);
		JLabel glue1 = new JLabel();
		glue1.setOpaque(false);
		glue1.setMinimumSize(minGlue);
		JLabel glue2 = new JLabel();
		glue2.setOpaque(false);
		glue2.setMinimumSize(minGlue);
		JLabel glue3 = new JLabel();
		glue3.setOpaque(false);
		glue3.setMinimumSize(minGlue);
		
		
		JButton freeButton = new JButton("Free Version");
		freeButton.setForeground(Color.black);
		

		JButton signInButton = new JButton("Sign In");
		signInButton.setForeground(Color.black);
		
		
		freeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				departLauncher();
				
				Homepage freeLauncher = new Homepage();
				freeLauncher.enterHomepageLauncher(true);
				
			}
		});
		
		signInButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				signIn.setVisible(false);
				getter.returnWindowJLP().remove(signIn);
				
				PasswordPanel launchPasswordPanel = new PasswordPanel();
				launchPasswordPanel.enterPasswordPanel();
				
			}
		});
		
		
		
		signIn.add(glue,BorderLayout.NORTH);
		signIn.add(glue1,BorderLayout.WEST);
		signIn.add(glue2,BorderLayout.CENTER);
		signIn.add(glue3,BorderLayout.EAST);
		
		
		JPanel southFormat = new JPanel();
		southFormat.setOpaque(false);
		southFormat.setLayout(new GridLayout(2,1));
		southFormat.setMinimumSize(new Dimension(200, 80));
		
		southFormat.add(freeButton);
		southFormat.add(signInButton);
		
		signIn.add(southFormat, BorderLayout.SOUTH);
		
		
	}
	
	
	public void enterLauncher() {
		

		backGroundPanel.setVisible(true);
		signIn.setVisible(true);
		
		backGroundPanel.setBounds(0,0, getter.returnWindowJLP().getWidth(), getter.returnWindowJLP().getHeight());
		signIn.setBounds(0, 0, getter.returnWindowJLP().getWidth(), getter.returnWindowJLP().getHeight());
		
		getter.returnWindowJLP().add(backGroundPanel);
		getter.returnWindowJLP().setLayer(backGroundPanel, 0);
		getter.returnWindowJLP().add(signIn);
		getter.returnWindowJLP().setLayer(signIn, 1);
	}
	
	public void departLauncher() {
		
		backGroundPanel.setVisible(false);
		signIn.setVisible(false);
		
		getter.returnWindowJLP().remove(backGroundPanel);
		getter.returnWindowJLP().remove(signIn);
		
		
	}
}
