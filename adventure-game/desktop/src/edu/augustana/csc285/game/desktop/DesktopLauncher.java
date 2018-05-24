
package edu.augustana.csc285.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.augustana.csc285.game.AdventureGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Adventure Game";
		config.width = AdventureGame.SCREEN_WIDTH;
		config.height = AdventureGame.SCREEN_HEIGHT;
		new LwjglApplication(new AdventureGame(), config);
	}
}
