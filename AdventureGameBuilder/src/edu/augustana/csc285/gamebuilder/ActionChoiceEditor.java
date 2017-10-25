package edu.augustana.csc285.gamebuilder;

import java.util.ArrayList;
import edu.augustana.csc285.game.datamodel.*;


public class ActionChoiceEditor {
	public int currentActionChoiceIndex;
	private Slide slide;
	private SlideEditor se;
	
/**
 * constructor accepts a Game Data object and a Slide Editor Object 
 * and sets the currentActionChoiceIndex equal to -1
 * @param slide
 * @param se
 */
	public ActionChoiceEditor(Slide slide, SlideEditor se) {
		currentActionChoiceIndex = -1;
		this.slide = slide;
		this.se = se;
	}

	public int getCurrentActionChoiceIndex() {
		return currentActionChoiceIndex;
	}

	public void setCurrentActionChoiceIndex(int currentActionChoiceIndex) {
		this.currentActionChoiceIndex = currentActionChoiceIndex;
	}

	public void setDestinationSlideIndex(int index) {
		slide.getActionChoiceAt(currentActionChoiceIndex)
				.setDestinationSlideIndex(index);
	}

	public void setChoiceText(String text) {
		slide.getActionChoiceAt(currentActionChoiceIndex).setChoiceText(text);
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
		slide.removeAc(currentActionChoiceIndex);
		currentActionChoiceIndex = -1;
	}
	/**
	 * adds an item effect
	 * @param [item]item object that the user wants to add
	 * @param [effectSize] integer size of the effect
	 */
	public void addItemEffect(Item item, int effectSize){
		slide.getActionChoiceAt(currentActionChoiceIndex).addEffect(new ItemEffect (item,effectSize));
	}
	/**
	 * 
	 * @param effectIndex 
	 */
	public void removeEffect(int effectIndex){
		slide.getActionChoiceAt(currentActionChoiceIndex).removeEffect(effectIndex);
	}
	
	public void removeCondition(int conditionIndex, int visOrFeas){
		slide.getActionChoiceAt(currentActionChoiceIndex).removeCondition(conditionIndex, visOrFeas);
	}
	/**
	 * 
	 * @param effectSize new effect size
	 * @param index of the effect
	 */
	public void changeEffectSize(int newEffectSize, int index){
		slide.getActionChoiceAt(currentActionChoiceIndex).setEffectSize(newEffectSize, index);
	}
	
	public void addGenderChangeEffect(Gender gender){
		slide.getActionChoiceAt(currentActionChoiceIndex).addEffect(new GenderChangeEffect(gender));
	}
	/**
	 * 
	 * @return the call of the hasGenderEffect of the current action choice 
	 */
	public boolean hasGenderEffect() {
		return(slide.getActionChoiceAt(currentActionChoiceIndex).hasGenderEffect());
	}
	/**
	 * 
	 * @return the call of the hasItemEffect of the current action choice 
	 */
	public boolean hasItemEffect(Item itemChoice) {
		return slide.getActionChoiceAt(currentActionChoiceIndex).hasItemEffect(itemChoice);
	}
	/**
	 * 
	 * @return the call of the hasNameChangeEffect of the current action choice 
	 */
	public boolean hasNameChangeEffect() {
		return slide.getActionChoiceAt(currentActionChoiceIndex).hasNameChangeEffect();
	}
	/**
	 * creates a new name change effect by passing in a new name 
	 * @param [name] the name that the user wants the current name to change to
	 */
	public void addNameChangeEffect(String name) {
		slide.getActionChoiceAt(currentActionChoiceIndex).addEffect(new NameChangeEffect(name));
		
	}
	/**
	 * 
	 * @return a list of effects of the current action choice
	 */
	public ArrayList<Effect> getEffects() {
		return slide.getActionChoiceAt(currentActionChoiceIndex).getEffect();
	}
	/**
	 * 
	 * @param gender the gender that the user would like the new gender to be
	 * @param conditionType a int condition type
	 */
	public void addGenderCondition(Gender gender, int conditionType) {
		slide.getActionChoiceAt(currentActionChoiceIndex).addCondition(new GenderCondition(gender), conditionType);		
	}
	/**
	 * creates a new item condition
	 * @param item
	 * @param ro
	 * @param choiceSize
	 * @param conditionType
	 */
	public void addItemCondition(Item item, RelationalOperator ro, int choiceSize, int conditionType) {
		slide.getActionChoiceAt(currentActionChoiceIndex).addCondition(new ItemCondition(item, ro, choiceSize), conditionType);	
	}
	/**
	 * 
	 * @return the list of feasibilty conditions for a current action choice
	 */
	public ArrayList<Condition> getFConditions() {
		return slide.getActionChoiceAt(currentActionChoiceIndex).getFeasibilityCond();
	}
	/**
	 * 
	 * @return the list of visibility conditions for a current action choice
	 */
	public ArrayList<Condition> getVConditions() {
		return slide.getActionChoiceAt(currentActionChoiceIndex).getVisibilityCond();
	}
}
