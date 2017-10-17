package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;

public class ActionChoice {// implements Feasible
	private String choiceText = "";
	private int destinationSlideIndex = -1;
	private boolean visible;
	private String rejText;
	private ArrayList<Condition> feasibilityCond = new ArrayList<>();
	private ArrayList<Condition> visibilityCond = new ArrayList<>();
	private ArrayList<Effect> effectList;

	public ActionChoice() {
		this("", new ArrayList<Feasible>(), new ArrayList<Effect>());
		visible = true;
	}

	public ActionChoice(String choiceText, int destinationSlideIndex) {
		this("", new ArrayList<Feasible>(), new ArrayList<Effect>());
		this.choiceText = choiceText;
		this.destinationSlideIndex = destinationSlideIndex;
	}

	public ActionChoice(String rejText, ArrayList<Feasible> feasibilityReq, ArrayList<Effect> effectList) {
		this.setRejText(rejText);
		this.effectList = effectList;
		visible = true;
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

	public void getReq(int requirement) {

	}

	// test if the option is visable and return true or false
	public boolean isVisible() {
		return visible;
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

	// changes the effect name
	public void setEffectName(String newEffectName, int index) {
		effectList.get(index).setEffectName(newEffectName);
	}

	// changes the effect size
	public void setEffectSize(int newEffectSize, int index) {
		effectList.get(index).setEffectSize(newEffectSize);
	}

	// check if the option will be fersible
	public void isFeasible(ArrayList<Feasible> feasibilityReq) {

	}

	public ArrayList<Condition> getFeasibilityCond() {
		return feasibilityCond;
	}

	public ArrayList<Condition> getVisibilityCond() {
		return visibilityCond;
	}

	public String toString() {
		String s = "choiceText: " + choiceText + "\ndestinationSlideIndex: " + destinationSlideIndex;
		for (Effect effect : effectList) {
			s += "\n" + effect.printEffectInfo(); // TODO make look pretty
		}
		return s;
	}

	public String getRejText() {
		return rejText;
	}

	public void setRejText(String rejText) {
		this.rejText = rejText;
	}

	public boolean hasGenderEffect() {
		for(int i = 0; i < effectList.size(); i++){
			if(effectList.get(i) instanceof GenderChangeEffect){
				return true;
			}
		}
		return false;
	}

	public boolean hasItemEffect(Item itemChoice) {
		for(int i = 0; i < effectList.size(); i++){
			if(effectList.get(i) instanceof ItemEffect){
				if(effectList.get(i).equals(new ItemEffect(itemChoice,0))){
				return true;
				}
			}
		}
		return false;
	}

}
