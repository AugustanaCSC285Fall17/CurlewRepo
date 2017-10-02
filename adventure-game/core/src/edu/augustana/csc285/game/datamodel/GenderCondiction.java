package edu.augustana.csc285.game.datamodel;

public class GenderCondiction{
	
	private Gender genderToCheck;
	
	private GenderCondiction(){
		
	}
	
	public GenderCondiction(Gender genderToCheck){
		
	}
	
	
	//should implement Gender enum and override method and should reuturn a boolean but to avoid 
	//error i use int now
	public int evaluate(Player p){
		return p.getGender();
	}
	

}
