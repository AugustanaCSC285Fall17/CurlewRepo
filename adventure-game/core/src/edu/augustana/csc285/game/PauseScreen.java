package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


public class PauseScreen implements Screen {

	private final AdventureGame game;
	
	private Table table;
	private TextButton resumeButton;
	private TextButton settingsButton;
	private TextButton mainMenuButton;
	private TextButton quitButton;
	private TextButton backButton;
	private Label introText;
	private Sprite swansonLogo;
	
	private int curSlide;
	
	public PauseScreen(final AdventureGame game, int curSlide) {
		this.game = game; 
		this.curSlide = curSlide;
		initializeMain();		
		
		Gdx.input.setInputProcessor(game.stage);
	}

	private void initializeMain() {
		
		initializeTable();
		
		resumeButton = new TextButton("Resume", game.skin);
		resumeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new SlideScreen(game, curSlide));
			}
		});
		
		settingsButton = new TextButton("Settings", game.skin);
		settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				initializeSetting();
			}
		}); 
		
		mainMenuButton = new TextButton("Main Menu", game.skin);
		mainMenuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new MainMenuScreen(game));
			}
		}); 
		
		quitButton = new TextButton("Quit Game", game.skin);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		introText = new Label("Paused", game.skin, "title");
		introText.setWrap(true);
		introText.setWidth(600);
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(100);
		table.add(introText).width(600f);;
		table.row();
		table.add(resumeButton).padTop(5);
		table.row();
		table.add(settingsButton).padTop(5);
		table.row();
		table.add(mainMenuButton).padTop(5);
		table.row();
		table.add(quitButton).padTop(5);
		game.stage.addActor(table);
		
		drawBackground();
	}

	private void initializeSetting() {
		initializeTable();
		backButton = new TextButton("Back", game.skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				initializeMain();
			}
		});
		
		String text = "Settings goes here...\nSetting 1\nSetting 2\n Setting 3";
		
		introText = new Label(text, game.skin, "title");
		introText.setWrap(true);
		introText.setWidth(600);
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(100);
		table.add(introText).width(600f);;
		table.row();
		table.add(backButton).padTop(5);
		
		drawBackground();
	}

	
	private void initializeTable() {
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.center|Align.top);
		
		table.setPosition(0, Gdx.graphics.getHeight());
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.sprite.draw(game.batch);
		swansonLogo.draw(game.batch);
		game.batch.end();
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();

		game.stage.addActor(table);
		
	}
	
	private void drawBackground() {
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		game.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
		swansonLogo = new Sprite(swansonLogoTexture);
		swansonLogo.setPosition(40, 10);
		swansonLogo.setSize(400, (float) (swansonLogoTexture.getHeight() * 400.0 / swansonLogoTexture.getWidth()));
	}
	
	public int getCurSlide() {
		return curSlide;
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
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

	
