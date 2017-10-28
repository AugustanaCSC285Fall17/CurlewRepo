
package edu.augustana.csc285.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private Image muteImg = new Image(new Texture(Gdx.files.internal("art/icons/muteSMALL.png")));
	private Image unmuteImg = new Image(new Texture(Gdx.files.internal("art/icons/unmuteSMALL.png")));
	
	private Slider volumeSlider;
	private Dialog volumeDialog;

	private Dialog rejectDialog;
	
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
		game.stage.addActor(volumeDialog);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName())));
		if (curSlide.getImageFileName().equals("facts.png")) {
			game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		} else {
			game.sprite.setPosition(Gdx.graphics.getWidth() - size - 40, 0);
		}
		game.sprite.setSize(size, size);
	}

	public static final int BUTTON_WIDTH = 60;
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
		
		
		
		// -------------------- inventory button ----------------------
		
		
		Image invenImg = new Image(new Texture(Gdx.files.internal("art/icons/inventorySMALL.png")));
		inventoryButton = new Button(game.skin);
		inventoryButton.add(invenImg);
		inventoryButton.setWidth(BUTTON_WIDTH);
		inventoryButton.setHeight(BUTTON_WIDTH);
		inventoryButton.setPosition(Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10,
				Gdx.graphics.getHeight() - 2 * inventoryButton.getHeight() - 10);
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
				System.err.println("button clicked!");
			}
		});
		volumeDialog.add(okButton);
		volumeDialog.setWidth(320);
		volumeDialog.setHeight(70);
		volumeDialog.setPosition(880, 530);
		
		//--------------------- mute button --------------------
		
		updateMute();
	}
	
	private void updateMute() {
		muteButton = new Button(game.skin);
		muteButton.setWidth(BUTTON_WIDTH);
		muteButton.setHeight(BUTTON_WIDTH);
		muteButton.setPosition(Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10,
				Gdx.graphics.getHeight() - 3 * inventoryButton.getHeight() - 10);
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
						String rejText = game.data.attemptChoice(curChoice);
						if (rejText.equals("")) {
//							if (!curChoice.getItemEffects().isEmpty()) {
//								Dialog inventoryDialog = new Dialog("", game.skin);
//								String effect = curChoice.getEffectsString();
//								int height = 80;
//								for (int i = 0; i < curChoice.getItemEffects().size(); i++) {
//									height += 40;
//								}
//								inventoryDialog.align(Align.bottomLeft);
//								inventoryDialog.text(effect);
//								TextButton okButton = new TextButton("OK", game.skin);
//								okButton.addListener(new ClickListener() {
//									@Override
//									public void clicked(InputEvent event, float x, float y) {
//										inventoryDialog.hide();
//										initialize();
//									}
//								});
//								inventoryDialog.add(okButton);
//								inventoryDialog.setPosition(300, 400);
//								inventoryDialog.setWidth(600);
//								inventoryDialog.setHeight(height);
//								game.stage.addActor(inventoryDialog);
//							} else {
								initialize();
//							}
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
		title.setWidth(400);
		title.pack();
		title.setWidth(400);
		title.setPosition(40, Gdx.graphics.getHeight() - title.getHeight() - 20);
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

		table.padLeft(40);
		
		for (int i = 0; i < game.data.getVisibleChoicesForCurrentSlide().size(); i++) {
			table.add(choiceButtons.get(i)).width(400).padTop(5);
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
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	    scrollPane.setFadeScrollBars(false);
	}

	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(game.stage);
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

