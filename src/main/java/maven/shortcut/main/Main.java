package maven.shortcut.main;

import maven.shortcut.backend.init.AppInit;
import maven.shortcut.frontend.startup.Launcher;

public class Main {
	
	public static void main(String[] args) {
		
		Window launcher = new Window();
		launcher.windowInit();
		
		AppInit loader = new AppInit();
		loader.initApp();
		
		
		Launcher start = new Launcher();
		start.enterLauncher();
		
	}

}
