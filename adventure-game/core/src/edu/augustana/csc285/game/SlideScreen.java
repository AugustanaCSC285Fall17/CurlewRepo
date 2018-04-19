package edu.augustana.csc285.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.Slide;
import edu.augustana.csc285.game.datamodel.SlideType;

public class SlideScreen implements Screen {
	
	private AdventureGame game;
	private Slide curSlide;
	
	private static int fontSize = 26;

	private Table table;
	private Label title;
	private Label gameText;

	private Button restartButton;
	private Button inventoryButton;
	private Button muteButton;
	private Button creditButton;
	private Button fontButton;
	private ArrayList<TextButton> choiceButtons;
	private ScrollPane scrollPane;
	private Slider volumeSlider;
	private Slider fontSlider;
	private Dialog volumeDialog;
	private Dialog rejectDialog;
	private Dialog restartDialog;
	private Dialog fontDialog;
	
	private Image zoomImage;
	private Image zoomBorder;
	private Image zoomRectangle;
	
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

		// Set the background
		float size = Gdx.graphics.getHeight();
		game.bgImg = new Image(new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName())));

		game.bgImg.setSize(size, size);
		game.bgImg.setPosition(Gdx.graphics.getWidth() - size, 0);
		
		zoomImage = new Image();
		zoomImage.setVisible(false);
		zoomImage.setSize(450, 680);
		zoomImage.setPosition(90, Gdx.graphics.getHeight() - zoomImage.getHeight() - 20);

		zoomBorder = new Image(new Texture(Gdx.files.internal("art/grid.png")));
		zoomBorder.setBounds(zoomImage.getX(), zoomImage.getY(), zoomImage.getWidth(), zoomImage.getHeight());
		
		Label zoomOverlay = new Label("", game.skin);
		zoomOverlay.setSize(size, size);
		zoomOverlay.setPosition(Gdx.graphics.getWidth() - size, 0);
		
		float zoomMag = (float) 2.5;
		float zoomWPanel = zoomImage.getWidth() / zoomMag;
		float zoomHPanel = zoomImage.getHeight() / zoomMag;
		if (!curSlide.getImageFileName().equals("facts.png")) {
			zoomOverlay.addListener(new ClickListener() {
				@Override
				public void touchDragged(InputEvent event, float x, float y, int pointer) {
					mouseMoved(event,x , y);
				}
				@Override
				public boolean mouseMoved(InputEvent event, float x, float y) {
					
					
					Vector2 centerZoomImg = new Vector2();
					Texture img = new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName()));

					float porportion = (float) img.getWidth() / AdventureGame.GAME_SCREEN_HEIGHT;
					
					int zoomW = (int) (zoomWPanel * porportion);
					int zoomH = (int) (zoomHPanel * porportion);
					

					float zoomRectX = AdventureGame.GAME_SCREEN_WIDTH - AdventureGame.GAME_SCREEN_HEIGHT + x 
																							- zoomRectangle.getWidth() / 2;
					float zoomRectY = y - zoomRectangle.getHeight() / 2;
					
					centerZoomImg.x = (x - zoomWPanel / 2) * porportion;
					centerZoomImg.y = (AdventureGame.GAME_SCREEN_HEIGHT - y - zoomHPanel / 2) * porportion;
					
					if ((float) zoomW / 2 > x * porportion) {
						centerZoomImg.x = 0;
						zoomRectX = AdventureGame.GAME_SCREEN_WIDTH - AdventureGame.GAME_SCREEN_HEIGHT;
						
					} else if (x > (float) AdventureGame.GAME_SCREEN_HEIGHT - zoomWPanel / 2) {
						centerZoomImg.x = (AdventureGame.GAME_SCREEN_HEIGHT - zoomWPanel) * porportion;
						zoomRectX = AdventureGame.GAME_SCREEN_WIDTH - zoomRectangle.getWidth();
					}
					
					if ((float) zoomH / 2 > (AdventureGame.GAME_SCREEN_HEIGHT - y) * porportion) {
						centerZoomImg.y = 0;
						zoomRectY = AdventureGame.GAME_SCREEN_HEIGHT - zoomRectangle.getHeight();
					} else if (AdventureGame.GAME_SCREEN_HEIGHT - y > (float) AdventureGame.GAME_SCREEN_HEIGHT - zoomHPanel / 2) {
						centerZoomImg.y = (AdventureGame.GAME_SCREEN_HEIGHT - zoomHPanel) * porportion;
						zoomRectY = 0;
					}
					
					if (Gdx.input.getX() >= Gdx.graphics.getWidth() - game.bgImg.getWidth()) {
						zoomImage.setVisible(true);
					}
					
					zoomImage.setDrawable(new TextureRegionDrawable(
							new TextureRegion(new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName())),
							(int) (centerZoomImg.x), 
							(int) (centerZoomImg.y), zoomW, zoomH)));

					zoomRectangle.setPosition(zoomRectX, zoomRectY);
					zoomRectangle.setVisible(true);
					zoomBorder.setVisible(true);
					return true;
				}
				
			});
		}
		
		zoomRectangle = new Image(new Texture(Gdx.files.internal("art/zoomRect.png")));
		zoomRectangle.setVisible(false);
		zoomBorder.setVisible(false);
		zoomRectangle.setBounds(0, 0, zoomWPanel, zoomHPanel);
		
		game.stage.addActor(game.bgImg);
		createFunctionButtons();
		createTitle();
		createGameText();
		createChoiceButtons();
		createTable();
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(scrollPane);
		game.stage.addActor(table);
		
		// zoom functionality
		game.stage.addActor(zoomImage);
		game.stage.addActor(zoomRectangle);
		game.stage.addActor(zoomBorder);
		game.stage.addActor(zoomOverlay);

		game.stage.addActor(volumeDialog);
		game.stage.addActor(fontDialog);
		game.stage.addActor(restartDialog);
	}

	public static final int BUTTON_WIDTH = 60;
	
	private TooltipManager tooltip = new TooltipManager();
	
	private void createFunctionButtons() {
		tooltip.initialTime = 0;
		tooltip.offsetX = 0;
		tooltip.offsetY = 0;
		
		// ------------------- restart button ---------------------------

		restartDialog = new Dialog("", game.skin);
		
		Image restartImg = new Image(new Texture(Gdx.files.internal("art/icons/restartSMALL.png")));
		restartButton = new Button(game.skin);
		restartButton.add(restartImg);
		restartButton.setWidth(BUTTON_WIDTH);
		restartButton.setHeight(BUTTON_WIDTH);
							  //Gdx.graphics.getWidth() - pauseButton.getWidth() - 10
		restartButton.setPosition(10, Gdx.graphics.getHeight() - BUTTON_WIDTH - 10);
		
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				restartDialog.setVisible(true);
			}
		});
		
		
		restartDialog.setVisible(false);
		restartDialog.row();
		restartDialog.align(Align.center);
		restartDialog.add(new Label("Are you sure you want to restart the game?", game.skin));
		restartDialog.row();
		TextButton button = new TextButton("Yes", game.skin);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.restartGame();
				game.data.setGameStarted(true);
				game.stage.clear();
				game.setScreen(new SlideScreen(game));
			}
		});
		TextButton noButton = new TextButton("No", game.skin);
		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				restartDialog.setVisible(false);
			}
		});
		Table buttonTable = new Table();
		buttonTable.align(Align.center);
		buttonTable.add(button);
		buttonTable.add(noButton);
		restartDialog.add(buttonTable);
		restartDialog.setWidth(600);
		restartDialog.setHeight(100);
								//880
		restartDialog.setPosition((AdventureGame.GAME_SCREEN_WIDTH - restartDialog.getWidth())/ 2, 
				(AdventureGame.GAME_SCREEN_HEIGHT - restartDialog.getHeight())/ 2);	
		
		
		restartButton.addListener(new TextTooltip("Restart", tooltip, game.skin));
		game.stage.addActor(restartButton);
		
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
		inventoryButton.addListener(new TextTooltip("Inventory", tooltip, game.skin));
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
		
		// -------------------- font button -------------------------
		
		fontButton = new Button(game.skin);
		fontButton.add(new Image(new Texture(Gdx.files.internal("art/icons/fontSMALL.png"))));
		fontButton.setWidth(BUTTON_WIDTH);
		fontButton.setHeight(BUTTON_WIDTH);
		fontButton.setPosition(10, Gdx.graphics.getHeight() - 4 * BUTTON_WIDTH - 10);
		
		fontSlider = new Slider(20, 40, 2, false, game.skin);
		fontSlider.setValue(fontSize);
		fontSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				fontSize = (int) fontSlider.getValue();
				gameText.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + fontSize + ".fnt")), Color.BLACK));
				return false;
			}
			
		});
		
		fontDialog = new Dialog("", game.skin);
		fontDialog.setVisible(false);
		fontDialog.row();
		fontDialog.align(Align.center);
		fontDialog.add(new Label("Font Size: ", game.skin));
		fontDialog.add(fontSlider);
		TextButton fontOkButton = new TextButton("OK", game.skin);
		fontOkButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fontDialog.setVisible(false);
			}
		});
		fontDialog.add(fontOkButton);
		fontDialog.setWidth(390);
		fontDialog.setHeight(70);
								//880
		fontDialog.setPosition(90, 530);
		
		fontButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fontDialog.setVisible(true);
			}
		});
		game.stage.addActor(fontButton);
		
		
		// -------------------- credit button ----------------------
		
		Image creditImg = new Image(new Texture(Gdx.files.internal("art/icons/creditSMALL.png")));
		creditButton = new Button(game.skin);
		creditButton.add(creditImg);
		creditButton.setWidth(BUTTON_WIDTH);
		creditButton.setHeight(BUTTON_WIDTH);
								  //Gdx.graphics.getWidth() - inventoryButton.getWidth() - 10
		creditButton.setPosition(10, Gdx.graphics.getHeight() - 5 * BUTTON_WIDTH - 10);
		creditButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// set the destination of the back button of inventory to
				// the last slide
				game.stage.clear();
				game.setScreen(new CreditsScreen(game));
			}
		});
		creditButton.addListener(new TextTooltip("Credits", tooltip, game.skin));
		game.stage.addActor(creditButton);
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
		muteButton.addListener(new TextTooltip("Volume", tooltip, game.skin));
		game.stage.addActor(muteButton);
	}

	
	private void createChoiceButtons() {
		List<ActionChoice> curChoices = game.data.getVisibleChoicesForCurrentSlide();
		for (ActionChoice curChoice : curChoices) {
			String curChoiceText = curChoice.getChoiceText();
			
			TextButton newButton = new TextButton(curChoiceText, game.skin);
			newButton.getLabel().setWrap(true);
			newButton.getLabel().pack();
			newButton.getLabel().setWidth(350);
			newButton.getLabel().pack();
			newButton.getLabel().setWidth(350);
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
		if (curSlide.getSlideType() == SlideType.NORMAL) {
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
		gameText.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + fontSize + ".fnt")), Color.BLACK));
		gameText.setWrap(true);
		
		// for normal slide
		int gameTextWidth = 400;
		
		if (curSlide.getSlideType() == SlideType.HISTORICAL) {
			gameTextWidth = 700;
		}
		
		gameText.pack();
		gameText.setWidth(gameTextWidth);
		gameText.pack();
		gameText.setWidth(gameTextWidth);
		gameText.setAlignment(Align.topLeft);
		
		createScrollPane(gameTextWidth);
	
	}
	
	float tableHeight;
	
	private void createTable() {
		
		table = new Table(game.skin);
		table.align(Align.topLeft);
		
		for (int i = 0; i < game.data.getVisibleChoicesForCurrentSlide().size(); i++) {
			table.add(choiceButtons.get(i)).width(370).padTop(5);
			table.row();
		}

		tableHeight = table.getPrefHeight();
//		if (curSlide.getSlideType() == SlideType.NORMAL)
//			tableHeight = 350;
//		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
//			tableHeight = 300;
		table.setPosition(100, tableHeight + 10);
	}
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(gameText, game.skin);
		
	    scrollPane.setBounds(110, tableHeight - 20,
	    		gameTextWidth, Gdx.graphics.getHeight() - title.getHeight() - tableHeight);
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
		
		if (Gdx.input.getX() < Gdx.graphics.getWidth() - game.bgImg.getWidth()) {
			zoomImage.setVisible(false);
			zoomRectangle.setVisible(false);
			zoomBorder.setVisible(false);
		}
		
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