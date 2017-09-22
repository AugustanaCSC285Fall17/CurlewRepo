package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;
import java.util.List;


public class Slide {
	private String title;
	private String imageFileName;
	private String gameText; 
	private String urlText;
	private boolean gameOver;
	private List<ActionChoice> actionChoices;

	//DON'T TOUCH IT
	public Slide() {
		this.title = "NewSlide";
		this.imageFileName = "";
		this.actionChoices = new ArrayList<>();
		
	}

	public Slide(String title, String imageFileName, String gameText
			    , String urlText) {
		this.gameText = gameText;
		this.title = title;
		this.imageFileName = imageFileName;
		this.urlText = urlText;
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
	
	public List<ActionChoice> getActionChoices() {
		return actionChoices;
	}
	
	// return gameText
	public String getGameText(){
		return gameText;
	}
	
	//return the urlText
	public String getUrlText(){
		return urlText;
	}

	public void setActionChoices(List<ActionChoice> actionChoices) {
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
	
	public String toString(){
		String s = "";
		if(actionChoices.size()==0){
			s = "There are no choices on this slide";
		} else{
		for(int i = 0; i < actionChoices.size(); i++){
			s+= "Action Choice "+i+": "+actionChoices.get(i).toString()+"\n";
		}
		}
		return "The title is "+title + " and the action Choices are \n" +s;
	}

}
