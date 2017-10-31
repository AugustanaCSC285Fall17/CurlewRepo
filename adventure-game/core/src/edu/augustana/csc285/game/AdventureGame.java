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
	
	//Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Si_la_Rigueur.mp3"));
	
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		data = GameData.fromJSON(Gdx.files.internal("data/TESTER.json").readString());
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Sonata_con_Allemanda_Courante_Sarabande_Gigue.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		
		
		Gdx.input.setInputProcessor(stage);
		
		// TODO: delete this when done testing
//		this.setScreen(new ShopScreen(this));
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void restartGame() {
		data = GameData.fromJSON(Gdx.files.internal("data/TESTER.json").readString());
		
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
