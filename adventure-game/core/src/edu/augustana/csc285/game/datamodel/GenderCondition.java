package edu.augustana.csc285.game.datamodel;

public class GenderCondition{
	
	private Gender genderToCheck;
	
	private GenderCondition(){
		
	}
	
	public GenderCondition(Gender genderToCheck){
		
	}
	
	
	//should implement Gender enum and override method and should reuturn a boolean but to avoid 
	//error i use int now
	public int evaluate(Player p){
		return p.getGender();
	}
	

}
