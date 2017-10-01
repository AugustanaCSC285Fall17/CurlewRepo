
package edu.augustana.csc285.game;

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

import edu.augustana.csc285.game.datamodel.Player;

public class InventoryScreen implements Screen {
	
	private AdventureGame game;
	private Player player;
	
	private Table table;
	private Table itemTable;
	private Label title;
	private TextButton pauseButton;
	private TextButton backButton;
	private ScrollPane scrollPane;
	
	private int curSlide;
	
	public InventoryScreen(final AdventureGame game, int curSlide, Player player) {
		this.game = game;
		this.curSlide = curSlide;
		
		this.player = player;
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
		
		pauseButton = new TextButton("Pause", game.skin);
		pauseButton.setWidth(100);
		pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 20,
				Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new PauseScreen(game, curSlide, player));
			}
		});
		game.stage.addActor(pauseButton);
		
		
		// initialize slide contents
		createTitle();
		createItemTable();
		
		createBackButton();
		createTable();
	    
		
		// Add actors
		game.stage.addActor(title);
		game.stage.addActor(table);
		game.stage.addActor(scrollPane);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/facts.png")));
		game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		game.sprite.setSize(size, size);
	}

	
	private void createBackButton() {
		backButton = new TextButton("Back", game.skin);
		backButton.getLabel().setWrap(true);
		backButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SlideScreen(game, curSlide, player));
			}
		});
	}
	
	private void createItemTable() {
		itemTable = new Table();
		itemTable.setWidth(550);
		itemTable.align(Align.topLeft);
		
		@SuppressWarnings("rawtypes")
		Set set = player.getInventory().entrySet();
		
		@SuppressWarnings("rawtypes")
		Iterator it = set.iterator();
		
		int itemAdded = 0;
		
		while(it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry)it.next();
			
			if ((int)me.getValue() != 0) {
				Image itemImage = new Image(new Texture(Gdx.files.internal("assets/art/icons/" + me.getKey() + ".png")));
				Label item = new Label((int)me.getValue() + "x " + (String)me.getKey(), game.skin);
				item.setAlignment(Align.topLeft);
				itemTable.add(itemImage).size(80, 80);
				itemTable.add(item).align(Align.left);
				if (itemAdded % 2 == 0)
					itemTable.add().pad(20);
				else
					itemTable.row().padTop(20);
				itemAdded++;
			}
		}
		
		createScrollPane(550);
	}
	
	private void createScrollPane(int gameTextWidth) {
		scrollPane = new ScrollPane(itemTable, game.skin);
		
		int gameTextHeight = 300;
		
	    scrollPane.setBounds(50, Gdx.graphics.getHeight() - title.getHeight() - 30 - gameTextHeight, gameTextWidth, gameTextHeight);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	}
	
	private void createTitle() {
		title = new Label("Inventory", game.skin, "title");
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
		
		table.add(backButton).width(260).padTop(5);
		
		int tableHeight = 350;
		table.padTop(tableHeight + title.getHeight());
	}

	public int getCurSlide() {
		return curSlide;
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

