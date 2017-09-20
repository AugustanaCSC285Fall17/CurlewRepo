package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;
import java.util.List;


public class Slide {
	private String title;
	private String imageFileName;
	//I'll keep this List but i think it is the same as our option list
	private List<ActionChoice> actionChoices;

	//DON'T TOUCH IT
	public Slide() {
		title = "";
		imageFileName = "";
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

}
