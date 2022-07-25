package maven.shortcut.frontend.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import maven.shortcut.backend.password.PasswordCheck;
import maven.shortcut.main.Window;

public class PasswordPanel {
	
	Window getter = new Window();
	
	public static JPanel passwordCheckPanel;
	
	static final JPasswordField passwordTF = new JPasswordField("Password");
	
	Dimension macScreenDx = new Dimension (1440, 900);
	
	public void passwordCheckInit() {
		
		passwordCheckPanel  = new JPanel();
		
		passwordCheckPanel = new JPanel();
		passwordCheckPanel.setOpaque(false);
		passwordCheckPanel.setLayout(new BorderLayout());
		passwordCheckPanel.setPreferredSize(macScreenDx);
		
		
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
		
		
		JButton enter = new JButton("Enter");
		enter.setForeground(Color.black);

		
		
		enter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String password = String.valueOf(passwordTF.getPassword());
				
				PasswordCheck passwordChecker = new PasswordCheck();
				passwordChecker.checkPassword(password);
				
			}
		});
		
		
		passwordTF.setOpaque(false);
		
		passwordCheckPanel.add(glue,BorderLayout.NORTH);
		passwordCheckPanel.add(glue1,BorderLayout.WEST);
		passwordCheckPanel.add(glue2,BorderLayout.CENTER);
		passwordCheckPanel.add(glue3,BorderLayout.EAST);
		
		
		JPanel southFormat = new JPanel();
		southFormat.setOpaque(false);
		southFormat.setLayout(new GridLayout(1,2));
		southFormat.setMinimumSize(new Dimension(200, 80));
		
		southFormat.add(passwordTF);
		southFormat.add(enter);
		
		passwordCheckPanel.add(southFormat, BorderLayout.SOUTH);
		
		
		
		
	}
	
	public void enterPasswordPanel() {
		passwordCheckPanel.setVisible(true);
		
		passwordCheckPanel.setBounds(0,0, getter.returnWindowJLP().getWidth(), getter.returnWindowJLP().getHeight());
		getter.returnWindowJLP().add(passwordCheckPanel);
		getter.returnWindowJLP().setLayer(passwordCheckPanel, 1);
	}
	
	
	public void departPasswordPanel() {
		passwordCheckPanel.setVisible(false);
		getter.returnWindowJLP().remove(passwordCheckPanel);
	}
	
	
	public void wrongPassword() {
		passwordTF.setText("");
		passwordTF.updateUI();
	}

}
