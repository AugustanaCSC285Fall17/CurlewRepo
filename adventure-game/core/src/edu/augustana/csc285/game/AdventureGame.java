package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AdventureGame extends Game {
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 480;
	
	Stage stage;
	
	public void create() {
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		
		super.render(); //important!
	}
	
	public void dispose() {
		stage.dispose();
	}
	
}
