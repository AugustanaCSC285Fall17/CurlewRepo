package edu.augustana.csc285.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import edu.augustana.csc285.game.AdventureGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(AdventureGame.GAME_SCREEN_WIDTH,
                										AdventureGame.GAME_SCREEN_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new AdventureGame();
        }
}