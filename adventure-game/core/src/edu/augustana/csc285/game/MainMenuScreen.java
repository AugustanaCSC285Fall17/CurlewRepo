package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {

	private final AdventureGame game;
	
	private MainActor actor;

	public MainMenuScreen(final AdventureGame game) {
<<<<<<< HEAD
		this.game = game;
=======
		this.game = game; 
		
		// Initialize startButton
		TextButton startButton = new TextButton("Start Game" , game.skin, "default");
		startButton.setWidth(200);
		startButton.setHeight(60);
		
		Dialog dialog = new Dialog("SWEDISH IMMMIGRANT GAME", game.skin);
		
		//set up Listener
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.show(game.stage);
			}	
		});
		
		game.stage.addActor(startButton);
		
		
>>>>>>> d2f0b2f7bfc0f0e725192fabde7ffaea29a25b7f
		Gdx.input.setInputProcessor(game.stage);
		
		
		actor = new MainActor();
		game.stage.addActor(actor);
		game.stage.setKeyboardFocus(actor);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		game.font.draw(game.batch, "Welcome to Adventure!!! ", 100, 150);
//		game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
		
		
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

	
