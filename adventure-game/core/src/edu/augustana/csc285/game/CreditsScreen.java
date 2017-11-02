package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class CreditsScreen implements Screen {

	private final AdventureGame game;
	
	private TextButton backButton;
	private Label introText;
	private Sprite swansonLogo;
	
	public CreditsScreen(final AdventureGame game) {
		this.game = game; 
		

		initializeAbout();
	}
	

	private void initializeAbout() {
		Label credit = new Label("Credits", game.skin, "title");
		credit.setPosition(50, 630);
		game.stage.addActor(credit);
		
		backButton = new TextButton("Back", game.skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.stage.clear();
				game.setScreen(new MainMenuScreen(game));
			}
		});
		backButton.setSize(300, 40);
		backButton.setPosition(500, 130);
		game.stage.addActor(backButton);
		
		String intro = ""
				+ "QA Testers:\n\n"
				+ "     Thinh Nguyen\n"
				+ "     Neo Hoang\n"
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
				+ "\nImages:\n\n"
				+ "     Slide images by Swenson Swedish Immigration Research Center, Augustana College\n"
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
		introText.setWidth(1000);
		introText.setAlignment(Align.topLeft);
		
		ScrollPane scrollPane = new ScrollPane(introText);
	    scrollPane.setBounds(70, 200, 1300, 430);
	    scrollPane.layout();
	    scrollPane.setTouchable(Touchable.enabled);
	    scrollPane.setFadeScrollBars(false);
	    
		game.stage.addActor(scrollPane);
		
		drawBackgroundAndLogo();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.sprite.draw(game.batch);
		swansonLogo.draw(game.batch);
		game.batch.end();
		
		game.stage.act(Gdx.graphics.getDeltaTime());
		game.stage.draw();
		
	}
	
	private void drawBackgroundAndLogo() {
		game.batch = new SpriteBatch();
		game.sprite = new Sprite(new Texture(Gdx.files.internal("slideImages/mainmenu2.jpg")));
		game.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		int logoWidth = 500;
		
		Texture swansonLogoTexture = new Texture(Gdx.files.internal("slideImages/image1.png"));
		swansonLogo = new Sprite(swansonLogoTexture);
		swansonLogo.setPosition(40, 10);
		swansonLogo.setSize(logoWidth, (float) (swansonLogoTexture.getHeight() * logoWidth * 1.0 / swansonLogoTexture.getWidth()));
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
	public void dispose() {
	}
}

	
