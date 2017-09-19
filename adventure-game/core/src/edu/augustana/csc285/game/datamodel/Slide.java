package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Slide {
	private String title;
	private String imageFileName;
	private List<ActionChoice> actionChoices;

	public Slide() {
		this.title = "NewSlide";
		this.imageFileName = "";
		this.actionChoices = new ArrayList<>();
		
	}

	public Slide(String title, String imageFileName) {
		this.title = title;
		this.imageFileName = imageFileName;
		this.actionChoices = new ArrayList<>();
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

	public void setActionChoices(List<ActionChoice> actionChoices) {
		this.actionChoices = actionChoices;
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
