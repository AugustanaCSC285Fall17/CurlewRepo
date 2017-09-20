package edu.augustana.csc285.game.datamodel;

public class ActionChoice {
	private String choiceText = "";
	private int destinationSlideIndex = -1;

	public ActionChoice() {	
	}
	
	public String getChoiceText() {
		return choiceText;
	}

	public void setChoiceText(String choiceText) {
		this.choiceText = choiceText;
	}

	public int getDestinationSlideIndex() {
		return destinationSlideIndex;
	}

	public void setDestinationSlideIndex(int destinationSlideIndex) {
		this.destinationSlideIndex = destinationSlideIndex;
	}

	public ActionChoice(String choiceText, int destinationSlideIndex) {
		this.choiceText = choiceText;
		this.destinationSlideIndex = destinationSlideIndex;
	}
	
	public String toString(){
		return "The choice text is: " + choiceText + "and the destinationSlideIndex is " + destinationSlideIndex;
	}
	
}
