package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
<<<<<<< HEAD
=======
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
>>>>>>> d2f0b2f7bfc0f0e725192fabde7ffaea29a25b7f
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AdventureGame extends Game {
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 480;
	
	Stage stage;
<<<<<<< HEAD
	
	public void create() {
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
=======
	Skin skin;
	
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		
>>>>>>> d2f0b2f7bfc0f0e725192fabde7ffaea29a25b7f
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
