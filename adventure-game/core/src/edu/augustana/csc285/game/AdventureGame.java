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
	public static final int GAME_SCREEN_WIDTH = 1280;
	public static final int GAME_SCREEN_HEIGHT = 720;

	public GameData data;

	public Music bgMusic;
	public Stage stage;
	public Skin skin;
	
	public SpriteBatch batch;
	public Sprite sprite;
	
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		data = GameData.fromJSON(Gdx.files.internal("data/TESTER.json").readString("UTF-8"));
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Triumphant_Return.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		Gdx.input.setInputProcessor(stage);
		
		// TODO: delete this when done testing
//		this.setScreen(new CreditsScreen(this));
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void restartGame() {
		data = GameData.fromJSON(Gdx.files.internal("data/demo.json").readString("UTF-8"));
		
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
