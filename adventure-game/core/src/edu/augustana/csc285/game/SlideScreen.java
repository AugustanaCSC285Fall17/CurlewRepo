
package edu.augustana.csc285.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	
	private AdventureGame game;
	private Slide curSlide;
	
	private Table table;
	private Label title;
	private Label gameText;

	private Button pauseButton;
	private Button inventoryButton;
	private Button muteButton;
	private ArrayList<TextButton> choiceButtons;
	private ScrollPane scrollPane;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
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
	
	/*
	 *  Initialize slide elements, including:
	 *  	- setting buttons: pause, inventory, mute
	 *  	- create the stage.
	 */
	
	private void initialize() {
		game.stage.clear();
		
		choiceButtons = new ArrayList<TextButton>();
		curSlide = game.data.getSlide(game.data.getCurrentSlideIndex());
		
		createFunctionButtons();

		// initialize slide contents
		createTitle();
		createGameText();
		
		createChoiceButtons();
		createTable();
	    
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(table);
		game.stage.addActor(scrollPane);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName())));
		game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		game.sprite.setSize(size, size);
	}

	public static final int BUTTON_WIDTH = 40;
	private void createFunctionButtons() {
		Image pauseImg = new Image(new Texture(Gdx.files.internal("art/icons/pauseSMALL.png")));
		pauseButton = new Button(game.skin);
		pauseButton.add(pauseImg);
		pauseButton.setWidth(BUTTON_WIDTH);
		pauseButton.setHeight(BUTTON_WIDTH);
		pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 10,
				Gdx.graphics.getHeight() - pauseButton.getHeight() - 10);
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new PauseScreen(game));
			}
		});
		game.stage.addActor(pauseButton);
		
		Image invenImg = new Image(new Texture(Gdx.files.internal("art/icons/inventorySMALL.png")));
		inventoryButton = new Button(game.skin);
		inventoryButton.add(invenImg);
		inventoryButton.setWidth(BUTTON_WIDTH);
		inventoryButton.setHeight(BUTTON_WIDTH);
		inventoryButton.setPosition(Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10,
				Gdx.graphics.getHeight() - inventoryButton.getHeight() - 50);
		inventoryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// set the destination of the back button of inventory to
				// the last slide
				game.stage.clear();
				game.setScreen(new InventoryScreen(game));
			}
		});
		game.stage.addActor(inventoryButton);
		
		Image muteImg = new Image(new Texture(Gdx.files.internal("art/icons/muteSMALL.png")));
		Image unmuteImg = new Image(new Texture(Gdx.files.internal("art/icons/unmuteSMALL.png")));
		muteButton = new Button(game.skin);
		muteButton.add(muteImg);
		if (!game.bgMusic.isPlaying()) {
			muteButton.removeActor(muteImg);
			muteButton.add(unmuteImg);
		}
		muteButton.setWidth(BUTTON_WIDTH);
		muteButton.setHeight(BUTTON_WIDTH);
		muteButton.setPosition(Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10,
				Gdx.graphics.getHeight() - inventoryButton.getHeight() - 90);
		muteButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toggleMusic(muteImg, unmuteImg);
			}
		});
		game.stage.addActor(muteButton);
	}
	
	private void toggleMusic(Image muteImg, Image unmuteImg) {
		if (game.bgMusic.isPlaying()) {
			game.bgMusic.pause();
			muteButton.removeActor(muteImg);
			muteButton.add(unmuteImg);
		} else {
			game.bgMusic.play();
			muteButton.removeActor(unmuteImg);
			muteButton.add(muteImg);
		}
	}

	
	private void createChoiceButtons() {
		List<ActionChoice> curChoices = game.data.getVisibleChoicesForCurrentSlide();
		for (ActionChoice curChoice : curChoices) {
			String curChoiceText = curChoice.getChoiceText();
			
			TextButton newButton = new TextButton(curChoiceText, game.skin);
			newButton.getLabel().setWrap(true);
			newButton.addListener(new ClickListener(){
					
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (curChoice.getDestinationSlideIndex() == -1) {
						game.stage.clear();
						game.setScreen(new MainMenuScreen(game));
					} else {
						game.data.attemptChoice(curChoice);
						initialize();
					}
				}
			});
			choiceButtons.add(newButton);
			
		}
	}
	
	private void createTitle() {
		title = new Label(curSlide.getTitle(), game.skin, "title");
		if (curSlide.getSlideType() == SlideType.LETTER
				|| curSlide.getSlideType() == SlideType.NORMAL) {
			title.setWrap(true);
		}
		title.setWidth(350);
		title.pack();
		title.setWidth(350);
		title.setPosition(40, Gdx.graphics.getHeight() - title.getHeight() - 20);
		title.setAlignment(Align.left);
	}
	
	private void createGameText() {
		gameText = new Label(curSlide.getGameText(), game.skin);
		gameText.setWrap(true);
		
		// for normal slide
		int gameTextWidth = 280;
		
		if (curSlide.getSlideType() == SlideType.HISTORICAL
				|| curSlide.getSlideType() == SlideType.MANY_BUTTONS) {
			gameTextWidth = 600;
		}
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.topLeft);
		
		createScrollPane(gameTextWidth);
	
	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.align(Align.topLeft);
		
		table.setPosition(0, game.stage.getHeight());

		table.padLeft(40);
		
		for (int i = 0; i < game.data.getVisibleChoicesForCurrentSlide().size(); i++) {
			table.add(choiceButtons.get(i)).width(260).padTop(5);
			table.row();
		}
		
		int tableHeight = 350;
		if (curSlide.getSlideType() == SlideType.NORMAL)
			tableHeight = 250;
		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
			tableHeight = 200;
		table.padTop(tableHeight + title.getHeight());
	}
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(gameText, game.skin);
		
		int gameTextHeight = 300;
		if (curSlide.getSlideType() == SlideType.NORMAL)
			gameTextHeight = 220;
		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
			gameTextHeight = 170;
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	    scrollPane.setFadeScrollBars(false);
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
	public void dispose () {
	}

}

