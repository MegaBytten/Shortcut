package maven.shortcut.frontend.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import maven.shortcut.backend.homepage.ChooseButton;
import maven.shortcut.backend.homepage.DiscordButton;
import maven.shortcut.backend.homepage.QuitButton;
import maven.shortcut.backend.homepage.StatButton;
import maven.shortcut.frontend.scrollpanepanels.PatchNotes;
import maven.shortcut.main.Window;

public class Homepage {
	
	public static JPanel homepage;
	public static boolean isFree;
	public static JScrollPane jsp;
	public static MouseAdapter unselectedButtonHoverEffect;
	public static MouseAdapter selectedButtonHoverEffect;
	public static JButton bStat;
	
	public Color ogColor = new Color(23, 196, 232);
	Window getter = new Window();
	
	public void homepageInit() {
		homepage = new JPanel();
		homepage.setOpaque(true);
		homepage.setBackground(Color.black);
		homepage.setLayout(new BorderLayout());
		
		jsp = new JScrollPane();
		jsp.setPreferredSize(new Dimension((Window.window.getWidth()/3)*2, (Window.window.getHeight()/2)));
		jsp.setOpaque(false);
		homepage.add(jsp, BorderLayout.CENTER);
		
		
		bannerInit();
		buttonsInit();
		glueInit();
		
	}
	
	
	public void bannerInit() {
		
		JLabel banner = new JLabel();
		ImageIcon img = new ImageIcon();
		
		
		try {
			img = new ImageIcon(ImageIO.read(getClass().getResource("/homepage/Shortcut Banner.png")));
		} catch (IOException e) {
			System.out.println("Error loading banner image.");
			e.printStackTrace();
		}
		
		banner.setIcon(img);
		banner.setPreferredSize(new Dimension(1440, 150));
		
		homepage.add(banner, BorderLayout.NORTH);
		}
	
	
	public void buttonsInit() {
		
		
		
		JPanel gridButtons = new JPanel();
		gridButtons.setOpaque(false);
		gridButtons.setVisible(true);
		gridButtons.setLayout(new GridLayout(4,1));
		
		ArrayList<JButton> gridButtonsList = new ArrayList<JButton>(); 

		bStat = new JButton("Statistical Tests");
		JButton bChoose = new JButton("Choose Test");
		JButton bDiscord = new JButton("Discord");
		JButton bQuit = new JButton("Quit");
		

		bStat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				bStat.setForeground(Color.red);
				unselectedButtonHoverEffect(bStat);
				bStat.removeMouseListener(unselectedButtonHoverEffect);
				selectedButtonHoverEffect(bStat);
				
				//departHomepage();
				StatButton stats = new StatButton();
				stats.enterStats();
				
			}
		});
		
		bChoose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChooseButton choose = new ChooseButton();
				choose.chooseData(isFree);
			}
		});
		
		bDiscord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				departHomepage();
				
				DiscordButton discord = new DiscordButton();
				discord.discord();
			}
		});
		
		bQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				QuitButton quit = new QuitButton();
				quit.quit();
			}
		});
		

		
		
		
		gridButtonsList.add(bStat);
		gridButtonsList.add(bChoose);
		gridButtonsList.add(bDiscord);
		gridButtonsList.add(bQuit);
		
		
		
		for (JButton jb : gridButtonsList) {
			jb.setOpaque(false);
			jb.setBorderPainted(false);
			jb.setContentAreaFilled(false);
			jb.setFocusPainted(false);
			//to make background invisible
			
			jb.setForeground(ogColor);
			jb.setBackground(Color.black);
			jb.setFont(new Font("Helvetica", Font.BOLD, 20));
			
			unselectedButtonHoverEffect(jb);
			gridButtons.add(jb);
		}
		
		

		homepage.add(gridButtons, BorderLayout.WEST);
	}
	
	
	
	
	
	public void glueInit() {
		

		JLabel glue = new JLabel();
		glue.setPreferredSize(new Dimension(122,0));
		glue.setOpaque(false);
		JLabel glue1 = new JLabel();
		glue1.setPreferredSize(new Dimension(0,100));
		glue1.setOpaque(false);

		homepage.add(glue, BorderLayout.EAST);
		homepage.add(glue1, BorderLayout.SOUTH);
		
	}
	
	public void unselectedButtonHoverEffect(JButton jb) {
		
		unselectedButtonHoverEffect = new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jb.setForeground(Color.white);
			}
			
			
			public void mouseExited(MouseEvent evt) {
				jb.setForeground(ogColor);
			}	
		};
		
		jb.addMouseListener(unselectedButtonHoverEffect);
	}
	
	
	public void selectedButtonHoverEffect(JButton jb) {
		selectedButtonHoverEffect = new MouseAdapter() {
			
			public void mouseEntered(MouseEvent evt) {
				jb.setForeground(Color.white);
			}
			public void mouseExited(MouseEvent evt) {
				jb.setForeground(Color.red);
			}	
			
		};
		
		jb.addMouseListener(selectedButtonHoverEffect);
	}
	
	public void departHomepage() {
		
		homepage.setVisible(false);
		getter.returnWindowJLP().remove(homepage);
		
	}
	
	public void enterHomepageLauncher(boolean version) {
		isFree = version;
		homepage.setVisible(true);
		homepage.setBounds(0, 0,getter.returnWindowJLP().getWidth(), getter.returnWindowJLP().getHeight());
		jsp.setViewportView(PatchNotes.patchNotesText);
		getter.returnWindowJLP().add(homepage);
		getter.returnWindowJLP().setLayer(homepage, 0);
	}
	
	public void enterHomepage() {
		homepage.setVisible(true);
		homepage.setBounds(0, 0,getter.returnWindowJLP().getWidth(), getter.returnWindowJLP().getHeight());
		jsp.setViewportView(PatchNotes.patchNotesText);
		getter.returnWindowJLP().add(homepage);
		getter.returnWindowJLP().setLayer(homepage, 0);
	}
	
	public void resetButtonMouseListener(JButton b, MouseAdapter e) {
		b.removeMouseListener(e);
		b.addMouseListener(e);
		
	}
	

}
