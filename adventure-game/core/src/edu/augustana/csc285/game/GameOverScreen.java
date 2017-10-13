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

public class GameOverScreen implements Screen {

	private final AdventureGame game;
	
	private Table table;
	private TextButton startButton;
	private TextButton aboutButton;
	private TextButton quitButton;
	private TextButton backButton;
	private Label introText;
	private Sprite logo;
	private Sprite swansonLogo;
	
	public GameOverScreen(final AdventureGame game) {
		this.game = game; 
		
		initializeMain();		
		
		Gdx.input.setInputProcessor(game.stage);
	}
	
	private void initializeTable() {
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.center|Align.top);
		
		table.setPosition(0, Gdx.graphics.getHeight());
	}

	private void initializeMain() {
		
		initializeTable();
		
		startButton = new TextButton("Restart Game", game.skin);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new SlideScreen(game));
			}
		});
		
		quitButton = new TextButton("Quit Game", game.skin);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		String intro = "Thank you for playing the game.\n"
				+ "Game designed by Dr. Forrest Stonedahl's Software Development CSC 285 students and "
				+ "Dr. Brian Leech's history students Abigail Buchanan, Brooks Fielder, and Katie Laschanzky "
				+ "for the for the Swenson Swedish Immigration Research Center at Augustana College in Rock Island, "
				+ "Illinois, 2017.\n\nGame by Team Curlew: Jack Carnell, Steve Jia, Minh Ta, and Maxwell McComb";
		
		introText = new Label(intro, game.skin);
		introText.setWrap(true);
		introText.setWidth(800);
		introText.setAlignment(Align.center|Align.top);
		
		table.padTop(300);
		table.add(introText).width(800f);
		table.row();
		table.add(startButton).padTop(30).width(300);
		table.row();
		table.add(aboutButton).padTop(10).width(300);
		table.row();
		table.add(quitButton).padTop(10).width(300);
		game.stage.addActor(table);
		
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

	
