
package edu.augustana.csc285.game.datamodel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Json;

/**
 * This class will represent all of the data needed to load/save an adventure
 * game.
 */
public class GameData {

	private int startSlideIndex;

	private ArrayList<Slide> slides;
	private Player player;
	private int currentSlideIndex;
	public String saveName = "SavedFile";
	private boolean gameStarted;

	public GameData() { // needed for GSon
		slides = new ArrayList<Slide>();
		player = new Player("Curlew!!");
		currentSlideIndex = startSlideIndex;
	}

	/**
	 * 
	 * @return the visible choices for a current slide
	 */
	public ArrayList<ActionChoice> getVisibleChoicesForCurrentSlide() {
		ArrayList<ActionChoice> allChoices = slides.get(currentSlideIndex).getActionChoices();
		ArrayList<ActionChoice> visibleChoices = new ArrayList<ActionChoice>();
		for (ActionChoice choice : allChoices) {
			boolean choiceVisible = true;
			for (Condition cond : choice.getVisibilityCond()) {
				if (!cond.evaluate(player)) {
					choiceVisible = false;
					break;
				}
			}
			if (choiceVisible)
				visibleChoices.add(choice);
		}
		return visibleChoices;
	}

	/**
	 * 
	 * @return the visible items
	 */
	public ArrayList<Item> getCurrentVisibleItems() {
		ArrayList<Item> allItems = player.getInventory();
		ArrayList<Item> visibleItems = new ArrayList<Item>();
		for (Item item : allItems) {
			if (item.isVisible() && item.getItemQty() != 0)
				visibleItems.add(item);
		}
		return visibleItems;
	}

	/**
	 * 
	 * @param choice
	 * @return arbitrarily returns an empty string
	 */
	public String attemptChoice(ActionChoice choice) {
		for (Condition cond : choice.getFeasibilityCond()) {
			if (!cond.evaluate(player)) {
				return choice.getRejText();
			}
		}

		for (Effect e : choice.getEffects()) {
			e.applyEffect(player);
		}
		currentSlideIndex = choice.getDestinationSlideIndex();
		return "";
	}

	public Slide getSlide(int index) {
		return slides.get(index);
	}

	public Slide getStartSlide() {
		return slides.get(startSlideIndex);
	}

	/**
	 * adds a slide
	 * 
	 * @param slide
	 */
	public void addSlide(Slide slide) {
		slides.add(slide);
	}

	public void removeSlide(int index) {
		slides.remove(index);
		// loops through every slide
		for (int currentSlide = 0; currentSlide < slides.size(); currentSlide++) {
			List<ActionChoice> choices = slides.get(currentSlide).getActionChoices();
			// loops through every choice
			for (int currentActionChoice = 0; currentActionChoice < choices.size(); currentActionChoice++) {

				// for every action choice if the destination slide index is
				// greater than the remove slide index than the destination
				// slide index is reduced by 1
				if (choices.get(currentActionChoice).getDestinationSlideIndex() > index) {
					choices.get(currentActionChoice)
							.setDestinationSlideIndex(choices.get(currentActionChoice).getDestinationSlideIndex() - 1);
				}
			}
		}
	}

	public int getSlideListSize() {
		return slides.size();
	}
	
	public void setCurrentSlideIndex(int index) {
		currentSlideIndex = index;
	}
	
	public int getCurrentSlideIndex() {
		return currentSlideIndex;
	}
	
	public SlideType getCurrentSlideType() {
		return getSlide(currentSlideIndex).getSlideType();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getStartSlideIndex() {
		return startSlideIndex;
	}

	public void setStartSlideIndex(int startSlideIndex) {
		this.startSlideIndex = startSlideIndex;
	}

	/**
	 * @return a serialized JSON-format string that represents this GameData object
	 */
	public String toJSON() {
		return new Json().prettyPrint(this);
	}

	/**
	 * @return a GameData object, which is created from deserializing the JSON data
	 *         provided.
	 */
	public static GameData fromJSON(String jsonData) {
		GameData data = new Json().fromJson(GameData.class, jsonData);
		data.currentSlideIndex = data.startSlideIndex;
		return data;
	}
	/**
	 * saves the progress in the game builder with a save as name by calling the save method below
	 * @param saveName string that the user wants the data to be saved under
	 */
	public void saveAs(String newAddress) {
		save(newAddress);
	}

	/**
	 * saves the info in the game builder
	 */
	public void save(String currentAddress) {
		String toSave = toJSON();
		try {
			FileWriter writer = new FileWriter(currentAddress);
			writer.write(toSave);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return a string with slide info to be displayed in the preview pane
	 */
	public String printSlideInfo() {
		String s = "";
		s += player.toString();
		for (int i = 0; i < slides.size(); i++) {
			s += "Slide Number: " + i + "\n" + slides.get(i).toString() + "\n\n";
		}
		return s;
	}

	/**
	 * 
	 * @param item
	 * @return true if the item has an effect attached to it and false if not
	 */
	public boolean itemUsed(Item item) {
		for (Slide slide : slides) {
			for (ActionChoice choice : slide.getActionChoices()) {
				if (choice.hasItemEffect(item) || choice.hasItemConditonF(item) || choice.hasItemConditonV(item)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * removes an item
	 * 
	 * @param item
	 */
	public void removeItem(Item item) {
		for (Slide s : slides) {
			for (ActionChoice ac : s.getActionChoices()) {
				for (int i = 0; i < ac.getEffects().size(); i++) {
					if (ac.getEffects().get(i) instanceof ItemEffect) {
						ItemEffect iE = (ItemEffect) ac.getEffects().get(i);
						if (iE.getItem().equals(item)) {
							ac.removeEffect(ac.getEffects().get(i));
						}
					}
				}
				for (int i = 0; i < ac.getFeasibilityCond().size(); i++) {
					if (ac.getFeasibilityCond().get(i) instanceof ItemCondition) {
						ItemCondition ic = (ItemCondition) ac.getFeasibilityCond().get(i);
						if (ic.getItem().equals(item)) {
							ac.removeCondition(ac.getFeasibilityCond().remove(i), ActionChoice.FEASIBILITY);
						}
					}
				}
				for (int i = 0; i < ac.getVisibilityCond().size(); i++) {
					if (ac.getVisibilityCond().get(i) instanceof ItemCondition) {
						ItemCondition ic = (ItemCondition) ac.getVisibilityCond().get(i);
						if (ic.getItem().equals(item)) {
							ac.removeCondition(ac.getVisibilityCond().remove(i), ActionChoice.FEASIBILITY);
						}
					}
				}
			}
		}

		player.getInventory().remove(item);

	}

	public boolean gameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
}