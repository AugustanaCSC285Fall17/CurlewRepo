package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.augustana.csc285.game.datamodel.GameData;

public class AdventureGame extends Game {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 562;
	public static int textFontSize = 20;
	public static int appFontSize = 20;

	public GameData data;

	public Music bgMusic;
	public Stage stage;
	public Skin skin;
	public Image bgImg;
	public Image logo;
	public Image swansonLogo;
	
	public void create() {
		skin = new Skin(new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));
		skin.add("font", new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + appFontSize + ".fnt")), BitmapFont.class);
		skin.add("title", new BitmapFont(Gdx.files.internal("fonts/MyriadPro" + (appFontSize + 8) + ".fnt")), BitmapFont.class);
		skin.load(Gdx.files.internal("skins/uiskin.json"));
		stage = new Stage();
		data = GameData.fromJSON(Gdx.files.internal("data/data.json").readString("UTF-8"));
		bgImg = new Image();
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Triumphant_Return.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume((float) .1);
		bgMusic.play();
		Gdx.input.setInputProcessor(stage);

		Texture logoTexture = new Texture(Gdx.files.internal("art/LogoStroked.png"));
		float logoWidth = AdventureGame.percentWidth(40);
		float logoHeight = (float) (logoTexture.getHeight() * logoWidth * 1.0 / logoTexture.getWidth());
		logo = new Image(logoTexture);
		logo.setPosition((AdventureGame.SCREEN_WIDTH - logoWidth) / 2, AdventureGame.SCREEN_HEIGHT - logoHeight - AdventureGame.percentHeight(2));
		logo.setSize(logoWidth, logoHeight);
		
		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
		swansonLogo = new Image(swansonLogoTexture);
		swansonLogo.setPosition(AdventureGame.percentWidth(1), AdventureGame.percentHeight(1));
		swansonLogo.setSize(logoWidth, (float) (swansonLogoTexture.getHeight() * logoWidth * 1.0 / swansonLogoTexture.getWidth()));

		
		// TODO: delete this when done testing
//		this.setScreen(new GameOverScreen(this));
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void restartGame() {
		data = GameData.fromJSON(Gdx.files.internal("data/data.json").readString("UTF-8"));
		
	}

	public void render() {
//		Gdx.graphics.setWindowedMode(800, 450);
//        Gdx.gl.glViewport(0, 0, 800, 450);
//		stage.getViewport().update(640, 360);
		super.render(); //important!
	}
	
	public void dispose() {
		skin.dispose();
		stage.dispose();
	}

	public static float percentWidth(int percentage) {
		return SCREEN_WIDTH * percentage / 100f;
	}
	
	public static float percentHeight(int percentage) {
		return SCREEN_HEIGHT * percentage / 100f;
	}
	
}
