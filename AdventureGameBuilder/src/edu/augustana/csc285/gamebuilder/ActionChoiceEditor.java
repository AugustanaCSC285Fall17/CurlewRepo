package edu.augustana.csc285.gamebuilder;

import edu.augustana.csc285.game.datamodel.GameData;

public class ActionChoiceEditor {
	public int currentActionChoiceIndex;
	private GameData data;
	private SlideEditor se;

	public ActionChoiceEditor(GameData data, SlideEditor se) {
		currentActionChoiceIndex = -1;
		this.data=data;
		this.se=se;
		
	}

	public int getCurrentActionChoiceIndex() {
		return currentActionChoiceIndex;
	}

	public void setCurrentActionChoiceIndex(int currentActionChoiceIndex) {
		this.currentActionChoiceIndex = currentActionChoiceIndex;
	}
	
	public void setDestinationSlideIndex(int index){
		data.getSlide(se.getCurrentSlide()).getActionChoicesAt(currentActionChoiceIndex).setDestinationSlideIndex(index);
	}
	public void setChoiceText(String text){
		data.getSlide(se.getCurrentSlide()).getActionChoicesAt(currentActionChoiceIndex).setChoiceText(text);
	}
	
	public boolean aceSelected(){
		if(currentActionChoiceIndex==-1){
			return false;
		}else{
			return true;
		}
	}
}
