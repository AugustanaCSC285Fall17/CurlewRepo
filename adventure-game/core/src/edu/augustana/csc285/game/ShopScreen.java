	
package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.Item;

public class ShopScreen implements Screen {
	
	private AdventureGame game;
	private int backIndex;
	
	private Table table;
	private Table itemTable = new Table();
	private Table shopTable = new Table();
	
	private Label itemTitle;
	private Label shopTitle;
	private Label cashLabel;
	private Button backButton;
	private ScrollPane itemScrollPane;
	private ScrollPane shopScrollPane;
	
	public ShopScreen(final AdventureGame game, int backIndex) {
		this.game = game;
		this.backIndex = backIndex;
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
		game.bgImg.setSize(AdventureGame.GAME_SCREEN_WIDTH, AdventureGame.GAME_SCREEN_HEIGHT);
		game.stage.addActor(game.bgImg);
		
		
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
		game.stage.addActor(SlideScreen.restartButton);
		game.stage.addActor(SlideScreen.muteButton);
		game.stage.addActor(SlideScreen.creditButton);
		game.stage.addActor(SlideScreen.fontButton);
		game.stage.addActor(SlideScreen.volumeDialog);
		game.stage.addActor(SlideScreen.restartDialog);
		game.stage.addActor(SlideScreen.fontDialog);
		game.stage.addActor(SlideScreen.volumeDialog);
		
	}

	private void createTitle() {
		itemTitle = new Label("Inventory", game.skin, "title");
		itemTitle.setWidth(350);
		itemTitle.pack();
		itemTitle.setWidth(350);
		itemTitle.setPosition(90, AdventureGame.GAME_SCREEN_HEIGHT - itemTitle.getHeight() - 20);
		itemTitle.setAlignment(Align.left);
	}

	private int gameTextWidth = 500;
	
	private void createItemTable() {
		Label categoryLabel = new Label("Item Name              Quantity     Value", game.skin);
		categoryLabel.setPosition(160, AdventureGame.GAME_SCREEN_HEIGHT - itemTitle.getHeight() - 60);
		game.stage.addActor(categoryLabel);
		
		redrawItemTable();		
		
//		if (itemAdded == 0) {
//			itemTable.add(new Label("You have no items in your inventory.", game.skin));
//		}
		
	}
	
	private void createScrollPanes(int gameTextWidth) {
		itemScrollPane = new ScrollPane(itemTable, game.skin);
		
		int gameTextHeight = 300;
		
	    itemScrollPane.setBounds(100, AdventureGame.GAME_SCREEN_HEIGHT - itemTitle.getHeight() - 60 - gameTextHeight, gameTextWidth, gameTextHeight);
	    itemScrollPane.layout();
	    itemScrollPane.setTouchable(Touchable.enabled);
	    itemScrollPane.setFadeScrollBars(false);
	    
		shopScrollPane = new ScrollPane(shopTable, game.skin);
		
		shopScrollPane.setBounds(760, AdventureGame.GAME_SCREEN_HEIGHT - itemTitle.getHeight() - 60 - gameTextHeight, gameTextWidth, gameTextHeight);
		shopScrollPane.layout();
		shopScrollPane.setTouchable(Touchable.enabled);
		shopScrollPane.setFadeScrollBars(false);
	}
	
	private void createShopTable() {
		shopTitle = new Label("Shop", game.skin, "title");
		shopTitle.setWidth(350);
		shopTitle.pack();
		shopTitle.setWidth(350);
		shopTitle.setPosition(730, AdventureGame.GAME_SCREEN_HEIGHT - itemTitle.getHeight() - 20);
		shopTitle.setAlignment(Align.left);
		
		Label categoryLabel = new Label("Item Name                    Price", game.skin);
		categoryLabel.setPosition(870, AdventureGame.GAME_SCREEN_HEIGHT - itemTitle.getHeight() - 60);
		game.stage.addActor(categoryLabel);

		
		shopTable.setWidth(gameTextWidth);
		itemTable.setTouchable(Touchable.enabled);
		shopTable.align(Align.topLeft);

		for (Item item : game.data.getPlayer().getInventory()) {
			if (item.canBuy()) {
				Table itemRow = getItemRow(item, true);

				DragAndDrop dnd = new DragAndDrop();
				dnd.addSource(new Source(itemRow) {
					Payload payload = new Payload();

					@Override
					public Payload dragStart(InputEvent event, float x, float y, int pointer) {
						Image itemImage = new Image(
								new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
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
						if (game.data.getPlayer().getItemQuantity("Kronor") < item.getBuyPrice()) {
							Dialog rejectDialog = new Dialog("", game.skin);
							rejectDialog.button("Ok");
							rejectDialog.text("Insufficient funds!");
							rejectDialog.setWidth(700);
							rejectDialog.setPosition(300, 300);
							game.stage.addActor(rejectDialog);
						} else {
							game.data.getPlayer().incrementAvailableItem("Kronor", -item.getBuyPrice());
							game.data.getPlayer().incrementAvailableItem(item.getItemName(), 1);
							redrawItemTable();
							showDialog(item, false);
						}
					}
				});
				shopTable.add(itemRow).fill();
				shopTable.row().padTop(10);
			}
		}
	}
	
	private void createBackButton() {
		backButton = new Button(game.skin);
		backButton.add(new Image(new Texture(Gdx.files.internal("art/icons/backSMALL.png"))));
		backButton.setWidth(SlideScreen.BUTTON_SIZE);
		backButton.setHeight(SlideScreen.BUTTON_SIZE);
							  //AdventureGame.GAME_SCREEN_WIDTH - backButton.getWidth() - 10
		backButton.setPosition(10,	AdventureGame.GAME_SCREEN_HEIGHT - 2 * SlideScreen.BUTTON_SIZE - 10);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.data.attemptChoice(new ActionChoice("", backIndex));
				game.setScreen(new SlideScreen(game));
			}
		});
		game.stage.addActor(backButton);
	}
	
	private void createTable() {
		table = new Table();
		table.setWidth(AdventureGame.GAME_SCREEN_WIDTH);
		table.align(Align.topLeft);
		table.setPosition(0, game.stage.getHeight());
		table.padLeft(40);
		cashLabel = new Label("Kronor: " + game.data.getPlayer().getItemQuantity("Kronor"), game.skin);
		
		Label direction = new Label("Direction: Drag items from your inventory to the shop to sell and from the shop to your inventory to buy them.", game.skin);
		table.add(cashLabel).align(Align.left);
		table.row();
		table.add(direction);

		int tableHeight = 400;
		table.padTop(tableHeight + itemTitle.getHeight());
	}
	
	private Table getItemRow(Item item, boolean isShop) {
		Table itemRow = new Table(game.skin);
		Image itemImage = new Image(new Texture(Gdx.files.internal("art/icons/" + item.getImageAddress())));
		Label itemLabel = new Label(item.getItemName(), game.skin);
		int price = item.getSellPrice();
		if (isShop) {
			price = item.getBuyPrice();
		} else if (!item.canSell()) {
			price = 0;
		}
		
		Label quantityLabel = new Label("" + item.getItemQty(), game.skin);
		Label valueLabel = new Label("" + price, game.skin);
		
		quantityLabel.setAlignment(Align.center);
		valueLabel.setAlignment(Align.center);
		itemRow.add(itemImage).size(80, 80);
		itemRow.add(itemLabel).padLeft(30).size(150, 30).align(Align.left);
		if (!isShop) {
			itemRow.add(quantityLabel).padLeft(55).size(30);
		}
		itemRow.add(valueLabel).padLeft(60).size(30);
		
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
			if (!item.getItemName().equals("Kronor")) {
				Table itemRow = getItemRow(item, false);

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

						if (!item.canSell()) {
							Dialog rejectDialog = new Dialog("", game.skin);
							rejectDialog.button("OK");
							rejectDialog.text("You cannot sell " + item.getItemName() + "!");
							rejectDialog.setWidth(700);
							rejectDialog.setPosition(300, 300);
							game.stage.addActor(rejectDialog);
						} else {
							game.data.getPlayer().incrementAvailableItem("Kronor", item.getSellPrice());
							game.data.getPlayer().incrementAvailableItem(item.getItemName(), -1);
							redrawItemTable();
							showDialog(item, true);
						}
					}
				});
				itemTable.add(itemRow).fill();
				itemTable.row().padTop(10);
			}
		}
	}
	
	private void showDialog(Item item, boolean isSelling) {
		Dialog dialog = new Dialog("", game.skin);
		dialog.setPosition(300, 300);
		dialog.setWidth(700);
		dialog.button("OK");
		String message;
		if (isSelling) {
			message = "You sold " + item.getItemName() + " for " + item.getSellPrice() + " Kronor.";
		} else {
			message = "You bought " + item.getItemName() + " for " + item.getBuyPrice() + " Kronor.";
		}
		dialog.text(new Label(message, game.skin));
		game.stage.addActor(dialog);
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

