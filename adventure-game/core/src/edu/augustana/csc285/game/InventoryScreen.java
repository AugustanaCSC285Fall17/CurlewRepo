package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
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
	private Button backButton;
	private ScrollPane scrollPane;
	
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
		game.bgImg.setSize(AdventureGame.SCREEN_WIDTH, AdventureGame.SCREEN_HEIGHT);
		game.stage.addActor(game.bgImg);



		backButton = new Button(game.skin);
		backButton.add(new Image(new Texture(Gdx.files.internal("art/icons/backSMALL.png"))));
		backButton.setWidth(SlideScreen.BUTTON_SIZE);
		backButton.setHeight(SlideScreen.BUTTON_SIZE);
							  //AdventureGame.GAME_SCREEN_WIDTH - backButton.getWidth() - 10
		backButton.setPosition(AdventureGame.percentWidth(1),	AdventureGame.SCREEN_HEIGHT - 2 * SlideScreen.BUTTON_SIZE - AdventureGame.percentHeight(1));
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SlideScreen(game));
			}
		});
		backButton.addListener(new TextTooltip("Back", SlideScreen.tooltip, game.skin));
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
		game.stage.addActor(SlideScreen.restartButton);
		game.stage.addActor(SlideScreen.volumeButton);
		game.stage.addActor(SlideScreen.creditButton);
		game.stage.addActor(SlideScreen.fontButton);
		game.stage.addActor(SlideScreen.zoomButton);
		game.stage.addActor(SlideScreen.volumeDialog);
		game.stage.addActor(SlideScreen.restartDialog);
		game.stage.addActor(SlideScreen.fontDialog);
		game.stage.addActor(SlideScreen.zoomDialog);
		game.stage.addActor(SlideScreen.volumeDialog);
		
	}

//	private void createPauseDialog() {
//
//		// dialog
//		pauseDialog = new Dialog("", game.skin);
//		pauseDialog.setVisible(false);
//		pauseDialog.align(Align.top);
//		pauseDialog.row();
//		pauseDialog.add(new Label("PAUSE", game.skin, "title")).align(Align.center);
//		pauseDialog.row();
//		
//		// resume button
//		TextButton resumeButton = new TextButton("Resume", game.skin);
//		resumeButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				pauseDialog.setVisible(false);
//			}
//		});
//		pauseDialog.add(resumeButton).pad(0, 10, 0, 0).width(275).align(Align.left).row();
//		
//		// main menu button
//		TextButton mainMenuButton = new TextButton("Main Menu", game.skin);
//		mainMenuButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				game.stage.clear();
//				game.setScreen(new MainMenuScreen(game));
//			}
//		});
//		mainMenuButton.setWidth(290);
//		pauseDialog.add(mainMenuButton).pad(0, 10, 20, 0).width(275).align(Align.left).row();
//		
//		pauseDialog.setWidth(300);
//		pauseDialog.setHeight(200);
//		
//		pauseDialog.setPosition((AdventureGame.GAME_SCREEN_WIDTH - pauseDialog.getWidth()) / 2,
//				(AdventureGame.GAME_SCREEN_HEIGHT - pauseDialog.getHeight()) / 2);
//	}

	private void createTitle() {
		title = new Label("Inventory", game.skin, "title");
		title.setWidth(AdventureGame.percentWidth(27));
		title.pack();
		title.setPosition(AdventureGame.percentWidth(8), AdventureGame.SCREEN_HEIGHT - title.getHeight() - AdventureGame.percentHeight(1));
		title.setAlignment(Align.left);
	}
	
	private void createItemTable() {
		
		float gameTextWidth = AdventureGame.percentWidth(55);
		
		itemTable = new Table();
		itemTable.setWidth(gameTextWidth);
		itemTable.align(Align.topLeft);
		
		int itemAdded = 0;

		for (Item item : game.data.getCurrentVisibleItems()) {
			Image itemImage = new Image(new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
			String itemName = item.getItemQty() + "x " + item.getItemName();
			if (item.getItemName().equals("Dollar")) {
				itemName = "$" + item.getItemQty();
			} else if (item.getItemName().equals("Kronor")) {
				itemName = item.getItemQty() + " kr";
			}
			Label itemLabel = new Label(itemName, game.skin);
			itemLabel.setAlignment(Align.topLeft);
			itemTable.add(itemImage).size(AdventureGame.percentWidth(6), AdventureGame.percentWidth(6));
			itemTable.add(itemLabel).align(Align.left).padLeft(5);
			if (itemAdded % 2 == 0)
				itemTable.add().pad(AdventureGame.percentWidth(1));
			else
				itemTable.row().padTop(AdventureGame.percentHeight(1));
			itemAdded++;
		}
		
		if (itemAdded == 0) {
			Label noItemLabel = new Label("You have no items in your inventory.", game.skin); 
			itemTable.add(noItemLabel);
		}
		
		createScrollPane(gameTextWidth);
	}
	
	private void createScrollPane(float gameTextWidth) {
		scrollPane = new ScrollPane(itemTable, game.skin);
		
		float gameTextHeight = AdventureGame.percentHeight(75);
		
	    scrollPane.setBounds(AdventureGame.percentWidth(8), AdventureGame.SCREEN_HEIGHT - title.getHeight() - AdventureGame.percentHeight(1) - gameTextHeight, gameTextWidth, gameTextHeight);
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
		statsTable.setWidth(AdventureGame.SCREEN_WIDTH);
		statsTable.align(Align.topLeft);
		statsTable.setPosition(0, AdventureGame.SCREEN_HEIGHT);
		statsTable.padTop(AdventureGame.percentHeight(1));
		statsTable.padLeft(AdventureGame.percentWidth(60));

		Image avatar;
		if (game.data.getPlayer().getGender() == Gender.UNKNOWN) 
			avatar = new Image(new Texture(Gdx.files.internal("art/icons/unknown.png")));
		else if (game.data.getPlayer().getGender() == Gender.MALE) 
			avatar = new Image(new Texture(Gdx.files.internal("art/icons/male.png")));
		else
			avatar = new Image(new Texture(Gdx.files.internal("art/icons/female.png")));
		
		statsTable.add(statsTitle).width(AdventureGame.percentWidth(20));
		statsTable.row();
		statsTable.add(avatar).size(AdventureGame.percentWidth(12), AdventureGame.percentWidth(12)).padTop(AdventureGame.percentHeight(1));
		statsTable.row();
		statsTable.add(statsLabel).padTop(AdventureGame.percentHeight(1));

	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(AdventureGame.SCREEN_WIDTH);
		table.align(Align.topLeft);
		table.setPosition(0, AdventureGame.SCREEN_HEIGHT);

		float tableHeight = AdventureGame.percentHeight(83);
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

