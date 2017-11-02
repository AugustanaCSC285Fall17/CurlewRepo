package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;


public class Slide {
	private String title;
	private String imageFileName;
	private String gameText; 
	private String urlText;
	private SlideType slideType;
	private boolean gameOver;
	private ArrayList<ActionChoice> actionChoices;

	/**
	 * Initialize a newly created Slide object with a title, a imageFileName, a gameText, 
	 * an urlText, a SlideType, a gameOver boolean, and an ArrayList of actionChoices.
	 */
	public Slide() {
		this.title = "NewSlide";
		this.imageFileName = "";
		this.slideType = SlideType.NORMAL;
		this.actionChoices = new ArrayList<>();
		this.gameText = "";
		gameOver = false;
	}

	/**
	 * Initialize a newly created Slide object with a title, a imageFileName, a gameText, 
	 * an urlText, a SlideType, a gameOver boolean, and an ArrayList of actionChoices.
	 * 
	 * @param title			- slide title
	 * @param imageFileName	- name of the image file, located in assets/slideImages/
	 * @param gameText		- game text on the slide
	 * @param slideType		- type of the slide
	 * @param urlText		- url of the image file
	 */
	public Slide(String title, String imageFileName, String gameText
			    , SlideType slideType, String urlText) {
		this.gameText = gameText;
		this.title = title;
		this.imageFileName = imageFileName;
		this.urlText = urlText;
		this.slideType = slideType;
		this.actionChoices = new ArrayList<>();
		gameOver = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public ArrayList<ActionChoice> getActionChoices() {
		return actionChoices;
	}
	
	public ActionChoice getActionChoiceAt(int index) {
		return actionChoices.get(index);
	}
	
	/**
	 * This method add a default action choice
	 */
	public void addActionChoice () {
		actionChoices.add(new ActionChoice());
	}

	public void removeActionChoice(int index) {
		actionChoices.remove(index);	
	}
	
	// return gameText
	public String getGameText(){
		return gameText;
	}
	
	//return the urlText
	public String getUrlText(){
		return urlText;
	}

	public SlideType getSlideType() {
		return slideType;
	}

	public void setSlideType(SlideType slideType) {
		this.slideType = slideType;
	}

	//Return Game Over
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGameOver (boolean isGameOver){
		gameOver = isGameOver;
	}

	/**
	 * This method takes in an ArrayList of ActionChoice and replace the current ArrayList
	 * of ActionChoice with it.
	 * @param actionChoices	- new set of ActionChoices
	 */
	public void setActionChoices(ArrayList<ActionChoice> actionChoices) {
		this.actionChoices = actionChoices;
	}

	//Set the gameText
	public void setGameText(String text){
		gameText = text;
	}
	
	//Set Url Text
	public void setUrlText(String url){
		urlText = url;
	}
	
	public int getActionChoiceListSize(){
		return actionChoices.size();
	}
	
	public String toString(){
		String s = "";
		if(actionChoices.size()==0){
			s = "There are no choices on this slide";
		} else{
		for(int i = 0; i < actionChoices.size(); i++){
			s+= "Action Choice "+i+":\n"+actionChoices.get(i).toString()+"\n";
		}
		}
		return "The title is "+title + "\nThe image file name is: "+imageFileName+"\nThe gameText is: "+gameText+"\nThe slide type is: "+slideType+"\nThe action Choices are: \n" +s + "\nGame Over Slide?: " + gameOver;
	}

}
