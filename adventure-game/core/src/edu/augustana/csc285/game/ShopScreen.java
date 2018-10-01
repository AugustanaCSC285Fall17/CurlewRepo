	
package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
	private static LabelStyle shopTitleStyle;
	
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
		game.bgImg = new Image(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		game.bgImg.setPosition(0, 0);
		game.bgImg.setSize(AdventureGame.SCREEN_WIDTH, AdventureGame.SCREEN_HEIGHT);
		game.stage.addActor(game.bgImg);
		shopTitleStyle = new LabelStyle(new BitmapFont(Gdx.files.internal("fonts/GeorgiaProLight" + (AdventureGame.appFontSize) + ".fnt")), Color.BLACK);
		
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
		game.stage.addActor(SlideScreen.volumeButton);
		game.stage.addActor(SlideScreen.creditButton);
		game.stage.addActor(SlideScreen.fontButton);
		game.stage.addActor(SlideScreen.volumeDialog);
		game.stage.addActor(SlideScreen.restartDialog);
		game.stage.addActor(SlideScreen.fontDialog);
		game.stage.addActor(SlideScreen.zoomButton);
		game.stage.addActor(SlideScreen.zoomDialog);
		game.stage.addActor(SlideScreen.volumeDialog);
		
	}

	private void createTitle() {
		itemTitle = new Label("Inventory", game.skin, "title");
		itemTitle.setWidth(AdventureGame.percentWidth(27));
		itemTitle.pack();
		itemTitle.setPosition(AdventureGame.percentWidth(9), AdventureGame.SCREEN_HEIGHT - itemTitle.getHeight() - AdventureGame.percentHeight(3));
		itemTitle.setAlignment(Align.left);
	}

	private float gameTextWidth = AdventureGame.percentWidth(39);
	
	private void createItemTable() {
//		Label categoryLabel = new Label("Item Name              Quantity     Value", game.skin);
//		categoryLabel.setPosition(AdventureGame.percentWidth(13), AdventureGame.SCREEN_HEIGHT - itemTitle.getHeight() - AdventureGame.percentHeight(8));
//		game.stage.addActor(categoryLabel);

		cashLabel.setText("Kronor: " + game.data.getPlayer().getItemQuantity("Kronor"));
		itemTable.clear();
		itemTable.setWidth(gameTextWidth);
		itemTable.setTouchable(Touchable.enabled);
		itemTable.align(Align.topLeft);
		
		itemTable.add().size(AdventureGame.percentWidth(6));
		itemTable.add(new Label("Item Name", shopTitleStyle)).padLeft(AdventureGame.percentWidth(2)).width(AdventureGame.percentWidth(12)).align(Align.left);
		itemTable.add(new Label("Quantity", shopTitleStyle)).padLeft(AdventureGame.percentWidth(3)).width(AdventureGame.percentWidth(4)).align(Align.center);
		itemTable.add(new Label("Price", shopTitleStyle)).padLeft(AdventureGame.percentWidth(4)).width(AdventureGame.percentWidth(4)).align(Align.center);
		itemTable.row();
		
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
						itemImage.setSize(AdventureGame.percentWidth(6), AdventureGame.percentWidth(6));
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
							rejectDialog.text(new Label("You cannot sell " + item.getItemName() + "!", game.skin));
							rejectDialog.setWidth(AdventureGame.percentWidth(55));
							rejectDialog.setPosition(AdventureGame.percentWidth(23), AdventureGame.percentHeight(43));
							game.stage.addActor(rejectDialog);
						} else {
							game.data.getPlayer().incrementAvailableItem("Kronor", item.getSellPrice());
							game.data.getPlayer().incrementAvailableItem(item.getItemName(), -1);
							createItemTable();
							showDialog(item, true);
						}
					}
				});
				itemTable.add(itemRow).fill().colspan(4);
				itemTable.row().padTop(AdventureGame.percentHeight(1));
			}
		}	
		
//		if (itemAdded == 0) {
//			itemTable.add(new Label("You have no items in your inventory.", game.skin));
//		}
		
	}	
	
	private void createShopTable() {
		shopTitle = new Label("Shop", game.skin, "title");
		shopTitle.setWidth(AdventureGame.percentWidth(27));
		shopTitle.pack();
		shopTitle.setPosition(AdventureGame.percentWidth(57), AdventureGame.SCREEN_HEIGHT - itemTitle.getHeight() - AdventureGame.percentHeight(3));
		shopTitle.setAlignment(Align.left);
//		
//		Label categoryLabel = new Label("Item Name                    Price", game.skin);
//		categoryLabel.setPosition(AdventureGame.percentWidth(68), AdventureGame.SCREEN_HEIGHT - itemTitle.getHeight() - AdventureGame.percentHeight(8));
//		game.stage.addActor(categoryLabel);

		
		shopTable.setWidth(gameTextWidth);
		itemTable.setTouchable(Touchable.enabled);
		shopTable.align(Align.topLeft);
		
		shopTable.add().size(AdventureGame.percentWidth(6));
		shopTable.add(new Label("Item Name", shopTitleStyle)).padLeft(AdventureGame.percentWidth(2)).width(AdventureGame.percentWidth(12)).align(Align.left);
		shopTable.add(new Label("Price", shopTitleStyle)).padLeft(AdventureGame.percentWidth(3)).width(AdventureGame.percentWidth(4)).align(Align.center);
		shopTable.row();
		
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
							rejectDialog.text(new Label("Insufficient funds!", game.skin));
							rejectDialog.setWidth(AdventureGame.percentWidth(55));
							rejectDialog.setPosition(AdventureGame.percentWidth(23), AdventureGame.percentHeight(43));
							game.stage.addActor(rejectDialog);
						} else {
							game.data.getPlayer().incrementAvailableItem("Kronor", -item.getBuyPrice());
							game.data.getPlayer().incrementAvailableItem(item.getItemName(), 1);
							createItemTable();
							showDialog(item, false);
						}
					}
				});
				shopTable.add(itemRow).fill().colspan(3);
				shopTable.row().padTop(AdventureGame.percentHeight(1));
			}
		}
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
		itemRow.add(itemImage).size(AdventureGame.percentWidth(6), AdventureGame.percentWidth(6));
		itemRow.add(itemLabel).padLeft(AdventureGame.percentWidth(2)).size(AdventureGame.percentWidth(12), AdventureGame.percentHeight(4)).align(Align.left);
		if (!isShop) {
			itemRow.add(quantityLabel).padLeft(AdventureGame.percentWidth(5)).size(AdventureGame.percentWidth(2));
		}
		itemRow.add(valueLabel).padLeft(AdventureGame.percentWidth(5)).size(AdventureGame.percentWidth(2));
		
		return itemRow;
	}
	
	private void createScrollPanes(float gameTextWidth2) {
		itemScrollPane = new ScrollPane(itemTable, game.skin);
		
		float gameTextHeight = AdventureGame.percentHeight(50);
		
	    itemScrollPane.setBounds(AdventureGame.percentWidth(9), AdventureGame.SCREEN_HEIGHT - itemTitle.getHeight() - AdventureGame.percentHeight(3) - gameTextHeight, gameTextWidth2, gameTextHeight);
	    itemScrollPane.layout();
	    itemScrollPane.setTouchable(Touchable.enabled);
	    itemScrollPane.setFadeScrollBars(false);
	    
		shopScrollPane = new ScrollPane(shopTable, game.skin);
		
		shopScrollPane.setBounds(AdventureGame.percentWidth(59), AdventureGame.SCREEN_HEIGHT - itemTitle.getHeight() - AdventureGame.percentHeight(3) - gameTextHeight, gameTextWidth2, gameTextHeight);
		shopScrollPane.layout();
		shopScrollPane.setTouchable(Touchable.enabled);
		shopScrollPane.setFadeScrollBars(false);
	}
	

	
	private void createBackButton() {
		backButton = new Button(game.skin);
		Texture backTex = new Texture(Gdx.files.internal("art/icons/backSMALL.png"));
		backTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		backButton.add(new Image(backTex));
		backButton.setWidth(SlideScreen.BUTTON_SIZE);
		backButton.setHeight(SlideScreen.BUTTON_SIZE);
							  //AdventureGame.GAME_SCREEN_WIDTH - backButton.getWidth() - 10
		backButton.setPosition(10,	AdventureGame.SCREEN_HEIGHT - 2 * SlideScreen.BUTTON_SIZE - AdventureGame.percentHeight(1));
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
		table.setWidth(AdventureGame.SCREEN_WIDTH);
		table.align(Align.topLeft);
		table.setPosition(AdventureGame.percentWidth(9), AdventureGame.SCREEN_HEIGHT);
		cashLabel = new Label("Kronor: " + game.data.getPlayer().getItemQuantity("Kronor"), game.skin);
		
		Label direction = new Label("Direction: Drag items from your inventory to the shop to sell and from the shop to your inventory to buy.", game.skin);
		table.add(cashLabel).align(Align.left);
		table.row();
		table.add(direction);

		float tableHeight = AdventureGame.percentHeight(56);
		table.padTop(tableHeight + itemTitle.getHeight());
	}
	
	private void showDialog(Item item, boolean isSelling) {
		Dialog dialog = new Dialog("", game.skin);
		dialog.setPosition(AdventureGame.percentWidth(23), AdventureGame.percentHeight(42));
		dialog.setWidth(AdventureGame.percentWidth(55));
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

