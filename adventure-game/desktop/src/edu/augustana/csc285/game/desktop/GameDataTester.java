package edu.augustana.csc285.game.desktop;

import edu.augustana.csc285.game.datamodel.ActionChoice;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.ItemCondition;
import edu.augustana.csc285.game.datamodel.ItemEffect;
import edu.augustana.csc285.game.datamodel.RelationalOperator;
import edu.augustana.csc285.game.datamodel.Slide;
import edu.augustana.csc285.game.datamodel.SlideType;

public class GameDataTester{
	
	public static void main(String[] args) {
		GameData gd = new GameData();
		gd.getPlayer().addItem("Bible", 0);
		
		Slide s0 = new Slide("Room 0", "Slide1.png", "Welcome to the new Room", SlideType.HISTORICAL, "www.google.com");
		s0.getActionChoices().add(new ActionChoice("go to room 1", 1));
		s0.getActionChoices().add(new ActionChoice("go to room 2", 2));
		s0.getActionChoiceAt(0).addEffect(new ItemEffect(gd.getPlayer().getInventory().get(0), 1));
		
		Slide s1 = new Slide("Room 1", "slide_001.png", "text1", SlideType.HISTORICAL, "url1");
		s1.getActionChoices().add(new ActionChoice("die", -1));
		s1.getActionChoices().add(new ActionChoice("go to room 2", 2));
		
		Slide s2 = new Slide("Room 2", "slide_002.png", "text2", SlideType.MANY_BUTTONS, "url2");
		s2.getActionChoices().add(new ActionChoice("go back to room 0", 0));
		s2.getActionChoiceAt(0).getVisibilityCond().add(new ItemCondition("emus", RelationalOperator.GREATER_THAN, 1));
		
		//Slide s3 = new Slide("Room 0", "slide_000.png", "text0", SlideType.NORMAL, "url0");
		
		gd.addSlide(s0);
		gd.addSlide(s1);
		gd.addSlide(s2);
		
		
		System.out.println(gd.toJSON());
	}

}
