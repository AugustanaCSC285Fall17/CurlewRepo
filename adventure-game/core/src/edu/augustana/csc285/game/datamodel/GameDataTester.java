package edu.augustana.csc285.game.datamodel;

public class GameDataTester{
	
	public static void main(String[] args) {
		GameData gd = new GameData();

		Slide s0 = new Slide("Room 0", "Slide1.png", "Wellcome to the new Room", 0, "www.google.com");

		Slide s3 = new Slide("Room 0", "slide_000.png", "text0", 0, "url0");

		s0.getActionChoices().add(new ActionChoice("go to room 1", 1));
		s0.getActionChoices().add(new ActionChoice("go to room 2", 2));
		s0.getActionChoicesAt(0).addEffect(new InventoryEffect(69, "Bible"));
		Slide s1 = new Slide("Room 1", "slide_001.png", "text1", 0, "url1");
		s1.getActionChoices().add(new ActionChoice("die", -1));
		s1.getActionChoices().add(new ActionChoice("go to room 2", 2));
		Slide s2 = new Slide("Room 2", "slide_002.png", "text2", 0, "url2");
		s2.getActionChoices().add(new ActionChoice("go back to room 0", 0));
		s2.getActionChoicesAt(0).getVisibilityCond().add(new InventoryCondition("emus", RelationOperator.GREATER_THAN, 1));
		
		gd.addSlide(s0);
		gd.addSlide(s1);
		gd.addSlide(s2);
		
		gd.getPlayer().addItem("Bible", 0);
		
		System.out.println(gd.toJSON());
	}

}
