package edu.augustana.csc285.gamebuilder;
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
	
	public void setTitle(String title){
		
	}

	public void changeTitle(String title) {
		data.getSlide(currentSlideIndex).setTitle(title);
		
	}
	
	public void setGameText(String text){
		data.getSlide(currentSlideIndex).setGameText(text);
	}
}
