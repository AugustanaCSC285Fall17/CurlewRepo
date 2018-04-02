
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

import edu.augustana.csc285.game.datamodel.SlideType;

public class PauseScreen implements Screen{

	private final AdventureGame game;
	//private VolumePreference parent;
	
	private Table table;
	private TextButton resumeButton;
	private TextButton mainMenuButton;
//	private TextButton quitButton;
	private Label introText;
	private Sprite swansonLogo;
	private Sprite pauseLogo;
	
	
	public PauseScreen(final AdventureGame game) {
		this.game = game; 
		initializeMain();
	}

	private void initializeMain() {
		
		initializeTable();
		
		resumeButton = new TextButton("Resume Game", game.skin);
		resumeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				if (game.data.getSlide(game.data.getCurrentSlideIndex()).getSlideType() == SlideType.SHOP) {
					game.setScreen(new ShopScreen(game, game.data.getCurrentSlideIndex() - 1));
				} else {
					game.setScreen(new SlideScreen(game));
				}
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
		
//		quitButton = new TextButton("Quit Game", game.skin);
//		quitButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.exit();
//			}
//		});
		
		
		table.padTop(300);
		table.add(introText).width(600f);
		table.row();
		table.add(resumeButton).padTop(5).width(300);
		table.row();
		table.add(mainMenuButton).padTop(5).width(300);
//		table.row();
//		table.add(quitButton).padTop(5);
		game.stage.addActor(table);
		
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
		pauseLogo.draw(game.batch);
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
		swansonLogo.setSize(500, (float) (swansonLogoTexture.getHeight() * 500.0 / swansonLogoTexture.getWidth()));
		
		Texture logoTexture = new Texture(Gdx.files.internal("art/pausedBlueNoStroke.png"));
		int logoWidth = 300;
		float logoHeight = (float) (logoTexture.getHeight() * logoWidth * 1.0 / logoTexture.getWidth());
		pauseLogo = new Sprite(logoTexture);
		pauseLogo.setPosition((Gdx.graphics.getWidth() - logoWidth) / 2, Gdx.graphics.getHeight() - logoHeight - 100);
		pauseLogo.setSize(logoWidth, logoHeight);
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

	
