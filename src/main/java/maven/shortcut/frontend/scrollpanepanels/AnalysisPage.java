package maven.shortcut.frontend.scrollpanepanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import maven.shortcut.backend.statistics.Analysis;
import maven.shortcut.backend.utils.ParseData;
import maven.shortcut.frontend.app.Homepage;
import maven.shortcut.main.Window;

public class AnalysisPage {
	
	public static JPanel results;
	static JTextArea tempText;
	
	public void postAnalysisPanelInit() {
		
		results = new JPanel();
		results.setOpaque(true);
		results.setBackground(new Color(25,25,25));
		results.setLayout(new GridBagLayout());
		results.setVisible(true);
		
		JLabel tempLabel = new JLabel("Analysis:");
		tempLabel.setForeground(Color.white);
		
		tempText = new JTextArea("Results go here!");
		tempText.setWrapStyleWord(true);
		tempText.setLineWrap(true);
		tempText.setPreferredSize(new Dimension(Window.window.getWidth()/2, Window.window.getHeight()/5));
		tempText.setForeground(Color.LIGHT_GRAY);
		tempText.setBackground(Color.black);
		tempText.setOpaque(false);
		tempText.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ParseData data = new ParseData();
				data.getParsedData().clearData();
				Homepage.jsp.setViewportView(PatchNotes.patchNotesText);	
			}
		});
		
		
		JButton regenerateText = new JButton("Regenerate Text");
		regenerateText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempText.setText(Analysis.analysisText.toString());
			}
		});
		
		JButton copyButton = new JButton("Copy to Clipboard");
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Analysis getAnalysis = new Analysis();
				StringSelection selection = new StringSelection(getAnalysis.getAnalysisText().toString());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, null);
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,10,10);
		results.add(tempLabel, c);
		
		c.gridx = 1;
		c.gridwidth = 2;
		results.add(tempText, c);
		
		c.gridwidth = 1;
		c.gridy = 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		results.add(copyButton, c);
		
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		results.add(regenerateText, c);
		
		c.gridx = 2;
		c.insets = new Insets(10,10,10,0);
		results.add(closeButton, c);
		
	}

	
	public void enterPostAnalysis() {
		tempText.setText(Analysis.analysisText.toString());
		Homepage.jsp.setViewportView(results);
	}
}
