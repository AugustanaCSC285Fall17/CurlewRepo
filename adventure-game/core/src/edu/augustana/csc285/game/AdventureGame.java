package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	public Image bgImg;
	public Image logo;
	public Image swansonLogo;
	
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		data = GameData.fromJSON(Gdx.files.internal("data/TESTER.json").readString("UTF-8"));
		bgImg = new Image();
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Triumphant_Return.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume((float) .1);;
		bgMusic.play();
		Gdx.input.setInputProcessor(stage);
		

		Texture logoTexture = new Texture(Gdx.files.internal("art/LogoStroked.png"));
		int logoWidth = 500;
		float logoHeight = (float) (logoTexture.getHeight() * logoWidth * 1.0 / logoTexture.getWidth());
		logo = new Image(logoTexture);
		logo.setPosition((Gdx.graphics.getWidth() - logoWidth) / 2, Gdx.graphics.getHeight() - logoHeight - 50);
		logo.setSize(logoWidth, logoHeight);
		
		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
		swansonLogo = new Image(swansonLogoTexture);
		swansonLogo.setPosition(40, 10);
		swansonLogo.setSize(logoWidth, (float) (swansonLogoTexture.getHeight() * logoWidth * 1.0 / swansonLogoTexture.getWidth()));
		
		// TODO: delete this when done testing
		this.setScreen(new MainMenuScreen(this));
		
//		this.setScreen(new MainMenuScreen(this));
	}
	
	public void restartGame() {
		data = GameData.fromJSON(Gdx.files.internal("data/TESTER.json").readString("UTF-8"));
		
	}

	public void render() {
		
		super.render(); //important!
	}
	
	public void dispose() {
		skin.dispose();
		stage.dispose();
	}
	
}
