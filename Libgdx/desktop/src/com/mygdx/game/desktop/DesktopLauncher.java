package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Settings.BreakoutSettings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = BreakoutSettings.SCREEN_WIDTH;
		config.height = BreakoutSettings.SCREEN_HEIGHT;
		
		new LwjglApplication(new BreakoutGame(), config);
	}
}
