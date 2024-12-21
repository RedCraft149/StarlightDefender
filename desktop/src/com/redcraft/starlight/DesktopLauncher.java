package com.redcraft.starlight;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.redcraft.starlight.launcher.HomeScreen;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("StarlightDefender");
		config.setMaximized(true);
		config.setWindowIcon("icons/16.png","icons/32.png","icons/48.png");
		new Lwjgl3Application(new ApplicationHook(new HomeScreen()), config);
	}
}
