package edu.augustana.csc285.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SlideScreen implements Screen {
	private AdventureGame game;

	private Table table;

	//private HashMap<String, TextButton> buttons;
	private TextButton option1;
	private TextButton option2;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		
		//buttons = new HashMap<String, TextButton>();
		
		game.stage = new Stage(new ScreenViewport());
		
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.bottomLeft);
		
		table.setPosition(0, 0);
		
		option1 = new TextButton("Option 1", game.skin);
		option2 = new TextButton("Option 2", game.skin);
		
		table.padLeft(40);
		table.padBottom(60);
		table.add(option1).padBottom(20);
		table.row();
		table.add(option2);
		
		game.stage.addActor(table);
		
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/slide_000.png")));
		game.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Gdx.input.setInputProcessor(game.stage);
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.sprite.draw(game.batch);
		game.batch.end();
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose () {
		
	}

}
