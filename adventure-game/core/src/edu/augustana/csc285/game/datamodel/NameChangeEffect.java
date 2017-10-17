package edu.augustana.csc285.game.datamodel;

public class NameChangeEffect implements Effect {

	private String newName;
	
	public NameChangeEffect() {
	}
	
	public NameChangeEffect(String newName) {
		this.newName = newName;
	}
	
	@Override
	public void applyEffect(Player p) {
		p.setName(newName);
	}

	@Override
	public void setEffectSize(int size) {
		// TODO Don't know if we even need it in this class but is an abstract method
	}
	
	public void setEffectName(String name){
		//TODO Don't know if we even need it in this class but is an abstract method
	}
	
	public String printEffectInfo(){
		return "Name change to: " + newName;
	}
	
	@Override
	public String toString() {
		return newName + "";
	}

}
