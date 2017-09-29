package edu.augustana.csc285.game.datamodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class will represent all of the data needed to load/save an adventure
 * game.
 */
public class GameData {
	public static final int NORMAL_SLIDE = 0;
	public static final int HISTORICAL_POP_UP = 1;
	public static final int INVENTORY_SLIDE = 2;

	private int startSlideIndex;
	private int inventorySlideIndex;
	private int gameOverSlideIndex;

	private List<Slide> slides;
	private Player player;
	private int currentSlideIndex;
	public String saveName = "SavedFile";

	public GameData() { // needed for GSon
		slides = new ArrayList<Slide>();
		player = new Player("Minh!!");
		startSlideIndex = 0;
		inventorySlideIndex = 1;
		gameOverSlideIndex = 2;
	}

	public Slide getSlide(int index) {
		return slides.get(index);
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

	public int getInventorySlideIndex() {
		return inventorySlideIndex;
	}

	public void setInventorySlideIndex(int inventorySlideIndex) {
		this.inventorySlideIndex = inventorySlideIndex;
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

	/**
	 * 
	 * @param fileAddress
	 *            the address of the JSON file
	 * @return a GamaData object, which is created from deserializing the JSON
	 *         data imported from the file.
	 */
	public static GameData fromJSONFile(String fileAddress) {
		JsonParser parser = new JsonParser();
		try {
			Object obj = parser.parse(new FileReader(Gdx.files.internal(fileAddress).file()));

			JsonObject jsonData = (JsonObject) obj;

			return fromJSON(jsonData.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		String path = "assets/GameData/" + saveName + ".json";
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(toSave);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html
	public static GameData load(File inFile) throws FileNotFoundException{

		 String line = null;
		 String toLoad = "";
		 
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(inFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
             //   System.out.println(line);
            	toLoad+=line;
            } 
            bufferedReader.close(); 
        }
        catch(FileNotFoundException ex) {
                     
        }
        catch(IOException ex) {
          
        }
       GameData newGame= fromJSON(toLoad);
       return newGame;

	
	}
}
