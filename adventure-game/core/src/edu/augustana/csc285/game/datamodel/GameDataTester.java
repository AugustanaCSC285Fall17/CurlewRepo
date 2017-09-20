package edu.augustana.csc285.game.datamodel;

public class GameDataTester {

	public static void main(String[] args) {
		GameData gd = new GameData();
		Slide s0 = new Slide("Room 0", "slide_000.png", "text0", "url0");
		s0.getActionChoices().add(new ActionChoice("go to room 1", 1));
		s0.getActionChoices().add(new ActionChoice("go to room 2", 2));
		Slide s1 = new Slide("Room 1", "slide_001.png", "text1", "url1");
		s1.getActionChoices().add(new ActionChoice("die", -1));
		s1.getActionChoices().add(new ActionChoice("go to room 2", 2));
		Slide s2 = new Slide("Room 2", "slide_002.png", "text2", "url2");
		s2.getActionChoices().add(new ActionChoice("go back to room 0", 0));
		
		gd.addSlide(s0);
		gd.addSlide(s1);
		gd.addSlide(s2);
		
		System.out.println(s0);
		
		//String serializedJSONText = gd.toJSON();
		//System.out.println(serializedJSONText);
		
		//GameData gdRecreated = GameData.fromJSON(serializedJSONText);
		//System.out.println("Slide 0 has this image: ");
		//System.out.println(gdRecreated.getSlide(0).getImageFileName());
		
		//System.out.println(gd.getSlide(1).getActionChoices().get(1).getChoiceText());
		//System.out.println(gd.getSlide(1).getActionChoices().get(1).getDestinationSlideIndex());
		//gd.removeSlide(0);
		
		//System.out.println(gd.getSlide(0).getActionChoices().get(1).getChoiceText());
		//System.out.println(gd.getSlide(0).getActionChoices().get(1).getDestinationSlideIndex());
	}

}
