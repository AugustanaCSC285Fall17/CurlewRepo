
package edu.augustana.csc285.game.datamodel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * This class will represent all of the data needed to load/save an adventure
 * game.
 */
public class GameData {
	public static final int NORMAL_SLIDE = 0;
	public static final int HISTORICAL_POP_UP = 1;
	public static final int LETTER_SLIDE = 3;
	public static final int MANY_BUTTONS_SLIDE = 4;

	private int startSlideIndex;
	private int gameOverSlideIndex;

	private ArrayList<Slide> slides;
	private Player player;
	private int currentSlideIndex;
	public String saveName = "SavedFile";

	public GameData() { // needed for GSon
		slides = new ArrayList<Slide>();
		player = new Player("Minh!!");
		startSlideIndex = 0;
		gameOverSlideIndex = 2;
	}
	
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

	
	

	public String attemptChoice(ActionChoice choice) {
		for (Condition cond : choice.getFeasibilityCond()) {
			if (!cond.evaluate(player)) {
				return choice.getRejText();
			}
		}
		
		for (Effect e : choice.getEffect()) {
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
	
	public Slide getGameOverSlide() {
		return slides.get(gameOverSlideIndex);
	}

	public void addSlide(Slide slide) {
		slides.add(slide);
	}

	public void removeSlide(int index) {

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
		File removeImage = new File(slides.get(index).getImageFileName());
		removeImage.delete();
		slides.remove(index);
	}

	public int getSlideListSize() {
		return slides.size();
	}

	public int getCurrentSlideIndex() {
		return currentSlideIndex;
	}

	public void setCurrentSlideIndex(int slide) {
		currentSlideIndex = slide;
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

	public int getGameOverSlideIndex() {
		return gameOverSlideIndex;
	}

	public void setGameOverSlideIndex(int gameOverSlideIndex) {
		this.gameOverSlideIndex = gameOverSlideIndex;
	}

	/**
	 * @return a serialized JSON-format string that represents this GameData
	 *         object
	 */
	public String toJSON() {
		return new Json().prettyPrint(this);
	}

	/**
	 * @return a GameData object, which is created from deserializing the JSON
	 *         data provided.
	 */
	public static GameData fromJSON(String jsonData) {
		return new Json().fromJson(GameData.class,jsonData);
	}
	
	/**
	 * 
	 * @param file the address of the JSON file
	 * @return a GamaData object, which is created from deserializing the JSON
	 *         data imported from the file.
	 */
	public static GameData fromJSONFile(File file) {
		JsonReader reader = new JsonReader();
		try {

			JsonValue jsonData = reader.parse(new FileHandle(file));
			return fromJSON(jsonData.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void saveAs(String saveName) {
		this.saveName = saveName;
		save();
	}

	public void save() {
		String toSave = toJSON();
		String path = "assets/data/" + saveName + ".json";
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(toSave);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String printSlideInfo (){
		return slides.toString();
	}
}
