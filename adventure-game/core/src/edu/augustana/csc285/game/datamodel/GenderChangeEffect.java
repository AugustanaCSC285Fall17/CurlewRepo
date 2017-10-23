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
		// TODO Don't know if we even need it in this class but is an abstract method
	}
	
	public void setEffectName(String name){
		//TODO Don't know if we even need it in this class but is an abstract method
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
