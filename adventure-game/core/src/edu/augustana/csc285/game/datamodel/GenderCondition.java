package edu.augustana.csc285.game.datamodel;

public class GenderCondition implements Condition {
	
	private Gender genderToCheck;
	
	public GenderCondition(){
	}
	
	public GenderCondition(Gender genderToCheck){
		this.genderToCheck = genderToCheck;
	}
	
	
	@Override
	public boolean evaluate(Player p){
		return p.getGender() == genderToCheck;
	}

	@Override
	public String printEffectInfo() {
		return "Gender Condition; Checking if " + genderToCheck;
	}
}
