package edu.augustana.csc285.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	public static final int NORMAL_SLIDE = 0;
	public static final int HISTORICAL_POP_UP = 1;
	
	private AdventureGame game;
	private Table table;
	private GameData mainGameData;
	private Label title;
	private Label gameText;
	private ArrayList<TextButton> buttons;
	private Slide currentSlide;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		
		mainGameData = GameData.fromJSONFile("assets/GameData/SwedishImmigrant.json");
		
		initialize();
		
		Gdx.input.setInputProcessor(game.stage);
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
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
	
	// Initialize slide elements
	private void initialize() {
		
		// Clear the stage before changing slide
		game.stage.clear();
		
		// Create the list of option buttons
		buttons = new ArrayList<TextButton>();
		
		// Get the current slide object to initialize buttons & text etc.
		currentSlide = mainGameData.getSlide(mainGameData.getCurrentSlideIndex());
		
		// Initialize title text
		title = new Label(currentSlide.getTitle(), game.skin, "title");
		title.setWrap(true);
		title.setWidth(350);
		title.pack();
		title.setWidth(350);
		title.setPosition(40, Gdx.graphics.getHeight() - 20 - title.getHeight());
		title.setAlignment(Align.topLeft);
		
		// Initialize game text
		gameText = new Label(currentSlide.getGameText(), game.skin);
		gameText.setWrap(true);
		
		int gameTextWidth = 280;
		if (currentSlide.getSlideType() == HISTORICAL_POP_UP)
			gameTextWidth = 550;
		gameText.setWidth(gameTextWidth);
		gameText.pack();
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.left);
		gameText.setPosition(50, Gdx.graphics.getHeight() - 30 - title.getHeight() - gameText.getHeight());
		
		// Loop & create buttons
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			ActionChoice currentChoice = currentSlide.getActionChoicesAt(i);
			String currentChoiceText = currentChoice.getChoiceText();
			
			TextButton newButton = new TextButton(currentChoiceText, game.skin);
			
			newButton.addListener(new ClickListener(){
				
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (currentChoice.getDestinationSlideIndex() == -1)
						Gdx.app.exit();
					else
						mainGameData.setCurrentSlideIndex(currentChoice.getDestinationSlideIndex());
					

					initialize();
				}
			});
			buttons.add(newButton);
			
		}
		
		// Initialize table & add buttons to table
		table = new Table();
		table.setWidth(game.stage.getWidth());
		table.align(Align.topLeft);
		
		table.setPosition(0, game.stage.getHeight());

		table.padLeft(40);

		
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			table.add(buttons.get(i)).width(280).padBottom(10);
			table.row();
		}
		
		table.padTop(60 + title.getHeight() + gameText.getHeight());

		// Add title to stage
		game.stage.addActor(title);
		
		// Add gameText to stage
		game.stage.addActor(gameText);
		
		// Add table to stage
		game.stage.addActor(table);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/" + currentSlide.getImageFileName())));
		game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		game.sprite.setSize(size, size);
	}

}
