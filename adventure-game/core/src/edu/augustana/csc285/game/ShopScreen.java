	
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
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.Item;

public class ShopScreen implements Screen {
	
	private AdventureGame game;
	
	private Table table;
	private Table itemTable = new Table();
	private Table shopTable = new Table();
	
	private Label itemTitle;
	private Label shopTitle;
	private Label cashLabel;
	private Button pauseButton;
	private TextButton backButton;
	private ScrollPane itemScrollPane;
	private ScrollPane shopScrollPane;
	
	public ShopScreen(final AdventureGame game) {
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
		createBackButton();
		createTable();
		createScrollPanes(gameTextWidth);
		createItemTable();
		createShopTable();
		
	    
		
		// Add actors
		game.stage.addActor(table);
		game.stage.addActor(shopTitle);
		game.stage.addActor(shopScrollPane);
		game.stage.addActor(itemTitle);
		game.stage.addActor(itemScrollPane);
		
		// Set the background
		float size = Gdx.graphics.getHeight();
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/facts.png")));
		game.sprite.setPosition(Gdx.graphics.getWidth() - size, 0);
		game.sprite.setSize(size, size);
	}

	private void createTitle() {
		itemTitle = new Label("Inventory", game.skin, "title");
		itemTitle.setWidth(350);
		itemTitle.pack();
		itemTitle.setWidth(350);
		itemTitle.setPosition(40, Gdx.graphics.getHeight() - itemTitle.getHeight() - 20);
		itemTitle.setAlignment(Align.left);
	}

	private int gameTextWidth = 500;
	
	private void createItemTable() {
		Label categoryLabel = new Label("Item Name              Quantity     Value", game.skin);
		categoryLabel.setPosition(160, Gdx.graphics.getHeight() - itemTitle.getHeight() - 60);
		game.stage.addActor(categoryLabel);
		
		redrawItemTable();		
		
//		if (itemAdded == 0) {
//			itemTable.add(new Label("You have no items in your inventory.", game.skin));
//		}
		
	}
	
	private void createScrollPanes(int gameTextWidth) {
		itemScrollPane = new ScrollPane(itemTable, game.skin);
		
		int gameTextHeight = 300;
		
	    itemScrollPane.setBounds(50, Gdx.graphics.getHeight() - itemTitle.getHeight() - 60 - gameTextHeight, gameTextWidth, gameTextHeight);
	    itemScrollPane.layout();
	    itemScrollPane.setTouchable(Touchable.enabled);
	    itemScrollPane.setFadeScrollBars(false);
	    
		shopScrollPane = new ScrollPane(shopTable, game.skin);
		
		shopScrollPane.setBounds(760, Gdx.graphics.getHeight() - itemTitle.getHeight() - 60 - gameTextHeight, gameTextWidth, gameTextHeight);
		shopScrollPane.layout();
		shopScrollPane.setTouchable(Touchable.enabled);
		shopScrollPane.setFadeScrollBars(false);
	}
	
	private void createShopTable() {
		shopTitle = new Label("Shop", game.skin, "title");
		shopTitle.setWidth(350);
		shopTitle.pack();
		shopTitle.setWidth(350);
		shopTitle.setPosition(730, Gdx.graphics.getHeight() - itemTitle.getHeight() - 20);
		shopTitle.setAlignment(Align.left);
		
		Label categoryLabel = new Label("Item Name                    Price", game.skin);
		categoryLabel.setPosition(870, Gdx.graphics.getHeight() - itemTitle.getHeight() - 60);
		game.stage.addActor(categoryLabel);

		
		shopTable.setWidth(gameTextWidth);
		itemTable.setTouchable(Touchable.enabled);
		shopTable.align(Align.topLeft);

		for (Item item : game.data.getPlayer().getInventory()) {
			if (item.canBuy()) {
				Table itemRow = getAnItemRow(item, true);

				DragAndDrop dnd = new DragAndDrop();
				dnd.addSource(new Source(itemRow) {
					Payload payload = new Payload();

					@Override
					public Payload dragStart(InputEvent event, float x, float y, int pointer) {
						Image itemImage = new Image(
								new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
						itemImage.setSize(80, 80);
						payload.setObject(itemRow);
						payload.setDragActor(itemImage);
						return payload;
					}
				});

				dnd.addTarget(new Target(itemScrollPane) {
					@Override
					public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
						return true;
					}

					@Override
					public void drop(Source source, Payload payload, float x, float y, int pointer) {
						game.data.getPlayer().addInventory("Kronor", -item.getBuyPrice());
						game.data.getPlayer().addInventory(item.getItemName(), 1);
						redrawItemTable();
					}
				});
				shopTable.add(itemRow).fill();
				shopTable.row().padTop(10);
			}
		}

//		if (itemAdded == 0) {
//			shopTable.add(new Label("You have no items in your inventory.", game.skin));
//		}

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
		cashLabel = new Label("Kronor: " + game.data.getPlayer().getItemQuantity("Kronor"), game.skin);
		
		Label direction = new Label("Direction: Drag items from your inventory to the shop to sell and from the shop to your inventory to buy them.", game.skin);
		table.add(cashLabel).align(Align.left);
		table.row();
		table.add(direction);
		table.row();
		table.add(backButton).width(260).padTop(5).align(Align.left);

		int tableHeight = 400;
		table.padTop(tableHeight + itemTitle.getHeight());
	}
	
	private Table getAnItemRow(Item item, boolean isShop) {
		Table itemRow = new Table(game.skin);
		Image itemImage = new Image(new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
		Label itemLabel = new Label(item.getItemName(), game.skin);
		int price = item.getSellPrice();
		if (isShop) {
			price = item.getBuyPrice();
		}
		
		Label quantityLabel = new Label("" + item.getItemQty(), game.skin);
		Label valueLabel = new Label("" + price, game.skin);
		itemRow.add(itemImage).size(80, 80);
		itemRow.add(itemLabel).padLeft(30).size(150, 30).align(Align.left);
		if (!isShop) {
			itemRow.add(quantityLabel).padLeft(55).size(30).align(Align.center);
		}
		itemRow.add(valueLabel).padLeft(60).size(30).align(Align.center);
		
		return itemRow;
	}
	
	public void redrawItemTable() {
		cashLabel.setText("Kronor: " + game.data.getPlayer().getItemQuantity("Kronor"));
		itemTable.clear();
		itemTable.setWidth(gameTextWidth);
		itemTable.setTouchable(Touchable.enabled);
		itemTable.align(Align.topLeft);
		
		// ------------------------------------------------------

		for (Item item : game.data.getCurrentVisibleItems()) {
			if (item.canSell()) {
				Table itemRow = getAnItemRow(item, false);

				DragAndDrop dnd = new DragAndDrop();
				dnd.addSource(new Source(itemRow) {
					Payload payload = new Payload();

					@Override
					public Payload dragStart(InputEvent event, float x, float y, int pointer) {
						Image itemImage = new Image(
								new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
						itemImage.setSize(80, 80);
						payload.setObject(itemRow);
						payload.setDragActor(itemImage);
						return payload;
					}
				});
				dnd.addTarget(new Target(shopScrollPane) {
					@Override
					public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
						return true;
					}

					@Override
					public void drop(Source source, Payload payload, float x, float y, int pointer) {
						game.data.getPlayer().addInventory("Kronor", item.getSellPrice());
						game.data.getPlayer().addInventory(item.getItemName(), -1);
						redrawItemTable();
					}
				});
				itemTable.add(itemRow).fill();
				itemTable.row().padTop(10);
			}
		}
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

