package maven.shortcut.backend.init;

import maven.shortcut.backend.homepage.StatButton;
import maven.shortcut.frontend.app.Homepage;
import maven.shortcut.frontend.scrollpanepanels.ParsingTypeSelection;
import maven.shortcut.frontend.scrollpanepanels.PatchNotes;
import maven.shortcut.frontend.scrollpanepanels.AnalysisPage;
import maven.shortcut.frontend.scrollpanepanels.AnovaToTukeyPanel;
import maven.shortcut.frontend.scrollpanepanels.StatisticsErrorsPanel;
import maven.shortcut.frontend.startup.Launcher;
import maven.shortcut.frontend.startup.PasswordPanel;

public class AppInit {
	
	//Pre launcher app pages init
	
	public void initApp() {
		//Ensure app folder and all required files/documents downloaded
		AppFolder folderInit = new AppFolder();
		folderInit.appFolderInit();
		System.out.println("App Folder and Document Check Complete.");
		
		//launch all page inits
		Launcher launcherLoader = new Launcher();
		launcherLoader.initialisation();
		
		PasswordPanel passwordPanelLoader = new PasswordPanel();
		passwordPanelLoader.passwordCheckInit();
		
		Homepage homepageLoader = new Homepage();
		homepageLoader.homepageInit();
		
		StatButton statisticalTestsPageLoader = new StatButton();
		statisticalTestsPageLoader.statisticalTestsInit();
		
		ParsingTypeSelection parsingChoicePanelLoader = new ParsingTypeSelection();
		parsingChoicePanelLoader.parsingChoicePanelInit();
		
		StatisticsErrorsPanel statsErrorPageLoader = new StatisticsErrorsPanel();
		statsErrorPageLoader.init();
		
		AnalysisPage resultsPanelLoader = new AnalysisPage();
		resultsPanelLoader.postAnalysisPanelInit();
		
		PatchNotes patchNotesLoader = new PatchNotes();
		patchNotesLoader.patchNotesInit();
		
		AnovaToTukeyPanel anovaToTukeyPanelLoader = new AnovaToTukeyPanel();
		anovaToTukeyPanelLoader.init();
		
		System.out.println("All pages successfully intialised.");
	}

}
