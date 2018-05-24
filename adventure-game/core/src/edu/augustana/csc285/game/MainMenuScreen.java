package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen implements Screen {

	private final AdventureGame game;
	
	private Table table;
	private TextButton resumeButton;
	private TextButton startButton;
	private TextButton aboutButton;
//	private TextButton quitButton;
	private TextButton creditsButton;
	private TextButton backButton;
	private TextButton volumeButton;
	private Label introText;
	private Slider volumeSlider;
	private Dialog volumeDialog;
	
	public MainMenuScreen(final AdventureGame game) {
		this.game = game; 
		initializeMain();
	}
	
	private void initializeTable() {
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.center|Align.top);
		
		table.setPosition(0, Gdx.graphics.getHeight());
	}

	private void initializeMain() {
		
		initializeTable();
		
		resumeButton = new TextButton("Resume Game", game.skin);
		resumeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new SlideScreen(game));
			}
		});
		resumeButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		startButton = new TextButton("Take the journey", game.skin);
		if (game.data.gameStarted()) {
			startButton.setText("Restart Journey");
		}
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.restartGame();
				game.data.setGameStarted(true);
				game.stage.clear();
				game.setScreen(new SlideScreen(game));
			}
		});
		startButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		
		aboutButton = new TextButton("About", game.skin);
		aboutButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				initializeAbout();
			}
		}); 
		aboutButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		
		creditsButton = new TextButton("Credits", game.skin);
		creditsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new CreditsScreen(game));
			}
		}); 
		creditsButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
	
		volumeButton = new TextButton("Change Volume", game.skin);
		volumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				volumeDialog.setVisible(true);
			}
		});
		volumeButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		
		//Slider
		volumeSlider = new Slider(0f, 1f, 0.1f, false, game.skin);
		volumeSlider.setValue(game.bgMusic.getVolume());
		volumeSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				game.bgMusic.setVolume(volumeSlider.getValue());	
				return false;
			}
			
		});
		
		volumeDialog = new Dialog("", game.skin);
		volumeDialog.setVisible(false);
		Label volLabel = new Label("Volume: ", game.skin);
		volLabel.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		volumeDialog.add(volLabel);
		volumeDialog.add(volumeSlider).align(Align.left);
		TextButton okButton = new TextButton("OK", game.skin);
		okButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				volumeDialog.setVisible(false);
			}
		});
		volumeDialog.add(okButton);
		volumeDialog.setWidth(AdventureGame.percentWidth(25));
		volumeDialog.setHeight(AdventureGame.percentHeight(4));
		volumeDialog.setPosition(AdventureGame.percentWidth(69), AdventureGame.percentHeight(74));
		
//		quitButton = new TextButton("Quit Game", game.skin);
//		quitButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.exit();
//			}
//		});
		
		String intro = "You are a young Swedish immigrant to America in 1880. "
				+ "You have made the tough decision to leave your family and life in Sweden behind."
				+ " Will you survive and prosper in America?"
				+ "\n\nPlease note, this game is a work of historical fiction based on information found "
				+ "in the Swenson Center's collections and additional research done by Augustana College students.";
		
		introText = new Label(intro, game.skin);
		introText.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		introText.setWrap(true);
		introText.setWidth(AdventureGame.percentWidth(79));
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(AdventureGame.percentHeight(40));
		table.add(introText).padBottom(AdventureGame.percentHeight(2)).width(AdventureGame.percentWidth(79));
		table.row();
		if (game.data.gameStarted()) {
			table.add(resumeButton).padBottom(AdventureGame.percentHeight(1)).width(AdventureGame.percentWidth(24));
			table.row();
		}
		table.add(startButton).width(AdventureGame.percentWidth(24));
		table.row();
		table.add(aboutButton).padTop(AdventureGame.percentHeight(1)).width(AdventureGame.percentWidth(24));
		table.row();
		table.add(volumeButton).padTop(AdventureGame.percentHeight(1)).width(AdventureGame.percentWidth(24));
		table.row();
		table.add(volumeDialog);
		
//		table.row();
//		table.add(quitButton).padTop(10).width(300);

		drawBackgroundAndLogo();
		game.stage.addActor(table);
		
	}

	private void initializeAbout() {
		initializeTable();
		drawBackgroundAndLogo();
		backButton = new TextButton("Back", game.skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				initializeMain();
			}
		});
		backButton.getLabel().setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		
		String intro = "Game designed by Dr. Forrest Stonedahl's Software Development CSC 285 students and "
				+ "Dr. Brian Leech's history students Abigail Buchanan, Brooks Fielder, and Katie Laschanzky "
				+ "in collaboration with Lisa Huntsha and the staff of the Swenson Swedish Immigration Research "
				+ "Center at Augustana College in Rock Island, Illinois, 2017-2018 "
				+ "for the Swenson Swedish Immigration Research Center at Augustana College in Rock Island, "
				+ "Illinois, 2017.\n"
				+ "Images in the game are from the Swenson Center's library and archival collections.\n\n"
				+ "Game by Team Curlew: Jack Cannell, Steve Jia, Minh Ta, and Maxwell McComb";
		
		introText = new Label(intro, game.skin);
		introText.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.appFontSize + ".fnt")), Color.BLACK));
		introText.setWrap(true);
		introText.setWidth(AdventureGame.percentWidth(94));
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(AdventureGame.percentHeight(42));
		table.add(introText).width(AdventureGame.percentWidth(94));
		table.row();
		table.add(creditsButton).padTop(AdventureGame.percentHeight(2)).width(AdventureGame.percentWidth(24));
		table.row();
		table.add(backButton).padTop(AdventureGame.percentHeight(1)).width(AdventureGame.percentWidth(24));
		
		game.stage.addActor(table);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
		
	}
	
	private void drawBackgroundAndLogo() {
		Image bg = new Image(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		bg.setSize(AdventureGame.SCREEN_WIDTH, AdventureGame.SCREEN_HEIGHT);
		bg.setPosition(0, 0);
		game.stage.addActor(bg);
		game.stage.addActor(game.logo);
		game.stage.addActor(game.swansonLogo);
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		game.stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}

	
