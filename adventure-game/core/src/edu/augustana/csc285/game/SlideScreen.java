
package edu.augustana.csc285.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.Player;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	
	private AdventureGame game;
	private GameData mainGameData;
	private Slide currentSlide;
	private Player player;
	
	private Table table;
	private Table itemTable;
	private Label title;
	private Label gameText;
	private TextButton inventoryButton;
	private ArrayList<TextButton> buttons;
	private ScrollPane scrollPane;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		
		//mainGameData = GameData.fromJSONFile(Gdx.files.internal("assets/GameData/SampleGame.json"));
		mainGameData = GameData.fromJSONFile(Gdx.files.internal("assets/GameData/SwedishImmigrant.json").file());
		player = mainGameData.getPlayer();
		
		mainGameData.setCurrentSlideIndex(11);
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
		currentSlide = mainGameData.getSlide(mainGameData.getCurrentSlideIndex());
		
		if (currentSlide.getSlideType() != GameData.INVENTORY_SLIDE) {
			inventoryButton = new TextButton("Inventory", game.skin);
			inventoryButton.setPosition(Gdx.graphics.getWidth() - inventoryButton.getWidth() - 20,
					Gdx.graphics.getHeight() - inventoryButton.getHeight() - 20);
			inventoryButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// set the destination of the back button of inventory to
					// the last slide
					mainGameData.getInventorySlide().getActionChoicesAt(0)
							.setDestinationSlideIndex(mainGameData.getCurrentSlideIndex());
					mainGameData.setCurrentSlideIndex(mainGameData.getInventorySlideIndex());
					initialize();
				}
			});
			game.stage.addActor(inventoryButton);
		}
		
		// initialize slide contents
		createTitle();
		if (currentSlide.getSlideType() == GameData.INVENTORY_SLIDE)
			createItemTable();
		else
			createGameText();
		
		createChoiceButtons();
		createTable();
		
		// TODO: delete this afterwards, print out for debugging
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
			newButton.getLabel().setWrap(true);
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
	
	private void createGameText() {
		gameText = new Label(currentSlide.getGameText(), game.skin);
		gameText.setWrap(true);
		
		// for normal slide
		int gameTextWidth = 280;
		
		if (currentSlide.getSlideType() == GameData.HISTORICAL_POP_UP 
				|| currentSlide.getSlideType() == GameData.INVENTORY_SLIDE
				|| currentSlide.getSlideType() == GameData.MANY_BUTTONS_SLIDE)
			gameTextWidth = 600;
		
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.topLeft);
		
		createScrollPane(gameTextWidth);
	
	}
	
	private void createItemTable() {
		itemTable = new Table();
		itemTable.setWidth(550);
		itemTable.align(Align.topLeft);
		
		Set set = player.getInventory().entrySet();
		Iterator it = set.iterator();
		
		while(it.hasNext()) {
			Map.Entry me = (Map.Entry)it.next();
			if ((int)me.getValue() != 0) {
				Image itemImage = new Image(new Texture(Gdx.files.internal("assets/art/icons/" + me.getKey() + ".png")));
				Label item = new Label((int)me.getValue() + "x " + (String)me.getKey(), game.skin);
				item.setAlignment(Align.topLeft);
				itemTable.add(itemImage).size(80, 80);
				itemTable.add(item).align(Align.left);
				itemTable.row().padTop(20);
			}
		}
		
		createScrollPane(550);
	}
	
	private void createScrollPane(int gameTextWidth) {
		if(currentSlide.getSlideType() == GameData.INVENTORY_SLIDE)
			scrollPane = new ScrollPane(itemTable, game.skin);
		else
			scrollPane = new ScrollPane(gameText, game.skin);
		
		int gameTextHeight = 300;
		if (currentSlide.getSlideType() == GameData.NORMAL_SLIDE)
			gameTextHeight = 220;
		else if (currentSlide.getSlideType() == GameData.MANY_BUTTONS_SLIDE)
			gameTextHeight = 170;
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	}
	
	private void createTitle() {
		title = new Label(currentSlide.getTitle(), game.skin, "title");
		if (currentSlide.getSlideType() == GameData.LETTER_SLIDE
				|| currentSlide.getSlideType() == GameData.NORMAL_SLIDE)
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
		
		for (int i = 0; i < currentSlide.getActionChoices().size(); i++) {
			table.add(buttons.get(i)).width(260).padTop(5);
			table.row();
		}
		
		int tableHeight = 350;
		if (currentSlide.getSlideType() == GameData.NORMAL_SLIDE)
			tableHeight = 250;
		else if (currentSlide.getSlideType() == GameData.MANY_BUTTONS_SLIDE)
			tableHeight = 200;
		table.padTop(tableHeight + title.getHeight());
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

