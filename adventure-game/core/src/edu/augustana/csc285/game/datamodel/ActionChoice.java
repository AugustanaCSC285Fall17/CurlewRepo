package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;

public class ActionChoice implements Feasible {
	private String choiceText = "";
	private int destinationSlideIndex = -1;
	private boolean visable;
	private String rejText;
	private ArrayList<Feasible> feasibilityReq;
	private ArrayList<Effect> effectList;

	public ActionChoice() {
	}

	public ActionChoice(String rejText, ArrayList<Feasible> feasibilityReq, ArrayList<Effect> effectList) {
		this.rejText = rejText;
		feasibilityReq = new ArrayList<Feasible>();
		effectList = new ArrayList<Effect>();
		visable = true;

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

	public void getReq(int requirement) {

	}

	// test if the option is visable and return true or false
	public boolean isVisible() {
		return visable;
	}

	// Get the effect that the option cost and return it
	public ArrayList<Effect> getEffect() {
		return effectList;
	}

	// add new effect to the effectList
	public void addEffect(Effect newEffect) {
		effectList.add(newEffect);
	}

	// remove effect
	public void removeEffect(int index) {
		effectList.remove(index);
	}

	// check if the option will be fersible
	public void isFeasible(ArrayList<Feasible> feasibilityReq) {

	}

	public String toString() {
		return "The choice text is: " + choiceText + "and the destinationSlideIndex is " + destinationSlideIndex;
	}

}
