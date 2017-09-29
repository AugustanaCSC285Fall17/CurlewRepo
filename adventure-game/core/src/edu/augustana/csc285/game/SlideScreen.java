package edu.augustana.csc285.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	
	private AdventureGame game;
	private Table table;
	private GameData mainGameData;
	private Label title;
	private Label gameText;
	private ArrayList<TextButton> buttons;
	private Slide currentSlide;
	private ScrollPane scrollPane;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		mainGameData = GameData.fromJSONFile("assets/GameData/SwedishImmigrant.json");
		mainGameData.setCurrentSlideIndex(mainGameData.getStartSlideIndex());
		
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
		
		// Initialize slide elements
		createTitle();
		createGameTextWithScrollPane();
		createChoiceButtons();
		createTable();
		
	    // print out title height for debugging
	    System.err.println(title.getHeight());
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(table);
		game.stage.addActor(scrollPane);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/" + currentSlide.getImageFileName())));
		game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		game.sprite.setSize(size, size);
	}
	
	private void createChoiceButtons() {
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			ActionChoice currentChoice = currentSlide.getActionChoicesAt(i);
			String currentChoiceText = currentChoice.getChoiceText();
			
			TextButton newButton = new TextButton(currentChoiceText, game.skin);
			
			newButton.addListener(new ClickListener(){
					
				@Override
				public void clicked(InputEvent event, float x, float y) {
					mainGameData.setCurrentSlideIndex(currentChoice.getDestinationSlideIndex());
					initialize();
				}
			});
			buttons.add(newButton);
			
		}
	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.align(Align.topLeft);
		
		table.setPosition(0, game.stage.getHeight());

		table.padLeft(40);
		
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			table.add(buttons.get(i)).width(260).padTop(5);
			table.row();
		}
		
		table.padTop(250 + title.getHeight());
	}
	
	private void createGameTextWithScrollPane() {
		gameText = new Label(currentSlide.getGameText(), game.skin);
		gameText.setWrap(true);
		
		int gameTextWidth = 0;
		if (currentSlide.getSlideType() == GameData.NORMAL_SLIDE)
			gameTextWidth = 280;
		else if (currentSlide.getSlideType() == GameData.HISTORICAL_POP_UP)
			gameTextWidth = 550;
		
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.topLeft);
		
	    scrollPane = new ScrollPane(gameText, game.skin);
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 250, gameTextWidth, 220);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	}
	
	private void createTitle() {
		title = new Label(currentSlide.getTitle(), game.skin, "title");
		title.setWrap(true);
		title.setWidth(350);
		title.pack();
		title.setWidth(350);
		title.setPosition(40, Gdx.graphics.getHeight() - title.getHeight() - 20);
		title.setAlignment(Align.left);
	}

}
