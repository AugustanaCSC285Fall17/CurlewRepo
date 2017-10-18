package edu.augustana.csc285.gamebuilder;

import java.util.ArrayList;
import edu.augustana.csc285.game.datamodel.*;


public class ActionChoiceEditor {
	public int currentActionChoiceIndex;
	private Slide slide;
	private SlideEditor se;
	
//constructor accepts a Game Data object and a Slide Editor Object
//and sets the currentActionChoiceIndex equal to -1
	public ActionChoiceEditor(Slide slide, SlideEditor se) {
		currentActionChoiceIndex = -1;
		this.slide = slide;
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
		slide.getActionChoicesAt(currentActionChoiceIndex)
				.setDestinationSlideIndex(index);
	}
	// sets the choice text of an action choice to a passes in String 
	public void setChoiceText(String text) {
		slide.getActionChoicesAt(currentActionChoiceIndex).setChoiceText(text);
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
		slide.removeAc(currentActionChoiceIndex);
		currentActionChoiceIndex = -1;
	}
	
	public void addItemEffect(Item item, int effectSize){
		slide.getActionChoicesAt(currentActionChoiceIndex).addEffect(new ItemEffect (item,effectSize));
	}
	
	public void removeEffect(Effect effect){
		slide.getActionChoicesAt(currentActionChoiceIndex).removeEffect(effect);
	}
	
	public void changeEffectSize(int effectSize, int index){
		slide.getActionChoicesAt(currentActionChoiceIndex).setEffectSize(effectSize, index);
	}
	
	public void addGenderChangeEffect(Gender gender){
		slide.getActionChoicesAt(currentActionChoiceIndex).addEffect(new GenderChangeEffect(gender));
	}

	public boolean hasGenderEffect() {
		return(slide.getActionChoicesAt(currentActionChoiceIndex).hasGenderEffect());
	}

	public boolean hasItemEffect(Item itemChoice) {
		return slide.getActionChoicesAt(currentActionChoiceIndex).hasItemEffect(itemChoice);
	}

	public boolean hasNameChangeEffect() {
		return slide.getActionChoicesAt(currentActionChoiceIndex).hasNameChangeEffect();
	}

	public void addNameChangeEffect(String name) {
		slide.getActionChoicesAt(currentActionChoiceIndex).addEffect(new NameChangeEffect(name));
		
	}

	public ArrayList<Effect> getEffects() {
		return slide.getActionChoicesAt(currentActionChoiceIndex).getEffect();
	}
}
