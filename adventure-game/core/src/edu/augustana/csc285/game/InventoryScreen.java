	
package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
	private TextButton backButton;
	private ScrollPane scrollPane;
	
	public InventoryScreen(final AdventureGame game) {
		this.game = game;
		
		initialize();
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

		Image pauseImg = new Image(new Texture(Gdx.files.internal("art/icons/pauseSMALL.png")));
		pauseButton = new Button(game.skin);
		pauseButton.add(pauseImg);
		pauseButton.setWidth(SlideScreen.BUTTON_WIDTH);
		pauseButton.setHeight(SlideScreen.BUTTON_WIDTH);
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
		
		
		// initialize slide contents
		createTitle();
		createItemTable();
		createPlayerStats();
		
		createBackButton();
		createTable();
	    
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(table);
		game.stage.addActor(scrollPane);
		game.stage.addActor(statsTable);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/facts.png")));
		game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		game.sprite.setSize(size, size);
	}

	private void createTitle() {
		title = new Label("Inventory", game.skin, "title");
		title.setWidth(350);
		title.pack();
		title.setWidth(350);
		title.setPosition(40, Gdx.graphics.getHeight() - title.getHeight() - 20);
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
			itemTable.add(itemImage).size(80, 80);
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
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(itemTable, game.skin);
		
		int gameTextHeight = 550;
		
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
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

	private void createBackButton() {
		backButton = new TextButton("Back", game.skin);
		backButton.getLabel().setWrap(true);
		backButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SlideScreen(game));
			}
		});
	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.align(Align.topLeft);
		table.setPosition(0, game.stage.getHeight());
		table.padLeft(40);
		table.add(backButton).width(260).padTop(5);

		int tableHeight = 600;
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

