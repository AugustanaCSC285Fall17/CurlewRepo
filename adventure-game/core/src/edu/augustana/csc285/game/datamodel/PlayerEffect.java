package edu.augustana.csc285.game.datamodel;

public class PlayerEffect {
	
	private Gender newGender;
	
	private PlayerEffect(){
		
	}
	
	public PlayerEffect(Gender newGender){
		this.newGender = newGender;
	}
	
	public Gender getGender(){
		return newGender;
	}
}
