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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

	public static Button restartButton;
	public static Button inventoryButton;
	public static Button muteButton;
	public static Button creditButton;
	public static Button fontButton;
	public static Dialog volumeDialog;
	public static Dialog restartDialog;
	public static Dialog fontDialog;
	public static Dialog itemDialog;

	public static Slider volumeSlider;
	public static Slider fontSlider;
	private Dialog rejectDialog;
	private ScrollPane scrollPane;
	private ArrayList<TextButton> choiceButtons;
	
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
		Texture img = new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName()));
		
		Vector2 centerZoomImg = new Vector2();
		TextureRegionDrawable zoomTexture = new TextureRegionDrawable();
		if (!curSlide.getImageFileName().equals("facts.png")) {
			zoomOverlay.addListener(new ClickListener() {
				@Override
				public void touchDragged(InputEvent event, float x, float y, int pointer) {
					mouseMoved(event,x , y);
				}
				@Override
				public boolean mouseMoved(InputEvent event, float x, float y) {

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
					
					zoomTexture.setRegion(new TextureRegion(img, (int) (centerZoomImg.x), (int) (centerZoomImg.y), zoomW, zoomH));
					zoomImage.setDrawable(zoomTexture);

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
		createChoiceButtons();
		createTable();
		createGameText();
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(scrollPane);
		game.stage.addActor(table);
		
		// zoom functionality
		game.stage.addActor(zoomImage);
		game.stage.addActor(zoomRectangle);
		game.stage.addActor(zoomBorder);
		game.stage.addActor(zoomOverlay);
		
		game.stage.addActor(itemDialog);
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
		Label volumeLabel = new Label(" 10%", game.skin);
		volumeLabel.setWidth(53);
		volumeSlider.setValue(game.bgMusic.getVolume());
		volumeSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				game.bgMusic.setVolume(volumeSlider.getValue());
				volumeLabel.setText(" " + (int) (volumeSlider.getValue() * 100) + "%");
				updateMute();
				return false;
			}
			
		});
		
		// dialog
		volumeDialog = new Dialog("", game.skin);
		volumeDialog.setVisible(false);
		
		Table volumeTab = new Table(game.skin);
		volumeTab.add(new Label("Volume: ", game.skin));
		volumeTab.add(volumeSlider);
		volumeTab.add(volumeLabel).width(70);
		
		TextButton okButton = new TextButton("OK", game.skin);
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				volumeDialog.setVisible(false);
			}
		});
//		okButton.center();
		
		volumeTab.row();
		volumeTab.add(okButton).colspan(3);
		volumeTab.pack();

		volumeDialog.add(volumeTab);
		volumeDialog.setWidth(volumeTab.getWidth() + 20);
		volumeDialog.setHeight(volumeTab.getHeight() + 20);
								//880
		volumeDialog.setPosition(90, 530);
		
		updateMute();
		
		// -------------------- font button -------------------------
		
		fontButton = new Button(game.skin);
		fontButton.add(new Image(new Texture(Gdx.files.internal("art/icons/fontSMALL.png"))));
		fontButton.setWidth(BUTTON_WIDTH);
		fontButton.setHeight(BUTTON_WIDTH);
		fontButton.setPosition(10, Gdx.graphics.getHeight() - 4 * BUTTON_WIDTH - 10);
		
		
		
		fontDialog = new Dialog("", game.skin);
		fontDialog.setVisible(false);

		Table fontTab = new Table(game.skin);
		fontTab.center();
		fontTab.add(new Label("Font Size: ", game.skin));
		
		Label fontLabel = new Label(" " + fontSize + "px", game.skin);
		fontSlider = new Slider(20, 40, 2, false, game.skin);
		fontSlider.setValue(fontSize);
		fontSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				fontSize = (int) fontSlider.getValue();
				gameText.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + fontSize + ".fnt")), Color.BLACK));
				fontLabel.setText(" " + fontSize + "px");
				return false;
			}
			
		});
		fontTab.add(fontSlider);
		fontTab.add(fontLabel).width(70);
		fontTab.row();
		
		TextButton fontOkButton = new TextButton("OK", game.skin);
		fontOkButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fontDialog.setVisible(false);
			}
		});
		fontTab.add(fontOkButton).colspan(3);
		fontTab.pack();
		
		fontDialog.add(fontTab);
		fontDialog.setWidth(fontTab.getWidth() + 20);
		fontDialog.setHeight(fontTab.getHeight() + 20);
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
		itemDialog = new Dialog("", game.skin);
		Label itemLabel = new Label("", game.skin);
		itemDialog.row();
		itemDialog.align(Align.left);
		itemDialog.add(itemLabel);
		
		//ok button
//		TextButton kButton = new TextButton("OK", game.skin);
//		kButton.addListener(new Cl)
//		itemDialog.add(kButton);
		
		
		itemDialog.setPosition(130, Gdx.graphics.getHeight() - 300, Align.left);
		itemDialog.setVisible(false);
		
		for (ActionChoice curChoice : curChoices) {
			String curChoiceText = curChoice.getChoiceText();
			
			TextButton newButton = new TextButton(curChoiceText, game.skin);
			newButton.padTop(10).padBottom(10);
			newButton.getLabel().setWrap(true);
			newButton.getLabel().setWidth(350);
			newButton.getLabel().pack();
			
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
								if(!curChoice.getEffectsString().equals("Inventory change:\n")) {
									System.out.println("dialog should open");
									itemDialog.setVisible(true);
									itemLabel.setText(curChoice.getEffectsString());
									itemLabel.pack();
									itemDialog.setWidth(itemLabel.getWidth() + 150);
									itemDialog.addAction(Actions.sequence(
											Actions.fadeIn(0.5f),
											Actions.delay(1),
											Actions.fadeOut(0.5f),
											Actions.run(new Runnable() {
												@Override
												public void run() {
													initialize();
													
												}
											})
											));
								} else {
									initialize();
								}
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
	
	private void createTable() {
		
		table = new Table(game.skin);
		table.align(Align.topLeft);
		
		for (int i = 0; i < game.data.getVisibleChoicesForCurrentSlide().size(); i++) {
			table.add(choiceButtons.get(i)).width(370).padTop(5);
			table.row();
		}
		table.pack();
//		if (curSlide.getSlideType() == SlideType.NORMAL)
//			tableHeight = 350;
//		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
//			tableHeight = 300;
		table.setPosition(100, 10);
	}
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(gameText, game.skin);
		
	    scrollPane.setBounds(110, 20 + table.getHeight(),
	    		gameTextWidth, Gdx.graphics.getHeight() - (title.getHeight() + table.getHeight()) - 40);
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