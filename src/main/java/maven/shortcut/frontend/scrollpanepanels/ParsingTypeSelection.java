package maven.shortcut.frontend.scrollpanepanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import maven.shortcut.backend.homepage.StatButton;
import maven.shortcut.backend.statistics.Redirectory;
import maven.shortcut.frontend.app.Homepage;
import maven.shortcut.main.Window;

public class ParsingTypeSelection {
	
	public static JPanel parsingChoicePanel;
	boolean isFast;
	boolean optionChosen = false;
	
	public void parsingChoicePanelInit() {
		
		parsingChoicePanel = new JPanel();
		parsingChoicePanel.setOpaque(true);
		parsingChoicePanel.setBackground(new Color(30,30,30));
		parsingChoicePanel.setLayout(new GridBagLayout());
		parsingChoicePanel.setVisible(true);
		
		
		JLabel title = new JLabel("Select Parsing Style");
		title.setBorder(BorderFactory.createEmptyBorder(/* top */25, /* left */ 0, /* bottom */ 0, /* right */ 0));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setForeground(Color.white);
		title.setFont(new Font("Helvetica", Font.ROMAN_BASELINE, 40));
		
		JLabel pngFast = new JLabel();
		try {
			Image img = ImageIO.read(getClass().getResource("/misc/Shortcut - FastParse.png"));
			ImageIcon scaledImageIcon = new ImageIcon(img.getScaledInstance(Window.window.getWidth()/4, (int) (Window.window.getHeight()/3), Image.SCALE_DEFAULT));
			pngFast.setIcon(scaledImageIcon);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JLabel pngCustom = new JLabel();
		try {
			Image img1 = ImageIO.read(getClass().getResource("/misc/Shortcut - CustomParse.png"));
			ImageIcon scaledImageIcon = new ImageIcon(img1.getScaledInstance(Window.window.getWidth()/4, (int) (Window.window.getHeight()/3), Image.SCALE_DEFAULT));
			pngCustom.setIcon(scaledImageIcon);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JRadioButton fast = new JRadioButton("Fast");
		fast.setForeground(Color.white);
		fast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFast = true;
				optionChosen = true;
			}
		});
		
		JRadioButton custom = new JRadioButton("Custom");
		custom.setForeground(Color.white);
		custom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFast = false;
				optionChosen = true;
			}
		});
		
		ButtonGroup bg = new ButtonGroup();    
		bg.add(fast);
		bg.add(custom);
		
		JButton continueB = new JButton("Continue");
		continueB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (optionChosen) {
					if (isFast){
						Redirectory.isFast = true;
					} else if (isFast != true) {
						Redirectory.isFast = false;
					} else {
						//big crash || failure
						System.out.println("Null Parsing Type Selected (isFast failure).");
						System.exit(0);
					
					}
					Redirectory processing = new Redirectory();
					processing.setPathway();
					//launches backend continuation
				}
			}
		});
		
		JButton backB = new JButton("Back");
		backB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StatButton navigateBack = new StatButton();
				navigateBack.enterStats();
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 1;
		parsingChoicePanel.add(title, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10,10,0,10);
		parsingChoicePanel.add(pngFast, gbc);
		
		gbc.gridx = 2;
		gbc.insets = new Insets(10,10,10,0);
		parsingChoicePanel.add(pngCustom, gbc);
		
		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.insets = new Insets(0,0,0,0);
		parsingChoicePanel.add(fast, gbc);
		
		gbc.gridx = 2;
		parsingChoicePanel.add(custom, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		parsingChoicePanel.add(continueB, gbc);
		
		gbc.gridx = 2;
		parsingChoicePanel.add(backB, gbc);
	}
	
	public void enterParsingChoice() {
		Homepage.jsp.setViewportView(parsingChoicePanel);
	}
	

}
