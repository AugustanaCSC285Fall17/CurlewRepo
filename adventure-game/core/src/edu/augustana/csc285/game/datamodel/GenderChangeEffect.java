package edu.augustana.csc285.game.datamodel;

public class GenderChangeEffect implements Effect {

	private Gender newGender;
	
	/**
	 * creates a new gender by setting an inputed gender 
	 * @param newGender the gender that the user would like to 
	 */
	public GenderChangeEffect(Gender newGender) {
		this.newGender = newGender;
	}
	/**
	 * puts the effect into effect
	 */
	@Override
	public void applyEffect(Player p) {
		p.setGender(newGender);
	}

	/**
	 * need for abstract methods in other effect classes
	 */
	@Override
	public void setEffectSize(int size) {}
	/**
	 * need for abstract methods in other effect classes
	 */
	public void setEffectName(String name){}
	/**
	 * getter method for the effect size. always returns 0
	 * needed for abstract methods in other effect classes
	 */
	public int getEffectSize(){
		return 0;
	}
	/**
	 * used to display a pop up of the gender change
	 */
	public String printEffectInfo(){
		return "Gender change to: " + newGender;
	}
	
	/**
	 * to string of the gender effect returns just the gender object
	 */
	@Override
	public String toString() {
		return newGender + "";
	}

}
