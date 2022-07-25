package maven.shortcut.frontend.scrollpanepanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import maven.shortcut.backend.statistics.Analysis;
import maven.shortcut.backend.statistics.processing.tukey.FastParseTukey;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.app.Homepage;

public class AnovaToTukeyPanel {
	
	static JPanel anovaTukeyPanel;
	
	public void init() {
		anovaTukeyPanel = new JPanel();
		anovaTukeyPanel.setOpaque(true);
		anovaTukeyPanel.setBackground(new Color(10, 80, 15));
		anovaTukeyPanel.setVisible(true);
		anovaTukeyPanel.setLayout(new BorderLayout());
	
		JLabel text = new JLabel("Signficant difference between groups detected. Would you like to view Anova results or continue to Tukey Test?", SwingConstants.CENTER);
		
		JPanel buttonGrid = new JPanel();
		buttonGrid.setVisible(true);
		buttonGrid.setOpaque(false);
		buttonGrid.setLayout(new GridLayout(1,2));
		
		JButton anovaResults = new JButton("Display Anova Results");
		anovaResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ParseData parseData = new ParseData();
				
				System.out.println("pValue: " + parseData.getParsedData().getPValue());
				
				System.out.println("Generating Analysis");
				new Analysis();
				
				AnalysisPage presentData = new AnalysisPage();
				presentData.enterPostAnalysis();
			}
		});
		
		JButton continueToTukey = new JButton("Continue to Tukey Analysis");
		continueToTukey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Tiddy Trace");
				new FastParseTukey(true);
			}
		});
		
		buttonGrid.add(anovaResults);
		buttonGrid.add(continueToTukey);
		
		anovaTukeyPanel.add(buttonGrid, BorderLayout.SOUTH);
		anovaTukeyPanel.add(text, BorderLayout.CENTER);
	}

	public void enterAnovaToTukeyPanel() {
		Homepage.jsp.setViewportView(anovaTukeyPanel);
	}
	
}
