package edu.augustana.csc285.gamebuilder;

import edu.augustana.csc285.game.datamodel.GameData;

public class ActionChoiceEditor {
	public int currentActionChoiceIndex;
	private GameData data;
	private SlideEditor se;
	
//constructor accepts a Game Data object and a Slide Editor Object
//and sets the currentActionChoiceIndex equal to -1
	public ActionChoiceEditor(GameData data, SlideEditor se) {
		currentActionChoiceIndex = -1;
		this.data = data;
		this.se = se;

	}

	//getter method for currentActionChoiceIndex returns int
	public int getCurrentActionChoiceIndex() {
		return currentActionChoiceIndex;
	}
	//sets currentActionChoiceIndex to a new index that is passed in as a param
	public void setCurrentActionChoiceIndex(int currentActionChoiceIndex) {
		this.currentActionChoiceIndex = currentActionChoiceIndex;
	}
	//sets the destinationSlideIndex to a new index that is passed in as a param
	public void setDestinationSlideIndex(int index) {
		data.getSlide(se.getCurrentSlide()).getActionChoicesAt(currentActionChoiceIndex)
				.setDestinationSlideIndex(index);
	}
	// sets the choice text of an action choice to a passes in String 
	public void setChoiceText(String text) {
		data.getSlide(se.getCurrentSlide()).getActionChoicesAt(currentActionChoiceIndex).setChoiceText(text);
	}
	//returns true if an action choice has been selected 
	public boolean aceSelected() {
		if (currentActionChoiceIndex == -1) {
			return false;
		} else {
			return true;
		}
	}
	//removes the action choice
	public void remove() {
		data.getSlide(se.getCurrentSlide()).removeAc(currentActionChoiceIndex);
		currentActionChoiceIndex = -1;
	}
}
