package edu.augustana.csc285.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.Slide;
import edu.augustana.csc285.game.datamodel.SlideType;

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
	private Slider volumeSlider;
	private Dialog volumeDialog;
	private Dialog rejectDialog;
	
	public SlideScreen(final AdventureGame game) {
		this.game = game;
		initialize();
	}
	
	/*
	 *  Initialize slide elements, including:
	 *  	- setting buttons: pause, inventory, mute
	 *  	- create the stage.
	 */
	private void initialize() {
		game.stage.clear();
		
		// initialize slide contents
		choiceButtons = new ArrayList<TextButton>();
		curSlide = game.data.getSlide(game.data.getCurrentSlideIndex());

		createFunctionButtons();
		createTitle();
		createGameText();
		createChoiceButtons();
		createTable();

		// Set the background
		float size = Gdx.graphics.getHeight();
		game.bgImg = new Image(new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName())));
		
		if (curSlide.getImageFileName().equals("facts.png")) {
			game.bgImg.setPosition(Gdx.graphics.getWidth() - size, 0);
			game.bgImg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		} else {
			game.bgImg.setSize(size, size);
			game.bgImg.setPosition(Gdx.graphics.getWidth() - size - 40, 0);
		}
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(table);
		game.stage.addActor(scrollPane);
		game.stage.addActor(volumeDialog);
		game.stage.addActor(game.bgImg);
	}

	public static final int BUTTON_WIDTH = 60;
	private void createFunctionButtons() {
		
		// ------------------- pause button ---------------------------
		
		Image pauseImg = new Image(new Texture(Gdx.files.internal("art/icons/pauseSMALL.png")));
		pauseButton = new Button(game.skin);
		pauseButton.add(pauseImg);
		pauseButton.setWidth(BUTTON_WIDTH);
		pauseButton.setHeight(BUTTON_WIDTH);
							  //Gdx.graphics.getWidth() - pauseButton.getWidth() - 10
		pauseButton.setPosition(10, Gdx.graphics.getHeight() - BUTTON_WIDTH - 10);
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new MainMenuScreen(game));
			}
		});
		game.stage.addActor(pauseButton);
		
		// -------------------- inventory button ----------------------
		
		Image invenImg = new Image(new Texture(Gdx.files.internal("art/icons/inventorySMALL.png")));
		inventoryButton = new Button(game.skin);
		inventoryButton.add(invenImg);
		inventoryButton.setWidth(BUTTON_WIDTH);
		inventoryButton.setHeight(BUTTON_WIDTH);
								  //Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10
		inventoryButton.setPosition(10,	Gdx.graphics.getHeight() - 2 * BUTTON_WIDTH - 10);
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

		//-------------------- volume dialog & slider ------------------
		
		// slider
		volumeSlider = new Slider(0f, 1f, 0.1f, false, game.skin);
		volumeSlider.setValue(game.bgMusic.getVolume());
		volumeSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				game.bgMusic.setVolume(volumeSlider.getValue());	
				updateMute();
				return false;
			}
			
		});
		
		// dialog
		volumeDialog = new Dialog("", game.skin);
		volumeDialog.setVisible(false);
		volumeDialog.add(new Label("Volume: ", game.skin));
		volumeDialog.add(volumeSlider).align(Align.left);
		TextButton okButton = new TextButton("OK", game.skin);
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				volumeDialog.setVisible(false);
			}
		});
		volumeDialog.add(okButton);
		volumeDialog.setWidth(320);
		volumeDialog.setHeight(70);
								//880
		volumeDialog.setPosition(90, 530);
		
		updateMute();
	}

	//--------------------- mute button --------------------
	
	private Image muteImg = new Image(new Texture(Gdx.files.internal("art/icons/muteSMALL.png")));
	private Image unmuteImg = new Image(new Texture(Gdx.files.internal("art/icons/unmuteSMALL.png")));
	
	private void updateMute() {
		muteButton = new Button(game.skin);
		muteButton.setWidth(BUTTON_WIDTH);
		muteButton.setHeight(BUTTON_WIDTH);
							 //Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10
		muteButton.setPosition(10, Gdx.graphics.getHeight() - 3 * BUTTON_WIDTH - 10);
		muteButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				volumeDialog.setVisible(true);
			}
		});
		if (game.bgMusic.getVolume() != 0) {
			muteButton.add(unmuteImg);
		} else {
			muteButton.add(muteImg);
		}
		game.stage.addActor(muteButton);
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
					if (curSlide.isGameOver()) {
						game.stage.clear();
						game.setScreen(new GameOverScreen(game));
					} else {
						int lastSlideIndex = game.data.getCurrentSlideIndex();
						String rejText = game.data.attemptChoice(curChoice);
						if (rejText.equals("")) {
							if (game.data.getSlide(game.data.getCurrentSlideIndex()).getSlideType() == SlideType.SHOP) {
								game.setScreen(new ShopScreen(game, lastSlideIndex));
							} else {
								initialize();
							}
						} else {
							rejectDialog = new Dialog("", game.skin);
							rejectDialog.button("Ok");
							rejectDialog.text(rejText);
							rejectDialog.setWidth(700);
							rejectDialog.setPosition(300, 300);
							game.stage.addActor(rejectDialog);
						}
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
		title.setWidth(440);
		title.pack();
		title.setWidth(440);
		title.setPosition(100, Gdx.graphics.getHeight() - title.getHeight() - 20);
		title.setAlignment(Align.left);
	}
	
	private void createGameText() {
		gameText = new Label(curSlide.getGameText(), game.skin);
		gameText.setWrap(true);
		
		// for normal slide
		int gameTextWidth = 400;
		
		if (curSlide.getSlideType() == SlideType.HISTORICAL
				|| curSlide.getSlideType() == SlideType.MANY_BUTTONS) {
			gameTextWidth = 700;
		}
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.topLeft);
		
		createScrollPane(gameTextWidth);
	
	}
	
	private void createTable() {
		table = new Table(game.skin);
		table.setWidth(Gdx.graphics.getWidth());
		table.align(Align.topLeft);
		
		table.setPosition(0, game.stage.getHeight());

		table.padLeft(100);
		
		for (int i = 0; i < game.data.getVisibleChoicesForCurrentSlide().size(); i++) {
			table.add(choiceButtons.get(i)).width(370).padTop(5);
			table.row();
		}
		
		int tableHeight = 450;
		if (curSlide.getSlideType() == SlideType.NORMAL)
			tableHeight = 350;
		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
			tableHeight = 300;
		table.padTop(tableHeight + title.getHeight());
	}
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(gameText, game.skin);
		
		int gameTextHeight = 400;
		if (curSlide.getSlideType() == SlideType.NORMAL)
			gameTextHeight = 320;
		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
			gameTextHeight = 270;
	    scrollPane.setBounds(110, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	    scrollPane.setFadeScrollBars(false);
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		
//		game.batch.begin();
//		game.sprite.draw(game.batch);
//		game.batch.end();
			
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}