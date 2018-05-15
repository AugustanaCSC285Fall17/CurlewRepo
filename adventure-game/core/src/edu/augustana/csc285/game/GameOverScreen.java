package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameOverScreen implements Screen {

	private final AdventureGame game;
	
	private Table table;
	private TextButton startButton;
	private TextButton creditsButton;
//	private TextButton quitButton;
	private TextButton mainMenuButton;
	private Label introText;
	private Image bgImg;
	private Image logo;
	private Image swansonLogo;
	
	public GameOverScreen(final AdventureGame game) {
		this.game = game; 
		drawBackgroundAndLogo();
		initializeMain();
	}

	private void initializeMain() {
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.center|Align.top);
		
		table.setPosition(0, Gdx.graphics.getHeight());
		
		mainMenuButton = new TextButton("Main Menu", game.skin);
		mainMenuButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.data.setGameStarted(false);
				game.setScreen(new MainMenuScreen(game));
			}
		});
		
		startButton = new TextButton("Play Again", game.skin);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.restartGame();
				game.setScreen(new SlideScreen(game));
			}
		});
		
		creditsButton = new TextButton("Credits", game.skin);
		creditsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.data.setGameStarted(false);
				game.setScreen(new CreditsScreen(game));
			}
		}); 
		
		
//		quitButton = new TextButton("Quit Game", game.skin);
//		quitButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.exit();
//			}
//		});

		String intro = "Thank you for playing the game.\n"
				+ "Please check out the Credits Screen below to see the awesome people who have worked hard to create this game. "
				+ "Did you get a bad ending? Play again!";
		
		introText = new Label(intro, game.skin);
		introText.setWrap(true);
		introText.setWidth(800);
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(200);
		table.add(introText).width(800f);
		table.row();
		table.add(startButton).padTop(30).width(300);
		table.row();
		table.add(mainMenuButton).padTop(10).width(300);
		table.row();
		table.add(creditsButton).padTop(10).width(300);
		
//		table.row();
//		table.add(quitButton).padTop(10).width(300);
		
		game.stage.addActor(table);
	}	
	
	private void drawBackgroundAndLogo() {
		bgImg = new Image(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		bgImg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.stage.addActor(bgImg);
		
		Texture logoTexture = new Texture(Gdx.files.internal("art/gameover.png"));
		int logoWidth = 500;
		float logoHeight = (float) (logoTexture.getHeight() * logoWidth * 1.0 / logoTexture.getWidth());
		logo = new Image(logoTexture);
		logo.setPosition((Gdx.graphics.getWidth() - logoWidth) / 2, Gdx.graphics.getHeight() - logoHeight - 100);
		logo.setSize(logoWidth, logoHeight);
		game.stage.addActor(logo);
		
		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
		swansonLogo = new Image(swansonLogoTexture);
		swansonLogo.setPosition(40, 10);
		swansonLogo.setSize(logoWidth, (float) (swansonLogoTexture.getHeight() * logoWidth * 1.0 / swansonLogoTexture.getWidth()));
		game.stage.addActor(swansonLogo);
	
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();

		game.stage.addActor(table);
		
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		game.stage.getViewport().update(width, height, true);
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

	
