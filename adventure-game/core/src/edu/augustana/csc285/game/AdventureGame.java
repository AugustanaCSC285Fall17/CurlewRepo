package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AdventureGame extends Game {
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 480;
	
	Stage stage;
	Skin skin;
	
	SpriteBatch batch;
	Sprite sprite;
	
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		
		
		Gdx.input.setInputProcessor(stage);
		
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		
		super.render(); //important!
	}
	
	public void dispose() {
	}
	
}
