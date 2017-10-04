package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import edu.augustana.csc285.game.datamodel.GameData;

public class AdventureGame extends Game {
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 480;

	public static GameData data;

	public Music bgMusic;
	Stage stage;
	Skin skin;
	
	SpriteBatch batch;
	Sprite sprite;
	
	//Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Si_la_Rigueur.mp3"));
	
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		data = GameData.fromJSON(Gdx.files.internal("assets/data/SwedishImmigrantv2.json").readString());
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Si_la_Rigueur.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		
		
		Gdx.input.setInputProcessor(stage);
		
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		
		super.render(); //important!
	}
	
	public void dispose() {
		skin.dispose();
		stage.dispose();
		batch.dispose();
	}
	
}
