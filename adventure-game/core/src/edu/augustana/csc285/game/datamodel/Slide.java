package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;


public class Slide {
	private String title;
	private String imageFileName;
	private String gameText; 
	private String urlText;
	private int slideType;
	private boolean gameOver;
	private ArrayList<ActionChoice> actionChoices;

	//DON'T TOUCH IT
	public Slide() {
		this.title = "NewSlide";
		this.imageFileName = "";
		this.slideType = 0;
		this.actionChoices = new ArrayList<>();
		
	}

	public Slide(String title, String imageFileName, String gameText
			    , int slideType, String urlText) {
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
	
	public ActionChoice getActionChoicesAt(int index) {
		return actionChoices.get(index);
	}
	//new method may change later
	public void addActionChoice () {
		actionChoices.add(new ActionChoice());
	}
	
	// return gameText
	public String getGameText(){
		return gameText;
	}
	
	//return the urlText
	public String getUrlText(){
		return urlText;
	}

	public int getSlideType() {
		return slideType;
	}

	public void setSlideType(int slideType) {
		this.slideType = slideType;
	}

	//Return Game Over
	public boolean isGameOver() {
		return gameOver;
	}

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
			s+= "Action Choice "+i+": "+actionChoices.get(i).toString()+"\n";
		}
		}
		return "The title is "+title + "\nThe image file name is: "+imageFileName+"\nThe gameText is: "+gameText+"\nThe slide type is: "+slideType+"\nThe action Choices are: \n" +s + "\n\n";
	}

	public void removeAc(int index) {
		actionChoices.remove(index);	
	}

}
