package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;

public class ActionChoice {// implements Feasible
	public static final int FEASIBILITY = 0;
	public static final int VISIBILITY = 1;
	private String choiceText = "";
	private int destinationSlideIndex = -1;
	private boolean visible;
	private String rejText;
	private boolean effectVisible;
	
	private ArrayList<Condition> feasibilityCond = new ArrayList<>();
	private ArrayList<Condition> visibilityCond = new ArrayList<>();
	private ArrayList<Effect> effectList;
	/**
	 * Constructor with no parameters.  calls the constructor with 3 fields
	 */
	public ActionChoice() {
		this("", new ArrayList<Feasible>(), new ArrayList<Effect>());
		effectVisible = true;
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
	/**
	 * constructor with 3 parameters, is called by the "smaller" constructors
	 * @param rejText 
	 * @param feasibilityReq
	 * @param effectList
	 * @param displayEffect
	 */
	public ActionChoice(String rejText, ArrayList<Feasible> feasibilityReq, ArrayList<Effect> effectList, boolean displayEffect) {
		this.setRejText(rejText);
		this.effectList = effectList;
		visible = true;
		this.effectVisible = displayEffect;
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
	 *  test if the effect is visible and return true or false
	 * @return
	 */
	public boolean isEffectVisible() {
		return effectVisible;
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
	 * remove effect with an index
	 * @param index
	 */
	public void removeEffect(int index) {
		effectList.remove(index);
	}
	/**
	 * removes effect with a given effect object
	 * @param e effect that is to be removed
	 */
	public void removeEffect(Effect e) {
		effectList.remove(e);
	}
	/**
	 *  removes a condition given an index and an int that distinguishes between feasibility or visibility
	 * @param index index of the desired slide
	 * @param visOrFeas checks to see if the condition is a feasibility condition or a visibility condition
	 */
	public void removeCondition(int index, int visOrFeas){
		if(visOrFeas== FEASIBILITY){
			feasibilityCond.remove(index);
		}else if(visOrFeas== VISIBILITY){
			this.visibilityCond.remove(index);
		}
	}
	/**
	 * removes a condition given a condition and an int that distinguishes between feasibility or visibility
	 * @param condition the object that is to be removed
	 * @param visOrFeas checks to see if the condition is a feasibility condition or a visibility condition
	 */
	public void removeCondition(Condition condition, int visOrFeas){
		if(visOrFeas== FEASIBILITY){
			feasibilityCond.remove(condition);
		}else if(visOrFeas== VISIBILITY){
			this.visibilityCond.remove(condition);
		}
	}
	/**
	 *  changes the effect name
	 * @param newEffectName the new name of the effect
	 * @param index of the effect
	 */
	public void setEffectName(String newEffectName, int index) {
		effectList.get(index).setEffectName(newEffectName);
	}

	/**
	 * changes the effect size
	 * @param newEffectSize the new effect size
	 * @param index of the effect that is being edited
	 */
	public void setEffectSize(int newEffectSize, int index) {
		effectList.get(index).setEffectSize(newEffectSize);
	}
	/**
	 * 
	 * @return the list of feasibility conditions
	 */
	public ArrayList<Condition> getFeasibilityCond() {
		return feasibilityCond;
	}
	/**
	 * 
	 * @return the list of visibility conditions
	 */
	public ArrayList<Condition> getVisibilityCond() {
		return visibilityCond;
	}
	/**
	 * used to display information about the effect that we do not want displayed in the toString
	 * @return s the string of string info
	 */
	public String getEffectsString() {
		String s = "Inventory change:\n";
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
	/**
	 * used to display information about the effect that we do not want displayed in the toString. This method ignore invisible items
	 * @return s the string of string info
	 */
	public String getEffectsStringIfVisible() {
		String s = "Inventory change:\n";
		for (Effect effect : effectList) {
			if (effect instanceof ItemEffect) {
				ItemEffect itemEffect = (ItemEffect) effect;
				String effectSize = "" + itemEffect.getEffectSize();
				if (!itemEffect.getItem().getItemName().substring(0, 1).equals("~")) {
					// add a plus sign if the quantity is positive (since negative effect size already have a negative sign, duh!)
					if (itemEffect.getEffectSize() > 0)
						effectSize = "+" + itemEffect.getEffectSize();
						
					s += effectSize + " " + itemEffect.getItem().getItemName() + "\n";
				}
			}
		}
		
		return s;
	}
	/**
	 * returns the string of action choice info to be displayed in the preview pane
	 */
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
	/**
	 * 
	 * @return the rejection text
	 */
	public String getRejText() {
		return rejText;
	}
	/**
	 * 
	 * @param rejText the new rejection text
	 */
	public void setRejText(String rejText) {
		this.rejText = rejText;
	}
	/**
	 * checks to see if an action choice has a gender effect
	 * @return true if the action choice has a gender effect and false otherwise
	 */
	public boolean hasGenderEffect() {
		for (int i = 0; i < effectList.size(); i++) {
			if (effectList.get(i) instanceof GenderChangeEffect) {
				return true;
			}
		}
		return false;
	}
	/**
	 *  checks to see if an action choice has a item effect
	 * @param itemChoice the specified item that we are checking if we have an effect for
	 * @return true if the action choice has a item effect for the specified item and false otherwise
	 */
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
	/**
	 *  checks to see if an itemCondition is feasible 
	 * @param item the specified item
	 * @return true if it is feasible and false otherwise
	 */
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
	/**
	 *  checks to see if an itemCondition is visible 
	 * @param item the specified item
	 * @return true if it is visible and false otherwise
	 */
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

	/**
	 * checks to see if an action choice has a name change effect
	 * @return true if the action choice has a name change effect and false otherwise
	 */
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
