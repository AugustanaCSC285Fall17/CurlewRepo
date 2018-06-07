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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

	public static Button restartButton;
	public static Button inventoryButton;
	public static Button muteButton;
	public static Button creditButton;
	public static Button fontButton;
	public static Button zoomButton;
	public static Dialog volumeDialog;
	public static Dialog restartDialog;
	public static Dialog fontDialog;
	public static Dialog itemDialog;
	public static Dialog zoomDialog;

	public static Slider volumeSlider;
	public static Slider fontSlider;
	public static Slider zoomSlider;
	public static CheckBox muteCheckbox;
	private Dialog rejectDialog;
	private ScrollPane scrollPane;
	private ArrayList<TextButton> choiceButtons;
	
	private Image zoomImage;
	private Image zoomBorder;
	private Image zoomRectangle;
	private Label zoomOverlay;
	private Label zoomLabel;
	private Label zoomImageLabel;
	private Image zoomGray;
	private CheckBox zoomInsCheckbox;
	private static boolean zoomInsChecked = true;
	
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
		float size = AdventureGame.SCREEN_HEIGHT;
		game.bgImg = new Image(new Texture(Gdx.files.internal("slideImages/" + curSlide.getImageFileName())));

		game.bgImg.setSize(size, size);
		game.bgImg.setPosition(AdventureGame.SCREEN_WIDTH - size, 0);

		setupZoom();
		
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
		game.stage.addActor(zoomGray);
		game.stage.addActor(zoomImage);
		game.stage.addActor(zoomRectangle);
		game.stage.addActor(zoomBorder);
		game.stage.addActor(zoomOverlay);
		game.stage.addActor(zoomDialog);
		game.stage.addActor(zoomImageLabel);
		
		game.stage.addActor(volumeDialog);
		game.stage.addActor(fontDialog);
		game.stage.addActor(restartDialog);
		game.stage.addActor(itemDialog);
	}
	
	private float zoomMag;
	private float zoomWPanel;
	private float zoomHPanel;
	private void setupZoom() {
		zoomImage = new Image();
		zoomImage.setVisible(false);
		zoomImage.setSize(AdventureGame.percentWidth(35), AdventureGame.percentHeight(75));
		zoomImage.setPosition((AdventureGame.SCREEN_WIDTH - AdventureGame.SCREEN_HEIGHT - zoomImage.getWidth()) / 2, AdventureGame.SCREEN_HEIGHT - zoomImage.getHeight() - AdventureGame.percentHeight(10));
		
		zoomGray = new Image(new Texture(Gdx.files.internal("art/zoomRect.png")));
		zoomGray.setVisible(false);
		zoomGray.setSize(AdventureGame.SCREEN_WIDTH - game.bgImg.getWidth(), AdventureGame.percentHeight(100));
		zoomGray.setPosition(0, 0);
		
		zoomImageLabel = new Label("IMAGE ZOOM PANEL\n1. Move mouse left to hide.\n2. Scroll to zoom in/out.", game.skin);
		zoomImageLabel.pack();
		zoomImageLabel.setColor(0, 0, 0, .7f);
		zoomImageLabel.setPosition(zoomImage.getX() + AdventureGame.percentWidth(2), zoomImage.getY() + zoomImage.getHeight() - zoomImageLabel.getHeight() - AdventureGame.percentHeight(2));
		zoomImageLabel.setVisible(false);
		
		zoomBorder = new Image(new Texture(Gdx.files.internal("art/grid.png")));
		zoomBorder.setBounds(zoomImage.getX(), zoomImage.getY(), zoomImage.getWidth(), zoomImage.getHeight());
		
		zoomOverlay = new Label("", game.skin);
		zoomOverlay.setSize(AdventureGame.SCREEN_HEIGHT, AdventureGame.SCREEN_HEIGHT);
		zoomOverlay.setPosition(AdventureGame.SCREEN_WIDTH - AdventureGame.SCREEN_HEIGHT, 0);
		if (curSlide.getImageFileName().equals("facts.png")) {
			zoomOverlay.setVisible(false);
		}
		
		zoomMag = 2.5f;
		zoomWPanel = zoomImage.getWidth() / zoomMag;
		zoomHPanel = zoomImage.getHeight() / zoomMag;
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

					float porportion = (float) img.getWidth() / AdventureGame.SCREEN_HEIGHT;
					
					int zoomW = (int) (zoomWPanel * porportion);
					int zoomH = (int) (zoomHPanel * porportion);
					

					float zoomRectX = AdventureGame.SCREEN_WIDTH - AdventureGame.SCREEN_HEIGHT + x 
																							- zoomRectangle.getWidth() / 2;
					float zoomRectY = y - zoomRectangle.getHeight() / 2;
					
					centerZoomImg.x = (x - zoomWPanel / 2) * porportion;
					centerZoomImg.y = (AdventureGame.SCREEN_HEIGHT - y - zoomHPanel / 2) * porportion;
					
					if ((float) zoomW / 2 > x * porportion) {
						centerZoomImg.x = 0;
						zoomRectX = AdventureGame.SCREEN_WIDTH - AdventureGame.SCREEN_HEIGHT;
						
					} else if (x > (float) AdventureGame.SCREEN_HEIGHT - zoomWPanel / 2) {
						centerZoomImg.x = (AdventureGame.SCREEN_HEIGHT - zoomWPanel) * porportion;
						zoomRectX = AdventureGame.SCREEN_WIDTH - zoomRectangle.getWidth();
					}
					
					if ((float) zoomH / 2 > (AdventureGame.SCREEN_HEIGHT - y) * porportion) {
						centerZoomImg.y = 0;
						zoomRectY = AdventureGame.SCREEN_HEIGHT - zoomRectangle.getHeight();
					} else if (AdventureGame.SCREEN_HEIGHT - y > (float) AdventureGame.SCREEN_HEIGHT - zoomHPanel / 2) {
						centerZoomImg.y = (AdventureGame.SCREEN_HEIGHT - zoomHPanel) * porportion;
						zoomRectY = 0;
					}
					
					if (Gdx.input.getX() * AdventureGame.SCREEN_WIDTH / Gdx.graphics.getWidth() >= AdventureGame.SCREEN_WIDTH - game.bgImg.getWidth()) {
						zoomImage.setVisible(true);
					}
					
					zoomTexture.setRegion(new TextureRegion(img, (int) (centerZoomImg.x), (int) (centerZoomImg.y), zoomW, zoomH));
					zoomImage.setDrawable(zoomTexture);

					zoomRectangle.setPosition(zoomRectX, zoomRectY);
					zoomRectangle.setVisible(true);
					zoomBorder.setVisible(true);
					if (zoomInsCheckbox.isChecked()) {
						zoomImageLabel.setVisible(true);
					}
					zoomGray.setVisible(true);
					return true;
				}
				
				@Override
				public boolean scrolled(InputEvent event, float x, float y, int amount) {
					if (amount < 0 && zoomMag < 4) {
						zoomMag += .25;
						zoomWPanel = zoomImage.getWidth() / zoomMag;
						zoomHPanel = zoomImage.getHeight() / zoomMag;
						zoomRectangle.setSize(zoomWPanel, zoomHPanel);
						mouseMoved(event, x, y);
					} else if (amount > 0 && zoomMag > 1.5) {
						zoomMag -= .25;
						zoomWPanel = zoomImage.getWidth() / zoomMag;
						zoomHPanel = zoomImage.getHeight() / zoomMag;
						zoomRectangle.setSize(zoomWPanel, zoomHPanel);
						mouseMoved(event, x, y);
					}
					zoomSlider.setValue(zoomMag);
					zoomLabel.setText(" " + (int) (zoomMag * 100) + " %");
					return true;
				}
			});
		}
		
		zoomRectangle = new Image(new Texture(Gdx.files.internal("art/zoomRect.png")));
		zoomRectangle.setVisible(false);
		zoomBorder.setVisible(false);
		zoomRectangle.setBounds(0, 0, zoomWPanel, zoomHPanel);
	}

	public static final float BUTTON_SIZE = AdventureGame.percentWidth(6);
	
	public static TooltipManager tooltip = new TooltipManager();
	
	private void createFunctionButtons() {
		tooltip.initialTime = 0;
		tooltip.offsetX = 0;
		tooltip.offsetY = 0;
		
		// ------------------- restart button ---------------------------

		restartDialog = new Dialog("", game.skin);
		
		Image restartImg = new Image(new Texture(Gdx.files.internal("art/icons/restartSMALL.png")));
		restartButton = new Button(game.skin);
		restartButton.add(restartImg);
		restartButton.setWidth(BUTTON_SIZE);
		restartButton.setHeight(BUTTON_SIZE);
							  //AdventureGame.GAME_SCREEN_WIDTH - pauseButton.getWidth() - 10
		restartButton.setPosition(AdventureGame.percentWidth(1), AdventureGame.SCREEN_HEIGHT - BUTTON_SIZE - AdventureGame.percentHeight(1));
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				restartDialog.setVisible(true);
			}
		});
		
		
		restartDialog.setVisible(false);
		restartDialog.row();
		restartDialog.align(Align.center);
		Label restartLabel = new Label("Are you sure you want to restart the game?", game.skin);
		restartDialog.add(restartLabel);
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
		restartDialog.setWidth(AdventureGame.percentWidth(45));
		restartDialog.setHeight(AdventureGame.percentHeight(14));
								//880
		restartDialog.setPosition((AdventureGame.SCREEN_WIDTH - restartDialog.getWidth())/ 2, 
				(AdventureGame.SCREEN_HEIGHT - restartDialog.getHeight())/ 2);	
		
		
		TextTooltip restartTt = new TextTooltip("Restart", tooltip, game.skin);
		restartButton.addListener(restartTt);
		game.stage.addActor(restartButton);
		
		// -------------------- inventory button ----------------------
		
		Image invenImg = new Image(new Texture(Gdx.files.internal("art/icons/inventorySMALL.png")));
		inventoryButton = new Button(game.skin);
		inventoryButton.add(invenImg);
		inventoryButton.setWidth(BUTTON_SIZE);
		inventoryButton.setHeight(BUTTON_SIZE);
								  //AdventureGame.GAME_SCREEN_WIDTH - inventoryButton.getWidth() - 10
		inventoryButton.setPosition(AdventureGame.percentWidth(1), AdventureGame.SCREEN_HEIGHT - 2 * BUTTON_SIZE - AdventureGame.percentHeight(1));
		inventoryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// set the destination of the back button of inventory to
				// the last slide
				game.stage.clear();
				game.setScreen(new InventoryScreen(game));
			}
		});
		
		TextTooltip invenTt = new TextTooltip("Inventory", tooltip, game.skin);
		inventoryButton.addListener(invenTt);
		game.stage.addActor(inventoryButton);

		//-------------------- volume dialog & slider ------------------
		
		// slider
		volumeSlider = new Slider(0f, 1f, 0.1f, false, game.skin);
		Label volumeLabel = new Label(" " + (int) (game.bgMusic.getVolume() * 100) + "%", game.skin);
		volumeLabel.setWidth(AdventureGame.percentWidth(4));
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
		Label volTabLabel = new Label("Volume: ", game.skin);
		volumeTab.add(volTabLabel);
		volumeTab.add(volumeSlider);
		volumeTab.add(volumeLabel).width(AdventureGame.percentWidth(5));
		
		TextButton okButton = new TextButton("OK", game.skin);
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				volumeDialog.setVisible(false);
			}
		});
		muteCheckbox = new CheckBox("Mute", game.skin);
		muteCheckbox.setChecked(game.musicMuted);
		muteCheckbox.addListener(new ChangeListener() {
		    @Override
		    public void changed(ChangeEvent event, Actor actor) {
				game.musicMuted = muteCheckbox.isChecked();
				if (game.musicMuted) {
					game.bgMusic.pause();
				} else {
					game.bgMusic.play();
				}
			}
			
		});
		
		volumeTab.row();
		volumeTab.add(muteCheckbox).colspan(3).left().pad(AdventureGame.percentHeight(1));
		volumeTab.row();
		volumeTab.add(okButton).colspan(3);
		volumeTab.pack();

		volumeDialog.add(volumeTab).expandX().expandY().left();
		volumeDialog.setWidth(volumeTab.getWidth() + AdventureGame.percentWidth(4));
		volumeDialog.setHeight(volumeTab.getHeight() + AdventureGame.percentHeight(4));
								//880
		volumeDialog.setPosition(AdventureGame.percentWidth(7), AdventureGame.SCREEN_HEIGHT - BUTTON_SIZE * 3);
		
		updateMute();
		
		// -------------------- font button -------------------------
		
		fontButton = new Button(game.skin);
		fontButton.add(new Image(new Texture(Gdx.files.internal("art/icons/fontSMALL.png"))));
		fontButton.setWidth(BUTTON_SIZE);
		fontButton.setHeight(BUTTON_SIZE);
		fontButton.setPosition(AdventureGame.percentWidth(1), AdventureGame.SCREEN_HEIGHT - 4 * BUTTON_SIZE - AdventureGame.percentHeight(1));
		
		
		
		fontDialog = new Dialog("", game.skin);
		fontDialog.setVisible(false);

		Table fontTab = new Table(game.skin);
		fontTab.center();
		Label fontTabLabel = new Label("Font Size: ", game.skin);
		fontTab.add(fontTabLabel);
		
		Label fontLabel = new Label(" " + AdventureGame.textFontSize + "px", game.skin);
		fontSlider = new Slider(AdventureGame.appFontSize - 6, AdventureGame.appFontSize + 14, 2, false, game.skin);
		fontSlider.setValue(AdventureGame.textFontSize);
		fontSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				AdventureGame.textFontSize = (int) fontSlider.getValue();
				gameText.setStyle(new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.textFontSize + ".fnt")), Color.BLACK));
				fontLabel.setText(" " + AdventureGame.textFontSize + "px");
				return false;
			}
			
		});
		fontTab.add(fontSlider);	
		fontTab.add(fontLabel).width(AdventureGame.percentWidth(5));
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
		
		fontDialog.add(fontTab).align(Align.center).expandX().expandY().left();
		fontDialog.setWidth(fontTab.getWidth() + AdventureGame.percentWidth(4));
		fontDialog.setHeight(fontTab.getHeight() + AdventureGame.percentHeight(4));
								//880
		fontDialog.setPosition(AdventureGame.percentWidth(7), AdventureGame.SCREEN_HEIGHT - BUTTON_SIZE * 4);
		
		fontButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fontDialog.setVisible(true);
			}
		});
		
		TextTooltip fontTt = new TextTooltip("Font Size", tooltip, game.skin);
		fontButton.addListener(fontTt);
		game.stage.addActor(fontButton);
		
		// -------------------- zoom button ------------------------
		
		zoomButton = new Button(game.skin);
		zoomButton.add(new Image(new Texture(Gdx.files.internal("art/icons/zoom.png"))));
		zoomButton.setWidth(BUTTON_SIZE);
		zoomButton.setHeight(BUTTON_SIZE);
		zoomButton.setPosition(AdventureGame.percentWidth(1), AdventureGame.SCREEN_HEIGHT - 5 * BUTTON_SIZE - AdventureGame.percentHeight(1));
		
		zoomDialog = new Dialog("", game.skin);
		zoomDialog.setVisible(false);
		zoomDialog.align(Align.left);

		zoomInsCheckbox = new CheckBox("Zoom instructions", game.skin);
		zoomInsCheckbox.setChecked(zoomInsChecked);
		zoomInsCheckbox.addListener(new ChangeListener() {
		    @Override
		    public void changed(ChangeEvent event, Actor actor) {
				zoomInsChecked = zoomInsCheckbox.isChecked();
				zoomImageLabel.setVisible(zoomInsChecked);
			}
			
		});

		Table zoomTab = new Table(game.skin);
		Label zoomTabLabel = new Label("Zoom: ", game.skin);
		zoomTab.add(zoomTabLabel);
		
		zoomLabel = new Label(" " + (int) (zoomMag * 100) + " %", game.skin);
		zoomSlider = new Slider(1.5f, 4.0f, .25f, false, game.skin);
		zoomSlider.setValue(zoomMag);
		zoomSlider.addListener(new EventListener(){
			@Override
			public boolean handle(Event event) {
				zoomMag = zoomSlider.getValue();
				zoomWPanel = zoomImage.getWidth() / zoomMag;
				zoomHPanel = zoomImage.getHeight() / zoomMag;
				zoomRectangle.setSize(zoomWPanel, zoomHPanel);
				zoomLabel.setText(" " + (int) (zoomMag * 100) + " %");
				return true;
			}
			
		});
		zoomTab.add(zoomSlider);
		zoomTab.add(zoomLabel).width(AdventureGame.percentWidth(5));
		zoomTab.row();
		zoomTab.add(zoomInsCheckbox).colspan(3).pad(AdventureGame.percentHeight(1)).align(Align.left);
		zoomTab.row();
		
		TextButton zoomOkButton = new TextButton("OK", game.skin);
		zoomOkButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				zoomDialog.setVisible(false);
			}
		});
		zoomTab.add(zoomOkButton).colspan(3).align(Align.center);
		zoomTab.pack();
		
		zoomDialog.add(zoomTab).expandX().expandY().left();
		zoomDialog.setWidth(zoomTab.getWidth() + AdventureGame.percentWidth(4));
		zoomDialog.setHeight(zoomTab.getHeight() + AdventureGame.percentHeight(4));
								//880
		zoomDialog.setPosition(AdventureGame.percentWidth(7),  AdventureGame.SCREEN_HEIGHT - BUTTON_SIZE * 5);
		
		zoomButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				zoomDialog.setVisible(true);
			}
		});
		
		TextTooltip zoomTt = new TextTooltip("Zoom Magnitude", tooltip, game.skin);
		zoomButton.addListener(zoomTt);
		game.stage.addActor(zoomButton);
		
		
		// -------------------- credit button ----------------------
		
		Image creditImg = new Image(new Texture(Gdx.files.internal("art/icons/creditSMALL.png")));
		creditButton = new Button(game.skin);
		creditButton.add(creditImg);
		creditButton.setWidth(BUTTON_SIZE);
		creditButton.setHeight(BUTTON_SIZE);
								  //AdventureGame.GAME_SCREEN_WIDTH - inventoryButton.getWidth() - 10
		creditButton.setPosition(AdventureGame.percentWidth(1), AdventureGame.SCREEN_HEIGHT - 6 * BUTTON_SIZE - AdventureGame.percentHeight(1));
		creditButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// set the destination of the back button of inventory to
				// the last slide
				game.stage.clear();
				game.setScreen(new CreditsScreen(game));
			}
		});
		
		TextTooltip creditsTt = new TextTooltip("Credits", tooltip, game.skin);
		creditButton.addListener(creditsTt);
		game.stage.addActor(creditButton);
	}

	//--------------------- mute button --------------------
	
	private Image muteImg = new Image(new Texture(Gdx.files.internal("art/icons/muteSMALL.png")));
	private Image unmuteImg = new Image(new Texture(Gdx.files.internal("art/icons/unmuteSMALL.png")));
	
	private void updateMute() {
		muteButton = new Button(game.skin);
		muteButton.setWidth(BUTTON_SIZE);
		muteButton.setHeight(BUTTON_SIZE);
							 //AdventureGame.GAME_SCREEN_WIDTH - inventoryButton.getWidth() - 10
		muteButton.setPosition(AdventureGame.percentWidth(1), AdventureGame.SCREEN_HEIGHT - 3 * BUTTON_SIZE - AdventureGame.percentHeight(1));
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
		
		TextTooltip muteTt = new TextTooltip("Volume", tooltip, game.skin);
		muteButton.addListener(muteTt);
		game.stage.addActor(muteButton);
	}

	
	private void createChoiceButtons() {
		List<ActionChoice> curChoices = game.data.getVisibleChoicesForCurrentSlide();
		itemDialog = new Dialog("", game.skin);
		Label itemLabel = new Label("", game.skin);
		itemDialog.row();
		itemDialog.align(Align.left);
		itemDialog.add(itemLabel);
		itemDialog.setVisible(false);
		
		for (ActionChoice curChoice : curChoices) {
			String curChoiceText = curChoice.getChoiceText();
			
			TextButton newButton = new TextButton(curChoiceText, game.skin);
			newButton.padTop(AdventureGame.percentWidth(1)).padBottom(AdventureGame.percentHeight(1));
			newButton.getLabel().setWrap(true);
			newButton.getLabel().setWidth(AdventureGame.percentWidth(27));
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
								if(!curChoice.getEffectsStringIfVisible().equals("Inventory change:\n") && curChoice.isEffectVisible()) {
									itemDialog.setVisible(true);
									itemLabel.setText(curChoice.getEffectsStringIfVisible());
									itemLabel.setAlignment(Align.center);
									itemLabel.pack();
									itemDialog.setWidth(itemLabel.getWidth() + AdventureGame.percentWidth(12));
									itemDialog.setHeight(itemLabel.getHeight() + AdventureGame.percentHeight(4));
									itemDialog.setPosition((AdventureGame.SCREEN_WIDTH - itemDialog.getWidth()) / 2, (AdventureGame.SCREEN_HEIGHT - itemDialog.getHeight()) / 2);
									itemDialog.addAction(Actions.sequence(
											Actions.fadeIn(0.5f),
											Actions.delay(0.5f),
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
							rejectDialog.setWidth(AdventureGame.percentWidth(55));
							rejectDialog.setPosition(AdventureGame.percentWidth(23), AdventureGame.percentHeight(42));
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
		title.setWidth(AdventureGame.percentWidth(34));
		title.pack();
		title.setWidth(AdventureGame.percentWidth(34));
		title.setPosition(AdventureGame.percentWidth(8), AdventureGame.SCREEN_HEIGHT - title.getHeight() - AdventureGame.percentHeight(2));
		title.setAlignment(Align.left);
	}
	
	private void createGameText() {
		gameText = new Label(curSlide.getGameText(), new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/MyriadProLight" + AdventureGame.textFontSize + ".fnt")), Color.BLACK));
		gameText.setWrap(true);
		
		// for normal slide
		float gameTextWidth = AdventureGame.percentWidth(31);
		
		if (curSlide.getSlideType() == SlideType.HISTORICAL) {
			gameTextWidth = AdventureGame.percentWidth(55);
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
			table.add(choiceButtons.get(i)).width(AdventureGame.percentWidth(29)).padTop(AdventureGame.percentHeight(1));
			table.row();
		}
		table.pack();
//		if (curSlide.getSlideType() == SlideType.NORMAL)
//			tableHeight = 350;
//		else if (curSlide.getSlideType() == SlideType.MANY_BUTTONS)
//			tableHeight = 300;
		table.setPosition(AdventureGame.percentWidth(8), AdventureGame.percentHeight(1));
	}
	
	private void createScrollPane(float gameTextWidth) {
		scrollPane = new ScrollPane(gameText, game.skin);
		
	    scrollPane.setBounds(AdventureGame.percentWidth(8), AdventureGame.percentHeight(1) + table.getHeight(),
	    		gameTextWidth, AdventureGame.SCREEN_HEIGHT - (title.getHeight() + table.getHeight()) - AdventureGame.percentHeight(3));
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
		
		if (Gdx.input.getX() * AdventureGame.SCREEN_WIDTH / Gdx.graphics.getWidth() < AdventureGame.SCREEN_WIDTH - game.bgImg.getWidth()) {
			zoomImage.setVisible(false);
			zoomRectangle.setVisible(false);
			zoomBorder.setVisible(false);
			zoomImageLabel.setVisible(false);
			zoomGray.setVisible(false);
			game.stage.setScrollFocus(scrollPane);
		} else {
			game.stage.setScrollFocus(zoomOverlay);
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
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}