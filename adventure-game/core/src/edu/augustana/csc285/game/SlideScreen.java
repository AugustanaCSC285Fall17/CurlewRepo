package edu.augustana.csc285.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	private AdventureGame game;

	private Table table;
	private GameData mainGameData;
	
	private TextField gameText;
	private ArrayList<TextButton> buttons;
	
	private Slide currentSlide;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		
		mainGameData = GameData.fromJSONFile("GameData/SampleGame.json");
		
		initialize();
		
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
		game.stage.clear();
	}
	
	private void initialize() {
		
		game.stage.clear();
		
		buttons = new ArrayList<TextButton>();

		currentSlide = mainGameData.getSlide(mainGameData.getCurrentSlideIndex());
		
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			ActionChoice currentChoice = currentSlide.getActionChoicesAt(i);
			String currentChoiceText = currentChoice.getChoiceText();
			
			TextButton newButton = new TextButton(currentChoiceText, game.skin);
			
			newButton.addListener(new ClickListener(){
				
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("CLICKED " + currentChoice.toString());
					if (currentChoice.getDestinationSlideIndex() == -1)
						Gdx.app.exit();
					else
						mainGameData.setCurrentSlideIndex(currentChoice.getDestinationSlideIndex());
					
					System.out.println(currentSlide.toString());

					initialize();
				}
			});
			buttons.add(newButton);
			
		}
		
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.bottomLeft);
		
		table.setPosition(0, 0);

		table.padLeft(40);
		table.padBottom(60);

		
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			table.add(buttons.get(i)).width(300).padBottom(20);
			table.row();
		}
		
		game.stage.addActor(table);
		
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/" + currentSlide.getImageFileName())));
		game.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

}
