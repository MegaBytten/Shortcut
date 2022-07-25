package maven.shortcut.backend.homepage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import maven.shortcut.backend.statistics.Redirectory;
import maven.shortcut.backend.statistics.processing.criticals.PCriticalValue;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.app.Homepage;
import maven.shortcut.frontend.scrollpanepanels.PatchNotes;
import maven.shortcut.main.Window;

public class StatButton {
	
	public static JPanel statChoicesPanel;
	
	Window getter = new Window();
	
	public void statisticalTestsInit() {
		statChoicesPanelInit();
		
	}
	
	public void statChoicesPanelInit() {
		statChoicesPanel = new JPanel();
		statChoicesPanel.setOpaque(true);
		statChoicesPanel.setBackground(new Color(20,20,20));
		statChoicesPanel.setLayout(new BorderLayout());
		statChoicesPanel.setVisible(true);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setOpaque(false);
		

		
		JLabel title = new JLabel("Choose your stats test");
		title.setBorder(BorderFactory.createEmptyBorder(/* top */25, /* left */ 0, /* bottom */ 0, /* right */ 0));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setForeground(Color.LIGHT_GRAY);
		title.setFont(new Font("Helvetica", Font.ROMAN_BASELINE, 40));
		
		//THIS INSETS shifts the spacing between buttons
		Insets i = new Insets(10, 10, 10, 10);
		
		//Anca helped me write this code!!
		GridBagConstraints gbcButtons = new GridBagConstraints();
		gbcButtons.gridheight = 1;
		gbcButtons.gridwidth = 1;
		gbcButtons.fill = GridBagConstraints.HORIZONTAL;
		gbcButtons.insets = i;
		
		int x = 1;
		int y = 2;
		

		ArrayList<JButton> gridButtonsList = buttonsInit();
		
		for (JButton b: gridButtonsList) {
			b.setOpaque(false);
			b.setBorderPainted(false);
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);
			b.setForeground(Color.LIGHT_GRAY);
			//THIS SIZE changes the button size, how accurately over the word do you need to hover
			b.setPreferredSize(new Dimension((int) b.getMaximumSize().getWidth(),60));
			
			
			b.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					b.setForeground(Color.red);
				}
				
				public void mouseExited(MouseEvent evt) {
					b.setForeground(Color.LIGHT_GRAY);
				}	
			});
			
			
			if (gridButtonsList.size() != gridButtonsList.indexOf(b)+1) {
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Redirectory redirectory = new Redirectory();
						redirectory.hub(b);
					}
				});
			}
			
			
			
			if (y < 6) {
				//this is INSIDE of the last row
				if (x < 3) {
					//buttons 1-2
					gbcButtons.gridx = x;
					gbcButtons.gridy = y;
					
					buttonPanel.add(b, gbcButtons);
					x++;
				} else {
					//button 3
					gbcButtons.gridx = x;
					gbcButtons.gridy = y;
					
					buttonPanel.add(b, gbcButtons);
					x = 1;
					y++;
				}
				
			} else {
				//this is OUTSIDE of the last row
				//will.figure.out.what.to.do.with.this.later
			}
		}
		
		statChoicesPanel.add(title, BorderLayout.NORTH);
		statChoicesPanel.add(buttonPanel, BorderLayout.CENTER);
	}
	
	
	public ArrayList<JButton> buttonsInit() {
		
		ArrayList<JButton> gridButtonsList = new ArrayList<JButton>(); 

		JButton tTestEqualVarB = new JButton("Unpaired T-Test Equal Var");
		JButton tTestUnequalVarB = new JButton("Unpaired T-Test Unequal Var");
		JButton pairedTTestB = new JButton("Paired T-Test");
		JButton anovaB = new JButton("ANOVA");
		JButton tukeyTestB = new JButton("Tukey Test");
		JButton mannWhitnneyTestB = new JButton("Mann-Whitnney Test");
		JButton chiSquaredTestB = new JButton("Chi-Squared Test");
		JButton fishersExactTestB = new JButton("Fisher's Exact Test");
		JButton pearsonsCorrelationB = new JButton("Pearson's Correlation Coefficient");
		JButton spearmansrankCorrelationB = new JButton("Spearman's Rank Correlation Coefficient");
		JButton backB = new JButton("Back");
		
		
		
		//action listener section
		backB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				departStats();
			}
		});
		
		gridButtonsList.add(tTestEqualVarB);
		gridButtonsList.add(tTestUnequalVarB);
		gridButtonsList.add(pairedTTestB);
		gridButtonsList.add(anovaB);
		gridButtonsList.add(tukeyTestB);
		gridButtonsList.add(mannWhitnneyTestB);
		gridButtonsList.add(chiSquaredTestB);
		gridButtonsList.add(fishersExactTestB);
		gridButtonsList.add(pearsonsCorrelationB);
		gridButtonsList.add(spearmansrankCorrelationB);
		gridButtonsList.add(backB);
		//linear regression
		//confidence intervals?
		
		return gridButtonsList; 
	}
	
	
	@SuppressWarnings("static-access")
	public void departStats() {
		Homepage homepage = new Homepage();
		homepage.jsp.setViewportView(PatchNotes.patchNotesText);
		
		homepage.bStat.setForeground(homepage.ogColor);
		homepage.selectedButtonHoverEffect(homepage.bStat);
		homepage.bStat.removeMouseListener(homepage.selectedButtonHoverEffect);
		homepage.bStat.addMouseListener(homepage.unselectedButtonHoverEffect);
	}
	
	public void enterStats() {
		PCriticalValue.isTiny = false;
		ParseData data = new ParseData();
		data.getParsedData().clearData();
		Homepage.jsp.setViewportView(statChoicesPanel);
	}

}
