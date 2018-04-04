package edu.augustana.csc285.game;

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

import edu.augustana.csc285.game.datamodel.Gender;
import edu.augustana.csc285.game.datamodel.Item;

public class InventoryScreen implements Screen {
	
	private AdventureGame game;
	
	private Table table;
	private Table itemTable;
	private Table statsTable;
	private Label statsLabel;
	private Label title;
	private Label statsTitle;
	private Button pauseButton;
	private Button muteButton;
	private Button backButton;
	private ScrollPane scrollPane;
	private Slider volumeSlider;
	private Dialog pauseDialog;
	private Dialog volumeDialog;
	
	private final int BUTTON_WIDTH = 60;
	
	public InventoryScreen(final AdventureGame game) {
		this.game = game;
		
		initialize();
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
	}
	
	// Initialize slide elements
	private void initialize() {
		game.stage.clear();
		
		// Add actors
		game.bgImg = new Image(new Texture(Gdx.files.internal("slideImages/facts.png")));
		game.bgImg.setPosition(0, 0);
		game.bgImg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.stage.addActor(game.bgImg);
		
		createPauseDialog();

		Image pauseImg = new Image(new Texture(Gdx.files.internal("art/icons/pauseSMALL.png")));
		pauseButton = new Button(game.skin);
		pauseButton.add(pauseImg);
		pauseButton.setWidth(SlideScreen.BUTTON_WIDTH);
		pauseButton.setHeight(SlideScreen.BUTTON_WIDTH);
							  //Gdx.graphics.getWidth() - pauseButton.getWidth() - 10
		pauseButton.setPosition(10,	Gdx.graphics.getHeight() - BUTTON_WIDTH - 10);
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseDialog.setVisible(true);
			}
		});
		game.stage.addActor(pauseButton);
		
		// -------------------- volume dialog & slider ------------------

		// slider
		volumeSlider = new Slider(0f, 1f, 0.1f, false, game.skin);
		volumeSlider.setValue(game.bgMusic.getVolume());
		volumeSlider.addListener(new EventListener() {
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
		// 880
		volumeDialog.setPosition(90, 530);

		updateMute();

		backButton = new Button(game.skin);
		backButton.add(new Image(new Texture(Gdx.files.internal("art/icons/backSMALL.png"))));
		backButton.setWidth(SlideScreen.BUTTON_WIDTH);
		backButton.setHeight(SlideScreen.BUTTON_WIDTH);
							  //Gdx.graphics.getWidth() - backButton.getWidth() - 10
		backButton.setPosition(10,	Gdx.graphics.getHeight() - 2 * BUTTON_WIDTH - 10);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SlideScreen(game));
			}
		});
		game.stage.addActor(backButton);
		
		// initialize slide contents
		createTitle();
		createItemTable();
		createPlayerStats();
		
		createTable();
		
		game.stage.addActor(title);
		game.stage.addActor(table);
		game.stage.addActor(scrollPane);
		game.stage.addActor(statsTable);
		game.stage.addActor(volumeDialog);
		game.stage.addActor(pauseDialog);
		
	}

	private void createPauseDialog() {

		// dialog
		pauseDialog = new Dialog("", game.skin);
		pauseDialog.setVisible(false);
		pauseDialog.align(Align.top);
		pauseDialog.row();
		pauseDialog.add(new Label("PAUSE", game.skin, "title")).align(Align.center);
		pauseDialog.row();
		
		// resume button
		TextButton resumeButton = new TextButton("Resume", game.skin);
		resumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseDialog.setVisible(false);
			}
		});
		pauseDialog.add(resumeButton).pad(0, 10, 0, 0).width(275).align(Align.left).row();
		
		// main menu button
		TextButton mainMenuButton = new TextButton("Main Menu", game.skin);
		mainMenuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new MainMenuScreen(game));
			}
		});
		mainMenuButton.setWidth(290);
		pauseDialog.add(mainMenuButton).pad(0, 10, 20, 0).width(275).align(Align.left).row();
		
		pauseDialog.setWidth(300);
		pauseDialog.setHeight(200);
		
		pauseDialog.setPosition((Gdx.graphics.getWidth() - pauseDialog.getWidth()) / 2,
				(Gdx.graphics.getHeight() - pauseDialog.getHeight()) / 2);
	}

	private void createTitle() {
		title = new Label("Inventory", game.skin, "title");
		title.setWidth(350);
		title.pack();
		title.setWidth(350);
		title.setPosition(110, Gdx.graphics.getHeight() - title.getHeight() - 20);
		title.setAlignment(Align.left);
	}
	
	private void createItemTable() {
		
		int gameTextWidth = 700;
		
		itemTable = new Table();
		itemTable.setWidth(gameTextWidth);
		itemTable.align(Align.topLeft);
		
		int itemAdded = 0;

		for (Item item : game.data.getCurrentVisibleItems()) {
			Image itemImage = new Image(new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
			Label itemLabel = new Label(item.getItemQty() + "x " + item.getItemName(), game.skin);
			itemLabel.setAlignment(Align.topLeft);
			itemTable.add(itemImage);
			itemTable.add(itemLabel).align(Align.left);
			if (itemAdded % 2 == 0)
				itemTable.add().pad(20);
			else
				itemTable.row().padTop(20);
			itemAdded++;
		}
		
		if (itemAdded == 0) {
			itemTable.add(new Label("You have no items in your inventory.", game.skin));
		}
		
		createScrollPane(gameTextWidth);
	}
	
	private Image muteImg = new Image(new Texture(Gdx.files.internal("art/icons/muteSMALL.png")));
	private Image unmuteImg = new Image(new Texture(Gdx.files.internal("art/icons/unmuteSMALL.png")));
	
	private void updateMute() {
		muteButton = new Button(game.skin);
		muteButton.setWidth(BUTTON_WIDTH);
		muteButton.setHeight(BUTTON_WIDTH);
							 //Gdx.graphics.getWidth() - BUTTON_WIDTH - 10
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
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(itemTable, game.skin);
		
		int gameTextHeight = 550;
		
	    scrollPane.setBounds(120, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	    scrollPane.setFadeScrollBars(false);
	}
	
	private void createPlayerStats() {
		
		
		statsTitle = new Label("Player Status", game.skin, "title");
		statsTitle.setAlignment(Align.center);
		
		statsLabel = new Label("Name: " + game.data.getPlayer().getName()
				+ "\nGender: " + game.data.getPlayer().getGender().toString(), game.skin);
		statsLabel.setAlignment(Align.topLeft);

		statsTable = new Table();
		statsTable.setWidth(Gdx.graphics.getWidth());
		statsTable.align(Align.topLeft);
		statsTable.setPosition(0, game.stage.getHeight());
		statsTable.padTop(20);
		statsTable.padLeft(760);

		Image avatar;
		if (game.data.getPlayer().getGender() == Gender.UNKNOWN) 
			avatar = new Image(new Texture(Gdx.files.internal("art/icons/unknown.jpg")));
		else if (game.data.getPlayer().getGender() == Gender.MALE) 
			avatar = new Image(new Texture(Gdx.files.internal("art/icons/male.jpg")));
		else
			avatar = new Image(new Texture(Gdx.files.internal("art/icons/female.jpg")));
		
		statsTable.add(statsTitle).width(260);
		statsTable.row();
		statsTable.add(avatar).size(150, 150).padTop(20);
		statsTable.row();
		statsTable.add(statsLabel).padTop(20);

	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.align(Align.topLeft);
		table.setPosition(0, game.stage.getHeight());

		int tableHeight = 600;
		table.padTop(tableHeight + title.getHeight());
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
	public void dispose () {
	}

}

