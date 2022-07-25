package maven.shortcut.frontend.scrollpanepanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import maven.shortcut.frontend.app.Homepage;

public class StatisticsErrorsPanel {
	
	static JPanel statsErrorsPanel;
	public static JLabel errorText;
	
	public void init() {
		
		statsErrorsPanel = new JPanel();
		statsErrorsPanel.setLayout(new BorderLayout());
		statsErrorsPanel.setBackground(new Color(100, 0, 0));
		statsErrorsPanel.setPreferredSize(Homepage.jsp.getSize());
		statsErrorsPanel.setVisible(true);
		
		JButton okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Homepage.jsp.setViewportView(PatchNotes.patchNotesText);
			}
		});
		errorText = new JLabel ("Error with Statistical Test Operation. Your format/test is incorrect.", SwingConstants.CENTER);
		
		statsErrorsPanel.add(errorText, BorderLayout.CENTER);
		statsErrorsPanel.add(okB, BorderLayout.SOUTH);
		
	}
	
	public void enterStatsErrorPanel(String errorDesc) {
		errorText.setText(errorDesc);
		Homepage.jsp.setViewportView(statsErrorsPanel);
	}

}
