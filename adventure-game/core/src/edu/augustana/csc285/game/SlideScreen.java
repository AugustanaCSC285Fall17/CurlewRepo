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

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.GameDataTester;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	private AdventureGame game;

	private Table table;
	private GameData mainGameData;
	private HashMap<String, TextButton> buttons;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		
		mainGameData = GameData.fromJSONFile("GameData/SampleGame.json");
		
		test(mainGameData);
		
		buttons = new HashMap<String, TextButton>();

		Slide currentSlide = mainGameData.getSlide(mainGameData.getCurrentSlide());
		
		for (int i = 0; i < mainGameData.getSlideListSize(); i++) {
			ActionChoice currentChoice = currentSlide.getActionChoicesAt(i);
			String currentChoiceText = currentChoice.getChoiceText();
			
			buttons.put(currentChoiceText, new TextButton(currentChoiceText, game.skin));
		}
		
		game.stage = new Stage(new ScreenViewport());
		
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.bottomLeft);
		
		table.setPosition(0, 0);

		table.padLeft(40);
		table.padBottom(60);

		
		for (int i = 0; i < mainGameData.getSlideListSize(); i++) {
			table.add(buttons.get(currentSlide.getActionChoicesAt(i).getChoiceText())).padBottom(20);
			table.row();
		}
		
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
	
	private void test(GameData gdRecreated) {
		System.out.println("Slide 0 has this image: ");
		System.out.println(gdRecreated.getSlide(0).getImageFileName());
		
		System.out.println(gdRecreated.getSlide(1).getActionChoices().get(1).getChoiceText());
		System.out.println(gdRecreated.getSlide(1).getActionChoices().get(1).getDestinationSlideIndex());
		gdRecreated.removeSlide(0);
		
		System.out.println(gdRecreated.getSlide(0).getActionChoices().get(1).getChoiceText());
		System.out.println(gdRecreated.getSlide(0).getActionChoices().get(1).getDestinationSlideIndex());
	}

}
