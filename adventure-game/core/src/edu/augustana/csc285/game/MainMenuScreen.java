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

public class MainMenuScreen implements Screen {

	private final AdventureGame game;
	
	private Table table;
	private TextButton resumeButton;
	private TextButton startButton;
	private TextButton aboutButton;
//	private TextButton quitButton;
	private TextButton creditsButton;
	private TextButton backButton;
	private Label introText;
	private Sprite logo;
	private Sprite swansonLogo;
	
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
		
		startButton = new TextButton("Take the journey", game.skin);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.data.setGameStarted(true);
				game.stage.clear();
				game.restartGame();
				game.setScreen(new SlideScreen(game));
			}
		});
		
		aboutButton = new TextButton("About", game.skin);
		aboutButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				initializeAbout();
			}
		}); 
		
		creditsButton = new TextButton("Credits", game.skin);
		creditsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new CreditsScreen(game));
			}
		}); 
//		
//		quitButton = new TextButton("Quit Game", game.skin);
//		quitButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.exit();
//			}
//		});
		
		String intro = "You are a young Swedish immigrant to America in 1880.\n"
				+ "You have made the tough decision to leave your family and life in Sweden behind.\n"
				+ "Will you survive and prosper in America?";
		
		introText = new Label(intro, game.skin);
		introText.setWrap(true);
		introText.setWidth(800);
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(300);
		table.add(introText).padBottom(30).width(800f);
		table.row();
		if (game.data.isGameStarted()) {
			table.add(resumeButton).padBottom(10).width(300);
			table.row();
		}
		table.add(startButton).width(300);
		table.row();
		table.add(aboutButton).padTop(10).width(300);
		
//		table.row();
//		table.add(quitButton).padTop(10).width(300);
		
		game.stage.addActor(table);
		
		drawBackgroundAndLogo();
	}

	private void initializeAbout() {
		initializeTable();
		backButton = new TextButton("Back", game.skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				initializeMain();
			}
		});
		
		String intro = "Game designed by Dr. Forrest Stonedahl's Software Development CSC 285 students and "
				+ "Dr. Brian Leech's history students Abigail Buchanan, Brooks Fielder, and Katie Laschanzky "
				+ "for the for the Swenson Swedish Immigration Research Center at Augustana College in Rock Island, "
				+ "Illinois, 2017.\n\nGame by Team Curlew: Jack Cannell, Steve Jia, Minh Ta, and Maxwell McComb";
		
		introText = new Label(intro, game.skin);
		introText.setWrap(true);
		introText.setWidth(800);
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(300);
		table.add(introText).width(800f);
		table.row();
		table.add(creditsButton).padTop(30).width(300);
		table.row();
		table.add(backButton).padTop(10).width(300);
		
		drawBackgroundAndLogo();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.sprite.draw(game.batch);
		logo.draw(game.batch);
		swansonLogo.draw(game.batch);
		game.batch.end();
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();

		game.stage.addActor(table);
		
	}
	
	private void drawBackgroundAndLogo() {
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		game.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Texture logoTexture = new Texture(Gdx.files.internal("art/LogoStroked.png"));
		int logoWidth = 500;
		float logoHeight = (float) (logoTexture.getHeight() * logoWidth * 1.0 / logoTexture.getWidth());
		logo = new Sprite(logoTexture);
		logo.setPosition((Gdx.graphics.getWidth() - logoWidth) / 2, Gdx.graphics.getHeight() - logoHeight - 50);
		logo.setSize(logoWidth, logoHeight);
		
		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
		swansonLogo = new Sprite(swansonLogoTexture);
		swansonLogo.setPosition(40, 10);
		swansonLogo.setSize(logoWidth, (float) (swansonLogoTexture.getHeight() * logoWidth * 1.0 / swansonLogoTexture.getWidth()));
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

	
