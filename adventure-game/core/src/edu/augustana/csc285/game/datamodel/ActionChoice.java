package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;

public class ActionChoice {// implements Feasible
	public static final int FEASIBILITY = 0;
	public static final int VISIBILITY = 1;
	private String choiceText = "";
	private int destinationSlideIndex = -1;
	private boolean visible;
	private String rejText;
	
	private ArrayList<Condition> feasibilityCond = new ArrayList<>();
	private ArrayList<Condition> visibilityCond = new ArrayList<>();
	private ArrayList<Effect> effectList;
	/**
	 * Constructor with no parameters.  calls the constructor with 3 fields
	 */
	public ActionChoice() {
		this("", new ArrayList<Feasible>(), new ArrayList<Effect>());
		visible = true;
	}
	/**
	 * constructor with 2 parameters
	 * @param choiceText
	 * @param destinationSlideIndex
	 */
	public ActionChoice(String choiceText, int destinationSlideIndex) {
		this("", new ArrayList<Feasible>(), new ArrayList<Effect>());
		this.choiceText = choiceText;
		this.destinationSlideIndex = destinationSlideIndex;
	}
	/**
	 * constructor with 3 parameters, is called by the "smaller" constructors
	 * @param rejText 
	 * @param feasibilityReq
	 * @param effectList
	 */
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

	/**
	 *  test if the option is visible and return true or false
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 *  Get the effect list that the option cost and return it
	 * @return the list of effects
	 */
	public ArrayList<Effect> getEffects() {
		return effectList;
	}
	
	/**
	 *Get the item effects that the option cost and return it
	 * @return the list of itemEffects
	 */
	public ArrayList<Effect> getItemEffects() {
		ArrayList<Effect> itemEffects = new ArrayList<>();
		for (Effect e : effectList) {
			if (e instanceof ItemEffect) {
				itemEffects.add(e);
			}
		}
		return itemEffects;
	}

	/**
	 *  add new effect to the effectList
	 * @param newEffect
	 */
	public void addEffect(Effect newEffect) {
		effectList.add(newEffect);
	}

	/**
	 * remove effect
	 * @param index
	 */
	public void removeEffect(int index) {
		effectList.remove(index);
	}

	public void removeEffect(Effect e) {
		effectList.remove(e);
	}
	
	public void removeCondition(int index, int visOrFeas){
		if(visOrFeas== FEASIBILITY){
			feasibilityCond.remove(index);
		}else if(visOrFeas== VISIBILITY){
			this.visibilityCond.remove(index);
		}
	}

	public void removeCondition(Condition condition, int visOrFeas){
		if(visOrFeas== FEASIBILITY){
			feasibilityCond.remove(condition);
		}else if(visOrFeas== VISIBILITY){
			this.visibilityCond.remove(condition);
		}
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

	public String getEffectsString() {
		String s = "You gained:\n";
		for (Effect effect : effectList) {
			if (effect instanceof ItemEffect) {
				ItemEffect itemEffect = (ItemEffect) effect;
				String effectSize = "" + itemEffect.getEffectSize();
				if (itemEffect.getEffectSize() > 0)
					effectSize = "+" + itemEffect.getEffectSize();
				
				s += effectSize + " " + itemEffect.getItem().getItemName() + "\n";
			}
		}
		
		return s;
	}
	
	public String toString() {
		String s = "choiceText: " + choiceText + "\ndestinationSlideIndex: " + destinationSlideIndex;
		for (Effect effect : effectList) {
			s += "\n" + effect.printEffectInfo();
		}
		
		for (Condition condition : feasibilityCond) {
			s += "\nFeasibility " + condition.printEffectInfo(); 
		}
		
		for (Condition condition : this.visibilityCond) {
			s += "\nVisibility " + condition.printEffectInfo(); 
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
		for (int i = 0; i < effectList.size(); i++) {
			if (effectList.get(i) instanceof GenderChangeEffect) {
				return true;
			}
		}
		return false;
	}

	public boolean hasItemEffect(Item itemChoice) {
		for (int i = 0; i < effectList.size(); i++) {
			if (effectList.get(i) instanceof ItemEffect) {
				ItemEffect ie = (ItemEffect) effectList.get(i);
				if (ie.getItem().equals(itemChoice)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasItemConditonF(Item item){
		for (int i = 0; i < feasibilityCond.size(); i++) {
			if (feasibilityCond.get(i) instanceof ItemCondition) {
				ItemCondition ie = (ItemCondition) feasibilityCond.get(i);
				if (ie.getItem().equals(item)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean hasItemConditonV(Item item){
		for (int i = 0; i < visibilityCond.size(); i++) {
			if (visibilityCond.get(i) instanceof ItemCondition) {
				ItemCondition ie = (ItemCondition) visibilityCond.get(i);
				if (ie.getItem().equals(item)) {
					return true;
				}
			}
		}
		return false;
	}


	public boolean hasNameChangeEffect() {
		for (int i = 0; i < effectList.size(); i++) {
			if (effectList.get(i) instanceof NameChangeEffect) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param condition: the condition you want added
	 * @param conditionChoice: if the condition is a feasibility or visibility condition
	 */
	public void addCondition(Condition condition, int conditionChoice) {
		if (conditionChoice == FEASIBILITY) {
			feasibilityCond.add(condition);
		} else if (conditionChoice == VISIBILITY) {
			visibilityCond.add(condition);
		}else{
			throw new IllegalArgumentException("Must enter valid condition choice");
		}
	}

}
