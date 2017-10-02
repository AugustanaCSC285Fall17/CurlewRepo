
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
import edu.augustana.csc285.game.datamodel.Effect;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.Player;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	
	private AdventureGame game;
	private GameData data = GameData.fromJSON(Gdx.files.internal("assets/GameData/SwedishImmigrantv2.json").toString());;
	private Slide curSlide;
	private Player player;
	
	private Table table;
	private Label title;
	private Label gameText;
	private TextButton inventoryButton;
	private TextButton pauseButton;
	private ArrayList<TextButton> buttons;
	private ScrollPane scrollPane;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		player = data.getPlayer();
		
		data.setCurrentSlideIndex(data.getStartSlideIndex());
		initialize();
		
		Gdx.input.setInputProcessor(game.stage);
	}
	
	// for reserving the right player data from inventory slide
	public SlideScreen(final AdventureGame game, int curSlide, Player player) {
		this.game = game;
		this.player = player;
		
		data.setCurrentSlideIndex(curSlide);
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
	
	// Initialize slide elements
	private void initialize() {
		game.stage.clear();
		
		buttons = new ArrayList<TextButton>();
		curSlide = data.getSlide(data.getCurrentSlideIndex());
		
		pauseButton = new TextButton("Pause", game.skin);
		pauseButton.setWidth(100);
		pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 20,
				Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new PauseScreen(game, data.getCurrentSlideIndex(), player));
			}
		});
		game.stage.addActor(pauseButton);
		
		inventoryButton = new TextButton("Inventory", game.skin);
		inventoryButton.setWidth(100);
		inventoryButton.setPosition(Gdx.graphics.getWidth() - inventoryButton.getWidth() - 20,
				Gdx.graphics.getHeight() - inventoryButton.getHeight() - 50);
		inventoryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// set the destination of the back button of inventory to
				// the last slide
				game.stage.clear();
				game.setScreen(new InventoryScreen(game, data.getCurrentSlideIndex(), player));
			}
		});
		game.stage.addActor(inventoryButton);

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

	
	private void createChoiceButtons() {
		for (int i = 0; i < curSlide.getActionChoices().size(); i++) {
			ActionChoice curChoice = curSlide.getActionChoicesAt(i);
			String curChoiceText = curChoice.getChoiceText();
			
			TextButton newButton = new TextButton(curChoiceText, game.skin);
			newButton.getLabel().setWrap(true);
			newButton.addListener(new ClickListener(){
					
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (!curChoice.getEffect().isEmpty()) {
						for (Effect effect: curChoice.getEffect()) {
							effect.applyEffect(player);
						}
					}
					data.setCurrentSlideIndex(curChoice.getDestinationSlideIndex());
					initialize();
				}
			});
			buttons.add(newButton);
			
		}
	}
	
	private void createGameText() {
		gameText = new Label(curSlide.getGameText(), game.skin);
		gameText.setWrap(true);
		
		// for normal slide
		int gameTextWidth = 280;
		
		if (curSlide.getSlideType() == GameData.HISTORICAL_POP_UP 
				|| curSlide.getSlideType() == GameData.MANY_BUTTONS_SLIDE)
			gameTextWidth = 600;
		
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.topLeft);
		
		createScrollPane(gameTextWidth);
	
	}
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(gameText, game.skin);
		
		int gameTextHeight = 300;
		if (curSlide.getSlideType() == GameData.NORMAL_SLIDE)
			gameTextHeight = 220;
		else if (curSlide.getSlideType() == GameData.MANY_BUTTONS_SLIDE)
			gameTextHeight = 170;
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	}
	
	private void createTitle() {
		title = new Label(curSlide.getTitle(), game.skin, "title");
		if (curSlide.getSlideType() == GameData.LETTER_SLIDE
				|| curSlide.getSlideType() == GameData.NORMAL_SLIDE)
			title.setWrap(true);
		title.setWidth(350);
		title.pack();
		title.setWidth(350);
		title.setPosition(40, Gdx.graphics.getHeight() - title.getHeight() - 20);
		title.setAlignment(Align.left);
	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.align(Align.topLeft);
		
		table.setPosition(0, game.stage.getHeight());

		table.padLeft(40);
		
		for (int i = 0; i < curSlide.getActionChoices().size(); i++) {
			table.add(buttons.get(i)).width(260).padTop(5);
			table.row();
		}
		
		int tableHeight = 350;
		if (curSlide.getSlideType() == GameData.NORMAL_SLIDE)
			tableHeight = 250;
		else if (curSlide.getSlideType() == GameData.MANY_BUTTONS_SLIDE)
			tableHeight = 200;
		table.padTop(tableHeight + title.getHeight());
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

