package edu.augustana.csc285.gamebuilder;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.augustana.csc285.game.datamodel.*;

public class SlideEditor {

	private int currentSlideIndex;
	private GameData data;
	
	
	
	public SlideEditor(GameData data) {
		this.currentSlideIndex = -1;
		this.data = data;
	}

	public int getCurrentSlide() {
		return currentSlideIndex;
	}

	public void setCurrentSlide(int currentRoom) {
		this.currentSlideIndex = currentRoom;
	}
	

	public void changeTitle(String title) {
		data.getSlide(currentSlideIndex).setTitle(title);
		
	}
	
	public void setGameText(String text){
		data.getSlide(currentSlideIndex).setGameText(text);
	}
	
	



	public void setSlideImage(File slideImageIn) throws IOException {
		//read in the image
		int imageSize = 600;
		
		System.out.println(1);
		
		BufferedImage inImage = new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_ARGB);
		inImage = ImageIO.read(slideImageIn);
		
		//write the new image
		File slideImageSave = new File("C:/git/CurlewRepo/adventure-game/core/assets/art/Test.jpg");
		
	//	File slideImageSave = new File("Test.jpg");
		System.out.println(2);
		
		ImageIO.write(inImage, ".jpg",slideImageSave );
		
		System.out.println(3);
	}
}
