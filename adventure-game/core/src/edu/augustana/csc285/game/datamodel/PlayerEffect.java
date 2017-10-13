package edu.augustana.csc285.game.datamodel;

public class PlayerEffect implements Effect {
	
	private Gender newGender;
	
	public PlayerEffect(){
		
	}
	
	public PlayerEffect(Gender newGender){
		this.newGender = newGender;
	}
	
	public Gender getGender(){
		return newGender;
	}

	@Override
	public void applyEffect(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEffectSize(int size) {
		// TODO Auto-generated method stub
		
	}
	
	public void setEffectName(String name){
		//TODO 
	}
}
