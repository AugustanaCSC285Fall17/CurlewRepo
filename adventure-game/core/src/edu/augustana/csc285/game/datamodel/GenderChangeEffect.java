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
		// need for abstract methods in other effect classes
	}
	
	public void setEffectName(String name){
		// need for abstract methods in other effect classes
	}
	
	public int getEffectSize(){
		return 0;
	}
	
	public String printEffectInfo(){
		return "Gender change to: " + newGender;
	}
	
	@Override
	public String toString() {
		return newGender + "";
	}

}
