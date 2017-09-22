package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class will represent all of the data needed to load/save an adventure
 * game.
 */
public class GameData {
	private List<Slide> slides;

	public GameData() { // needed for GSon
		slides = new ArrayList<Slide>();
	}

	public Slide getSlide(int index) {
		return slides.get(index);
	}

	public void addSlide(Slide slide) {
		slides.add(slide);
	}


	public void removeSlide(int index) {
		
		//loops through every slide
		for (int currentSlide = 0; currentSlide < slides.size(); currentSlide++) {
			List<ActionChoice> choices = slides.get(currentSlide).getActionChoices();
			//loops through every choice
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
		slides.remove(index);
	}



	public int getSlideListSize(){
		return slides.size();
	}
	



	/**
	 * @return a serialized JSON-format string that represents this GameData
	 *         object
	 */
	public String toJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

	/**
	 * @return a GameData object, which is created from deserializing the JSON
	 *         data provided.
	 */
	public static GameData fromJSON(String jsonData) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonData, GameData.class);
	}
}
