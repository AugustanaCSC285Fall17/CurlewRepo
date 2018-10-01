package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.augustana.csc285.game.datamodel.SlideType;

public class CreditsScreen implements Screen {

	private final AdventureGame game;
	
	private TextButton backButton;
	private Label introText;
	private int backIndex;
	
	public CreditsScreen(final AdventureGame game) {
		this.game = game; 
		backIndex = -1;
		

		drawBackgroundAndLogo();
		initializeAbout();
	}
	
	public CreditsScreen(final AdventureGame game, int backIndex) {
		this(game);
		this.backIndex = backIndex;
	}

	private void initializeAbout() {
		Label credit = new Label("Credits", game.skin, "title");
		credit.setPosition(AdventureGame.percentWidth(3), AdventureGame.percentHeight(88));
		game.stage.addActor(credit);
		
		backButton = new TextButton("Back", game.skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.data.gameStarted()) {
					game.stage.clear();
					if (game.data.getCurrentSlideType() == SlideType.SHOP) {
						game.data.setCurrentSlideIndex(21);
					}
					game.setScreen(new SlideScreen(game));
				} else {
					game.stage.clear();
					game.setScreen(new MainMenuScreen(game));
				}
			}
		});
		backButton.setWidth(AdventureGame.percentWidth(23));
		backButton.setPosition(AdventureGame.percentWidth(40), AdventureGame.percentHeight(18));
		game.stage.addActor(backButton);
		
		String intro =
				"Game designed by Dr. Forrest Stonedahl's Software Development CSC 285 students and \n"
				+ "Dr. Brian Leech's history students Abigail Buchanan, Brooks Fiedler, and Katie Laschanzky \n"
				+ "for the Swenson Swedish Immigration Research Center at Augustana College in \n"
				+ "Rock Island, Illinois.\n\n"
				+ "Coding by Team Curlew: Jack Cannell, Steve Jia, Minh Ta, and Maxwell McComb\n\n"
				+ "QA Testers:\n\n"
				+ "     Samuel Totten\n"
				+ "     Angelica Garcia\n" 
				+ "     Brandon Thompson\n" 
				+ "     Brian Hinsberger\n" 
				+ "     Christian Elliott\n" 
				+ "     Daniel Tenorio\n" 
				+ "     Daniel Zwiener\n" 
				+ "     Dat Tran\n" 
				+ "     Faisal Nawaz\n" 
				+ "     Liem Gearen\n" 
				+ "     Dylan Hart\n" 
				+ "     Hunter Bader\n"
				+ "     Jonathan Meir\n" 
				+ "     Larsen Harvey\n" 
				+ "     Trung Le\n" 
				+ "     Maegan Patterson\n"
				+ "     Paige Oucheriah\n" 
				+ "     Phoenix, Kevin\n" 
				+ "     Ryan Philp\n" 
				+ "     Scott Daluga\n" 
				+ "     Samih Sghier\n" 
				+ "     Vecna (Braden Isbell)\n"
				+ "     Zineb Zirari\n"
				+ "     Thinh Nguyen\n"
				+ "     Neo Hoang\n"
				+ "\nImages:\n\n"
				+ "     Images in the game are from the Swenson Center's library and archival collections.\n"
				+ "     Other images:\n"
				+ "     Kronor:\n" 
				+ "          http://www.myntkabinettet.se/fakta/galleri/mynt_1/nyare_tid/oskar_ii_1/oskar_ii_krona_guld\n" 
				+ "          Author: Royal Coin Cabinet Museum, Sweden\n"
				+ "     Old paper:\n"
				+ "          https://ftourini.deviantart.com/art/old-paper-stock-02-256716612\n" 
				+ "          Author: ftourini\n"
				+ "     Bible:\n"
				+ "          http://www.publicdomainpictures.net/view-image.php?image=60580&picture=bible-and-cross\n" 
				+ "          Author: George Hodan\n" 
				+ "     Official Paper:\n"
				+ "          https://rocketdock.com/addon/icons/42880\n" 
				+ "          http://lightquick.co.uk/steampunk-widgets.html?Itemid=264\n" 
				+ "          Author: yereverluvinunclebert\n"
				+ "     Heirloom:\n"
				+ "          https://www.pexels.com/photo/antique-brass-classic-clock-295885/\n" 
				+ "          Author: David Bartus\n"
				+ "     Sewing:\n"
				+ "          http://www.publicdomainpictures.net/view-image.php?image=172488&picture=sewing-background\n" 
				+ "          Author: George Hodan\n"
				+ "     Medicine:\n"
				+ "          https://raduluchian.deviantart.com/art/Small-glass-bottle-PNG-262365240\n" 
				+ "          Author: raduluchian\n"
				+ "     Cured Meat:\n"
				+ "          http://all-free-download.com/free-vector/download/meat-03_115454.html\n" 
				+ "          Author: ArtFavor\n" 
				+ "     Ticket:\n"
				+ "          http://www.libraryofbirmingham.com/\n" 
				+ "          Author: Library of Birmingham\n\n"
				+ "\nMusic:\n\n"
				+ "     Triumphant Return by Audionautix is licensed under a Creative Commons Attribution license\n"
				+ "     (https://creativecommons.org/licenses/by/4.0/)\n" 
				+ "     Artist: http://audionautix.com/";
		
		introText = new Label(intro, game.skin); 
		introText.setWrap(true);
		introText.setWidth(AdventureGame.percentWidth(92));
		introText.pack();
		introText.setAlignment(Align.topLeft);
		
		ScrollPane scrollPane = new ScrollPane(introText, game.skin);
	    scrollPane.setBounds(AdventureGame.percentWidth(3), AdventureGame.percentHeight(28), AdventureGame.percentWidth(92), AdventureGame.percentHeight(60));
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	    scrollPane.setFadeScrollBars(false);
	    

		game.stage.setScrollFocus(scrollPane);
		game.stage.addActor(scrollPane);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
		
	}
	
	private void drawBackgroundAndLogo() {
		game.bgImg = new Image(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		game.bgImg.setSize(AdventureGame.SCREEN_WIDTH, AdventureGame.SCREEN_HEIGHT);
//		
//		int logoWidth = 500;
//		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
//		game.swansonLogo = new Image(swansonLogoTexture);
//		game.swansonLogo.setPosition(40, 10);
//		game.swansonLogo.setSize(logoWidth, (float) (swansonLogoTexture.getHeight() * logoWidth * 1.0 / swansonLogoTexture.getWidth()));
		game.stage.addActor(game.bgImg);
		game.stage.addActor(game.swansonLogo);
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

	
