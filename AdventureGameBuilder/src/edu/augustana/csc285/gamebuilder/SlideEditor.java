package edu.augustana.csc285.gamebuilder;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import edu.augustana.csc285.game.datamodel.*;

public class SlideEditor {

	private int currentSlideIndex;
	private GameData data;
	
	//constructor
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
	//changes the title of the slide to the passed in string
	public void changeTitle(String title) {
		data.getSlide(currentSlideIndex).setTitle(title);
	}

	//changes the game text to the passed in string
	public void setGameText(String text) {
		data.getSlide(currentSlideIndex).setGameText(text);
	}
	//adds an action choice to the end of the list
	public void addActionChoice() {
		data.getSlide(currentSlideIndex).addActionChoice();

	}
	
	public void setSlideType(SlideType slideType) {
		data.getSlide(currentSlideIndex).setSlideType(slideType);
	}

	// https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
	// as a reference
	public void setSlideImage(File slideImageIn) throws IOException {

		String path = "assets/slideImages/"+ slideImageIn.getName();
		Files.copy(slideImageIn.toPath(), (new File(path)).toPath(), StandardCopyOption.REPLACE_EXISTING);		
		data.getSlide(currentSlideIndex).setImageFileName(path);
	}

	//checks to see if a slide was selected and returns true if one has been selected and 
	//false if one hasn't been selected
	public boolean wasSlideSelected() {
		if (currentSlideIndex == -1) {
			return false;
		} else {
			return true;
		}
	}
	//removes a slide from the slide list by calling on the GameData class's removeSlide method
	public void removeSlide() {
		data.removeSlide(currentSlideIndex);
		setCurrentSlide(-1);
	}
}
