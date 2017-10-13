package edu.augustana.csc285.game.datamodel;

public class GenderChangeEffect implements Effect {

	private Gender newGender;
	
	public GenderChangeEffect() {
	}
	
	public GenderChangeEffect(Gender newGender) {
		this.newGender = newGender;
	}
	
	@Override
	public void applyEffect(Player p) {
		p.setGender(newGender);
	}

	@Override
	public void setEffectSize(int size) {
		// TODO Auto-generated method stub
	}
	
	public void setEffectName(String name){
		//TODO 
	}
	
	@Override
	public String toString() {
		return "Gender change to: " + newGender;
	}

}
