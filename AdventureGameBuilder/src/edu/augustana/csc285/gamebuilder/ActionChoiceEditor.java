package edu.augustana.csc285.gamebuilder;

import java.util.ArrayList;
import edu.augustana.csc285.game.datamodel.*;


public class ActionChoiceEditor {
	public int currentActionChoiceIndex;
	private Slide slide;
	private ActionChoice currentActionChoice;
	
	/**
	 * constructor accepts a Game Data object and a Slide Editor Object 
	 * and sets the currentActionChoiceIndex equal to -1
	 * @param slide the slide that the action choice is under
	 * @param se the SlideEditor object the the action choice editor refers to
	 */
	public ActionChoiceEditor(Slide slide) {
		currentActionChoiceIndex = -1;
		this.slide = slide;
		currentActionChoice = null;
	}

	public int getCurrentActionChoiceIndex() {
		return currentActionChoiceIndex;
	}
	/**
	 * Sets the actionChoiceIndex equal to the new actionChoice index
	 * also updates the currentActionChoice
	 * @param currentActionChoiceIndex the new current action choice index
	 */
	public void setCurrentActionChoiceIndex(int currentActionChoiceIndex) {
		if (currentActionChoiceIndex != -1){
		this.currentActionChoiceIndex = currentActionChoiceIndex;
		currentActionChoice = slide.getActionChoiceAt(currentActionChoiceIndex);
		}
		else{
			currentActionChoice = null;
		}
	}

	public void setDestinationSlideIndex(int index) {
		currentActionChoice
				.setDestinationSlideIndex(index);
	}

	public void setChoiceText(String text) {
		currentActionChoice.setChoiceText(text);
	}
	/**
	 * 
	 * @return true if an action choice was selected
	 */
	public boolean aceSelected() {
		if (currentActionChoiceIndex == -1) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * removes the action choice
	 */
	public void remove() {
		slide.removeActionChoice(currentActionChoiceIndex);
		currentActionChoiceIndex = -1;
	}
	/**
	 * adds an item effect
	 * @param [item]item object that the user wants to add
	 * @param [effectSize] integer size of the effect
	 */
	public void addItemEffect(Item item, int effectSize){
		currentActionChoice.addEffect(new ItemEffect (item,effectSize));
	}
	/**
	 * removes the effect by calling on the remove method in the action choice class
	 * @param effectIndex 
	 */
	public void removeEffect(int effectIndex){
		currentActionChoice.removeEffect(effectIndex);
	}
	
	public void removeCondition(int conditionIndex, int visOrFeas){
		currentActionChoice.removeCondition(conditionIndex, visOrFeas);
	}
	/**
	 * 
	 * @param effectSize new effect size
	 * @param index of the effect
	 */
	public void changeEffectSize(int newEffectSize, int index){
		currentActionChoice.setEffectSize(newEffectSize, index);
	}
	
	public void addGenderChangeEffect(Gender gender){
		currentActionChoice.addEffect(new GenderChangeEffect(gender));
	}
	/**
	 * 
	 * @return the call of the hasGenderEffect of the current action choice 
	 */
	public boolean hasGenderEffect() {
		return(currentActionChoice.hasGenderEffect());
	}
	/**
	 * 
	 * @return the call of the hasItemEffect of the current action choice 
	 */
	public boolean hasItemEffect(Item itemChoice) {
		return currentActionChoice.hasItemEffect(itemChoice);
	}
	/**
	 * 
	 * @return the call of the hasNameChangeEffect of the current action choice 
	 */
	public boolean hasNameChangeEffect() {
		return currentActionChoice.hasNameChangeEffect();
	}
	/**
	 * creates a new name change effect by passing in a new name 
	 * @param [name] the name that the user wants the current name to change to
	 */
	public void addNameChangeEffect(String name) {
		currentActionChoice.addEffect(new NameChangeEffect(name));
		
	}
	/**
	 * 
	 * @return a list of effects of the current action choice
	 */
	public ArrayList<Effect> getEffects() {
		return currentActionChoice.getEffects();
	}
	/**
	 * 
	 * @param gender the gender that the user would like the new gender to be
	 * @param conditionType a int condition type
	 */
	public void addGenderCondition(Gender gender, int conditionType) {
		currentActionChoice.addCondition(new GenderCondition(gender), conditionType);		
	}
	/**
	 * creates a new item condition
	 * @param item
	 * @param ro
	 * @param choiceSize
	 * @param conditionType
	 */
	public void addItemCondition(Item item, RelationalOperator ro, int choiceSize, int conditionType) {
		currentActionChoice.addCondition(new ItemCondition(item, ro, choiceSize), conditionType);	
	}
	/**
	 * 
	 * @return the list of feasibilty conditions for a current action choice
	 */
	public ArrayList<Condition> getFConditions() {
		return currentActionChoice.getFeasibilityCond();
	}
	/**
	 * 
	 * @return the list of visibility conditions for a current action choice
	 */
	public ArrayList<Condition> getVConditions() {
		return currentActionChoice.getVisibilityCond();
	}
}
