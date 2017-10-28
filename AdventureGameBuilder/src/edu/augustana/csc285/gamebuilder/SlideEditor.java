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
	private Slide currentSlide;
	
	//constructor
	public SlideEditor(GameData data) {
		this.currentSlideIndex = -1;
		this.data = data;
		currentSlide = null;
	}
	/**
	 * getter method for the currentSlideIndex
	 * @return the currentSlideIndex
	 */
	public int getCurrentSlide() {
		return currentSlideIndex;
	}
	/**
	 * sets the current slide index equal to the current slide that is passed in
	 * also corrects the currentSlide index of the currentSlide field
	 * @param currentSlideInd the index of the new current slide
	 */
	public void setCurrentSlide(int currentSlideInd) {
		if(currentSlideInd!=-1){
		this.currentSlideIndex = currentSlideInd;
		currentSlide = data.getSlide(currentSlideIndex);
		}else{
			currentSlide = null;
		}
	}
	/**
	 * changes the title of the slide to the passed in string
	 * @param title is the title of the of the slide
	 */
	public void changeTitle(String title) {
		currentSlide.setTitle(title);
	}

	/**
	 * changes the game text to the passed in string
	 * @param text is the info text that is on each slide
	 */
	public void setGameText(String text) {
		currentSlide.setGameText(text);
	}
	/**
	 * adds an action choice to the end of the list
	 */
	public void addActionChoice() {
		currentSlide.addActionChoice();

	}
	/**
	 * sets the slide type equal to the new slide type that is passed in
	 * @param slideType new slideType 
	 */
	public void setSlideType(SlideType slideType) {
		currentSlide.setSlideType(slideType);
	}

	/**
	 * https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
	 *  as a reference
	 * @param slideImageIn is a the background image on the slide, should be a jpg
	 * @throws IOException is an Input or Output error 
	 */
	public void setSlideImage(File slideImageIn) throws IOException {

		String path = "assets/slideImages/"+ slideImageIn.getName();
		Files.copy(slideImageIn.toPath(), (new File(path)).toPath(), StandardCopyOption.REPLACE_EXISTING);		
		//currentSlide.setImageFileName(path);
		currentSlide.setImageFileName(slideImageIn.getName());
	}

	/**
	 * checks to see if a slide was selected and returns true if one has been selected and 
	*false if one hasn't been selected
	 */
	public boolean wasSlideSelected() {
		if (currentSlideIndex == -1) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * removes a slide from the slide list by calling on the GameData class's removeSlide method
	 */
	public void removeSlide() {
		data.removeSlide(currentSlideIndex);
		setCurrentSlide(-1);
	}
	
	public boolean isGameOver(){
		return currentSlide.isGameOver();
	}
	
	public void setGameOver(boolean gameOver){
		currentSlide.setGameOver(gameOver);
	}
}
